// Khi chọn file thì hiển thị preview
document.getElementById('addImageInput').addEventListener('change', function (event) {
    const preview = document.getElementById('editProductPreview');
    preview.innerHTML = ''; // clear cũ

    Array.from(event.target.files).forEach((file, index) => {
        const reader = new FileReader();
        reader.onload = function (e) {
            const div = document.createElement('div');
            div.classList.add('image-item');

            div.innerHTML = `
                <img src="${e.target.result}" alt="Ảnh sản phẩm" 
                     style="width:80px;height:80px;object-fit:cover;margin:5px;">
                <br>
                Ảnh chính <input type="radio" style="width: 20px; height: 20px" name="mainImage" value="${index}" ${index === 0 ? 'checked' : ''}>
            `;

            preview.appendChild(div);
        };
        reader.readAsDataURL(file);
    });
});

export const setEvent = () => {
    //set sự kiện click vào checkbox xóa (đảm bảo admin không chọn xóa được ảnh chính)
    document.querySelectorAll('#popup-edit .content-edit-product .edit-remove-img')
        .forEach(checkbox => {
            checkbox.addEventListener('change', () => {
                if(!checkbox.checked) return;
                const imageItem = checkbox.closest('.image-item');
                if(imageItem) {
                    const radio = imageItem.querySelector('input[type="radio"][name="mainImage"]');
                    if(radio.checked) {
                        alert("Ảnh chính của sản phẩm không thể bị xóa, vui lòng chọn lại ảnh chính và thử lại!")
                        checkbox.checked = false
                    }
                }
            })
        })

    //set sự kiện khi click vào radio chọn ảnh chính sẽ hủy checked xóa
    document.querySelectorAll('#popup-edit .content-edit-product .mainImage')
        .forEach(radio => {
            radio.addEventListener('change', () => {
                if(!radio.checked) return;
                const imageItem = radio.closest('.image-item');
                if(imageItem) {
                    const checkbox = imageItem.querySelector('input[type="checkbox"][name="edit-remove-img"]');
                    if(checkbox.checked) {
                        checkbox.checked = false
                    }
                }
            })
        })
}