import {deleteSkuImage, deleteProductVariant} from './admin_product_remove_variant.js';

//set sự kiện ajax cho show popup variants
const showPopupVariants = async (prodId) => {
    const container = document.getElementById('variant-data-container');
    const popup = document.getElementById('popup-variants');

    fetch(`${contextPath}/get-variants?productId=${prodId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Lỗi Server: " + response.status);
            }
            return response.text();
        })
        .then(html => {
            container.innerHTML = html;
            const sizeVariants = document.querySelectorAll(".product-variant-item").length

            //xử lí sự kiện xóa biến thể
            /*
            đầu tiên gọi xuống JsonGetFolderServlet để lấy các thông tin về folderId, nếu folderId không có thì
            tức hình ảnh sản phẩm đó chưa từng được lưu trên storage thì ta chỉ việc xóa sản phẩm dưới db, còn nếu có thì
            sau khi xóa xong bên trong JsonRemoveProductServlet sẽ có trả về mã SKU để dựa vào đấy truy ra đường dẫn
            mà storage đang lưu ảnh của biến thể đó
             */
            document.querySelectorAll(".edit-product-var-remove").forEach(function (el) {
                el.addEventListener("click", async function () {

                    const id = this.dataset.id; // lấy product variant id
                    if (sizeVariants < 2) {
                        alert("Không thể xóa biến thể " + id + " khi sản phẩm chung chỉ có " + sizeVariants + " biến thể");
                    } else {
                        const isDelete = confirm("Bạn có chắc chắn muốn xóa biến thể mang mã: " + id + " không?")
                        if (isDelete) {
                            try {
                                // lấy thông tin folderId và SKU trước khi xóa trong DB
                                const folderRes = await fetch(`${contextPath}/get-folder-id?variantId=${id}`);
                                const folderData = await folderRes.json();

                                // Gọi Servlet xóa trong Database
                                const res = await deleteProductVariant(id);

                                if (res.success) {
                                    // Nếu DB xóa xong và trước đó có ảnh trên storage thì mới xóa Firebase
                                    if (folderData.hasImageInStorage) {
                                        const deleteStorage = await deleteSkuImage(folderData.folderId, res.sku);
                                        alert(res.message + " và " + deleteStorage.message)
                                    } else
                                        alert(res.message);
                                    // Reload lại danh sách biến thể sau khi xóa thành công
                                    await showPopupVariants(prodId);
                                } else {
                                    alert("Lỗi xóa DB: " + res.message);
                                }
                            } catch (err) {
                                console.error(err);
                                alert("Lỗi hệ thống: " + err.message);
                            }

                        }
                    }
                });
            });

        })
        .catch(err => {
            console.log(err)
            container.innerHTML = `<tr><td colspan="7" style="color:red; text-align:center;">Không thể tải dữ liệu</td></tr>`;
        })
}


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
        const productId = btn.dataset.id; // Dùng trực tiếp biến btn sẽ an toàn hơn
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
    btn.addEventListener('click', () => {
        document.getElementById('popup-edit').style.display = 'block';
    });
});
document.getElementById('closeEdit').onclick = () => {
    document.getElementById('popup-edit').style.display = 'none';
};

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
