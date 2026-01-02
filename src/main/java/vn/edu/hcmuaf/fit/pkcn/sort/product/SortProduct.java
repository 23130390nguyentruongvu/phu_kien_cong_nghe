package vn.edu.hcmuaf.fit.pkcn.sort.product;

/*
    Các phương thức đều nhận vào boolean isParent, boolean getByCategoryId nhằm mục đích kiểm tra
    xem lệnh đấy có nên join với categoryId để lấy sản phẩm theo thể loại hay không, nếu có thì kiểm tra
    xem có lấy các sản phẩm theo thể loại cha hay con
 */
public interface SortProduct {
    String sqlSortByNewestProduct(boolean isParent, boolean getByCategoryId);

    String sqlSortByOldestProduct(boolean isParent, boolean getByCategoryId);

    String sqlSortByCheapestProduct(boolean isParent, boolean getByCategoryId);

    String sqlSortByMostExpensiveProduct(boolean isParent, boolean getByCategoryId);

    String sqlSortByHighestStarProduct(boolean isParent, boolean getByCategoryId);

    String sqlSortByLowestStarProduct(boolean isParent, boolean getByCategoryId);
}
