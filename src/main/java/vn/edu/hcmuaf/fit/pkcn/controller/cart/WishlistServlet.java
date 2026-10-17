package vn.edu.hcmuaf.fit.pkcn.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.pkcn.config.JDBI;
import vn.edu.hcmuaf.fit.pkcn.model.cart.Cart;
import vn.edu.hcmuaf.fit.pkcn.model.product.ProductVariant;
import vn.edu.hcmuaf.fit.pkcn.model.user.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WishlistServlet extends HttpServlet {
    private Jdbi jdbi;

    @Override
    public void init() {
        jdbi = JDBI.getJdbi();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = getCustomer(request);
        if (user == null) {
            request.setAttribute("wishlistGuest", true);
            request.setAttribute("wishlistProducts", List.of());
            request.getRequestDispatcher("/WEB-INF/views/client/wishlist.jsp").forward(request, response);
            return;
        }

        try {
            List<Map<String, Object>> products = jdbi.withHandle(handle -> handle.createQuery("""
                    SELECT w.id AS wishlist_id,
                           p.id AS product_id,
                           p.name,
                           p.min_price,
                           p.status AS product_status,
                           (SELECT pi.url_image
                            FROM product_images pi
                            WHERE pi.product_id = p.id
                            ORDER BY pi.is_main DESC, pi.id
                            LIMIT 1) AS image_url
                    FROM wishlists w
                    JOIN products p ON p.id = w.product_id
                    WHERE w.user_id = :userId
                    ORDER BY w.created_at DESC
                    """)
                    .bind("userId", user.getId())
                    .mapToMap()
                    .list());

            List<Map<String, Object>> viewProducts = products;
            if (!products.isEmpty()) {
                List<Integer> productIds = products.stream()
                        .map(product -> ((Number) product.get("product_id")).intValue())
                        .toList();
                List<Map<String, Object>> variants = jdbi.withHandle(handle -> handle.createQuery("""
                        SELECT pv.id, pv.product_id, pv.name, pv.sku, pv.price, pv.stock
                        FROM product_variants pv
                        JOIN products p ON p.id = pv.product_id AND p.status = 1
                        WHERE pv.product_id IN (<productIds>)
                        ORDER BY pv.product_id, pv.id
                        """)
                        .bindList("productIds", productIds)
                        .mapToMap()
                        .list());

                Map<Integer, List<Map<String, Object>>> variantsByProduct = new HashMap<>();
                for (Map<String, Object> variant : variants) {
                    int productId = ((Number) variant.get("product_id")).intValue();
                    variantsByProduct.computeIfAbsent(productId, ignored -> new ArrayList<>()).add(variant);
                }
                List<Map<String, Object>> enrichedProducts = new ArrayList<>(products.size());
                for (Map<String, Object> product : products) {
                    int productId = ((Number) product.get("product_id")).intValue();
                    List<Map<String, Object>> productVariants =
                            variantsByProduct.getOrDefault(productId, List.of());
                    boolean hasAvailableVariant = productVariants.stream()
                            .anyMatch(variant -> ((Number) variant.get("stock")).intValue() > 0);
                    Map<String, Object> viewProduct = new LinkedHashMap<>(product);
                    viewProduct.put("variants", productVariants);
                    viewProduct.put("productAvailable", isActiveStatus(product.get("product_status")));
                    viewProduct.put("hasAvailableVariant", hasAvailableVariant);
                    enrichedProducts.add(viewProduct);
                }
                viewProducts = enrichedProducts;
            }

            request.setAttribute("wishlistProducts", viewProducts);
            request.getRequestDispatcher("/WEB-INF/views/client/wishlist.jsp").forward(request, response);
        } catch (RuntimeException e) {
            getServletContext().log("Không thể tải wishlist của user " + user.getId(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        User user = getCustomer(request);
        if (user == null) {
            String target = "add".equals(request.getParameter("action"))
                    ? productDetailTarget(request.getParameter("productId"))
                    : "/wishlist";
            redirectToLogin(request, response, target);
            return;
        }
        String action = request.getParameter("action");
        try {
            if ("add".equals(action)) {
                addProduct(request, response, user);
            } else if ("remove".equals(action)) {
                removeProduct(request, response, user);
            } else if ("move".equals(action)) {
                addProductToCart(request, response, user);
            } else {
                response.sendRedirect(request.getContextPath() + "/wishlist?error=invalid");
            }
        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/wishlist?error=invalid");
        } catch (RuntimeException e) {
            getServletContext().log("Không thể cập nhật wishlist của user " + user.getId(), e);
            response.sendRedirect(request.getContextPath() + "/wishlist?error=server");
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {
        int productId = parsePositiveInt(request.getParameter("productId"));
        int activeProduct = jdbi.withHandle(handle -> handle.createQuery("""
                SELECT COUNT(*)
                FROM products
                WHERE id = :productId AND status = 1
                """)
                .bind("productId", productId)
                .mapTo(Integer.class)
                .one());
        if (activeProduct == 0) {
            response.sendRedirect(request.getContextPath() + "/product-detail?id="
                    + productId + "&wishlist=unavailable");
            return;
        }

        jdbi.useHandle(handle -> handle.createUpdate("""
                INSERT INTO wishlists (user_id, product_id)
                VALUES (:userId, :productId)
                ON DUPLICATE KEY UPDATE id = id
                """)
                .bind("userId", user.getId())
                .bind("productId", productId)
                .execute());
        response.sendRedirect(request.getContextPath() + "/product-detail?id="
                + productId + "&wishlist=added");
    }

    private void removeProduct(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {
        int wishlistId = parsePositiveInt(request.getParameter("wishlistId"));
        int removed = jdbi.withHandle(handle -> handle.createUpdate("""
                DELETE FROM wishlists
                WHERE id = :wishlistId AND user_id = :userId
                """)
                .bind("wishlistId", wishlistId)
                .bind("userId", user.getId())
                .execute());
        response.sendRedirect(request.getContextPath() + "/wishlist?success="
                + (removed > 0 ? "removed" : "unchanged"));
    }

    private void addProductToCart(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {
        int productId = parsePositiveInt(request.getParameter("productId"));
        int variantId = parsePositiveInt(request.getParameter("variantId"));
        int quantity = parsePositiveInt(request.getParameter("quantity"));
        if (quantity > 100) {
            throw new IllegalArgumentException("Số lượng không hợp lệ");
        }

        Map<String, Object> selected = jdbi.withHandle(handle -> handle.createQuery("""
                SELECT pv.id,
                       pv.product_id,
                       pv.sku,
                       pv.name AS variant_name,
                       pv.price,
                       pv.stock,
                       p.name AS product_name,
                       COALESCE((SELECT pi.url_image
                                 FROM product_images pi
                                 WHERE pi.product_variant_id = pv.id
                                 ORDER BY pi.is_main DESC, pi.id
                                 LIMIT 1),
                                (SELECT pi.url_image
                                 FROM product_images pi
                                 WHERE pi.product_id = p.id
                                 ORDER BY pi.is_main DESC, pi.id
                                 LIMIT 1),
                                '') AS url_image
                FROM wishlists w
                JOIN products p ON p.id = w.product_id
                JOIN product_variants pv ON pv.product_id = p.id
                WHERE w.user_id = :userId
                  AND p.id = :productId
                  AND p.status = 1
                  AND pv.id = :variantId
                  AND pv.stock >= :quantity
                """)
                .bind("userId", user.getId())
                .bind("productId", productId)
                .bind("variantId", variantId)
                .bind("quantity", quantity)
                .mapToMap()
                .findOne()
                .orElse(null));

        if (selected == null) {
            response.sendRedirect(request.getContextPath() + "/wishlist?error=unavailable");
            return;
        }

        ProductVariant variant = new ProductVariant();
        variant.setId(((Number) selected.get("id")).intValue());
        variant.setProductId(((Number) selected.get("product_id")).intValue());
        variant.setSku((String) selected.get("sku"));
        variant.setName((String) selected.get("variant_name"));
        variant.setPrice(new BigDecimal(selected.get("price").toString()));
        variant.setStock(((Number) selected.get("stock")).intValue());
        variant.setUrlImage((String) selected.get("url_image"));

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart(user);
        }
        int existingQuantity = cart.getCartItems().stream()
                .filter(item -> item.getProductVariantId() == variantId)
                .mapToInt(item -> item.getQuantity())
                .sum();
        if (existingQuantity + quantity > variant.getStock()) {
            response.sendRedirect(request.getContextPath() + "/wishlist?error=unavailable");
            return;
        }
        cart.addCartItem(variant, (String) selected.get("product_name"), quantity);
        session.setAttribute("cart", cart);

        response.sendRedirect(request.getContextPath() + "/wishlist?success=added-to-cart");
    }

    private User getCustomer(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response, String target)
            throws IOException {
        request.getSession().setAttribute("afterLoginPath", target);
        response.sendRedirect(request.getContextPath() + "/login");
    }

    private String productDetailTarget(String productIdValue) {
        try {
            int productId = parsePositiveInt(productIdValue);
            return "/product-detail?id=" + productId;
        } catch (IllegalArgumentException e) {
            return "/wishlist";
        }
    }

    private int parsePositiveInt(String value) {
        try {
            int parsed = Integer.parseInt(value);
            if (parsed > 0) {
                return parsed;
            }
        } catch (NumberFormatException ignored) {
        }
        throw new IllegalArgumentException("Giá trị số không hợp lệ");
    }

    private boolean isActiveStatus(Object status) {
        return Boolean.TRUE.equals(status)
                || status instanceof Number number && number.intValue() == 1;
    }
}
