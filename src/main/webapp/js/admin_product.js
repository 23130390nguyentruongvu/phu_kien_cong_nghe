import {showPopupVariants} from './admin_product_remove_variant.js';
import {setEvent} from "./admin_product_edit.js";


// Mở popup thêm sản phẩm
document.querySelector('.head-edit .edit-product-add-prod').onclick = () => {
    document.getElementById('popup-add-product').style.display = 'block';
};
document.getElementById('closeAddProd').onclick = () => {
    document.getElementById('popup-add-product').style.display = 'none';
};

// Mở popup xem biến thể
document.querySelectorAll('.edit-product-show-var').forEach(btn => {
    btn.addEventListener('click', async (e) => {
        const productId = btn.dataset.id;
        const popup = document.getElementById('popup-variants');
        const container = document.getElementById('variant-data-container');

        popup.style.display = 'block';
        container.innerHTML = `<tr><td colspan="7" style="text-align:center;">Đang tải dữ liệu...</td></tr>`;
        try {
            await showPopupVariants(productId);
        } catch (error) {
            console.error("Lỗi khi mở popup:", error);
            container.innerHTML = `<tr><td colspan="7" style="color:red; text-align:center;">Lỗi: ${error.message}</td></tr>`;
        }
    });
});
document.getElementById('closeVariants').onclick = () => {
    document.getElementById('popup-variants').style.display = 'none';
};

// Mở popup thêm/chỉnh biến thể
//thêm
document.querySelectorAll('.edit-product-add-var').forEach(btn => {
    btn.addEventListener('click', () => {
        openPopupByActionProdVar('', '', undefined)
    });
});

document.getElementById('closeAddProdVar').onclick = () => {
    document.getElementById('popup-add-edit-variant').style.display = 'none';
};
//chỉnh
document.querySelectorAll('.edit-product-var-update').forEach(btn => {
    btn.addEventListener('click', () => {
        openPopupByActionProdVar('', '', 1)
    });
});

document.getElementById('closeAddProdVar').onclick = () => {
    document.getElementById('popup-add-edit-variant').style.display = 'none';
};

// Mở popup chỉnh sửa
document.querySelectorAll('.edit-product-update').forEach(btn => {
    btn.addEventListener('click', async () => {
        const id = btn.dataset.id;
        try {
            const response = await fetch(`${contextPath}/get-product-edit?productId=${id}`);
            if (!response.ok) throw new Error("Server error: " + response.status);
            const html = await response.text();
            const container = document.querySelector('#popup-edit .content-edit-product');
            console.log(container)
            container.innerHTML = html;
            setEvent()

            document.getElementById('popup-edit').style.display = 'block';
        } catch (err) {
            console.error("Lỗi khi load form edit:", err);
            alert("Không thể tải form chỉnh sửa sản phẩm");
        }
    });
})

document.getElementById('closeEdit').addEventListener('click', () => {
    document.getElementById('popup-edit').style.display = 'none';
});
// Hàm mở popup add hoặc chỉnh sửa sản phẩm biến thể
// edit-product-add-var and edit-product-var-update
function openPopupByActionProdVar(actionCallProdVar, actionServlet, idProdVar) {
    /*
    Hàm này dùng ajax gọi api từ actionCallProdVar (servlet) để lấy về json của đối
    tượng idProdVar và fill vào form nếu nó có
     */
    const popup = document.getElementById('popup-add-edit-variant');
    const form = document.getElementById('variantForm');

    // Reset form trước khi mở
    form.reset();

    if (!idProdVar) {
        // Trường hợp chỉnh sửa biến thể
        // Gọi AJAX để lấy dữ liệu biến thể từ server theo idProdVar
        fetch(`${actionCallProdVar}?idProdVar=${idProdVar}`)
            .then(response => response.json())
            .then(data => {
                // Fill dữ liệu vào form
                form.querySelector('[name="variantNames"]').value = data.variantNames;
                form.querySelector('[name="sku"]').value = data.sku;
                form.querySelector('[name="variantPrices"]').value = data.variantPrices;
                form.querySelector('[name="variantStocks"]').value = data.variantStocks;
                form.querySelector('[name="gram"]').value = data.gram;
                form.querySelector('[name="color"]').value = data.color;
                form.querySelector('[name="size"]').value = data.size;
                form.querySelector('[name="variantImage"]').value = data.variantImage;
            })
            .catch(err => console.error("Lỗi load dữ liệu biến thể:", err));

    }
    form.formAction = actionServlet

    // Hiển thị popup
    popup.style.display = 'block';
}


// Hàm mở popup với message động
function openConfirmPopup(message, id, actionServlet, onConfirm) {
    //Thiết lập actionServlet trong form của popup-confirm
    const popup = document.getElementById('popup-confirm');
    const msg = document.getElementById('confirmMessage');
    msg.textContent = message;
    popup.style.display = 'block';
    popup.formAction = actionServlet;

    // Xử lý nút Hủy
    document.getElementById('confirmNo').onclick = () => {
        console.log("confirm no");
        popup.style.display = 'none';
    };
}

//mở popup xác nhận hành động
document.addEventListener('click', (e) => {
    if (e.target.classList.contains('remove-img')) {
        console.log(e.target);
        openConfirmPopup("Bạn có chắc chắn muốn xóa ảnh này?", 1, '', () => {
            // Logic xóa ảnh
            e.target.parentElement.remove();
            console.log("Ảnh đã bị xóa!");
        });
    } else if (e.target.classList.contains('')) {
        console.log(e.target);
    }
});

//thêm các url cho sản phẩm
document.getElementById('addImageUrl').onclick = () => {
    const list = document.getElementById('imageUrlList');
    const index = list.children.length;

    const div = document.createElement('div');
    div.className = 'image-url-item';
    div.innerHTML = `
    <input type="text" name="imageUrls[]" placeholder="Nhập URL ảnh">
    <input type="radio" name="mainImage" value="${index}"> Ảnh chính
    <button type="button" class="remove-url">Xóa</button>
  `;
    list.appendChild(div);
};

document.addEventListener('click', function (e) {
    if (e.target.classList.contains('remove-url')) {
        e.target.parentElement.remove();
    }
});
