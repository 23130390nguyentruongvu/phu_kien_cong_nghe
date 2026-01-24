document.addEventListener("DOMContentLoaded", function () {

    const variantItems = document.querySelectorAll(".variant-item");
    const mainImage = document.getElementById("mainProductImage");
    const thumbnails = document.querySelectorAll(".thumbnail-list .thumb");
    const qtyInput = document.querySelector(".quantity-input");
    const qtyBtns = document.querySelectorAll(".quantity-btn");

    variantItems.forEach(item => {
        item.addEventListener("click", function () {
            const dataVariant = item.querySelector('.dataVariant')
            const price = dataVariant.dataset.price
            const sku = dataVariant.dataset.sku
            const currentPrice = document.querySelector('.current-price')
            const skuCurrent = document.querySelector('.skuCurrent')

            currentPrice.innerText = price
            skuCurrent.innerText = sku

            const imageUrl = this.dataset.image;

            variantItems.forEach(v => v.classList.remove("active"));
            this.classList.add("active");

            const radio = this.querySelector("input[type=radio]");
            if (radio) {
                radio.checked = true;
            }
        });
    });

    qtyBtns.forEach(btn => {
        btn.addEventListener("click", function () {
            let value = parseInt(qtyInput.value) || 1;
            const action = this.dataset.action;

            if (action === "plus") {
                value++;
            } else if (action === "minus" && value > 1) {
                value--;
            }

            qtyInput.value = value;
        });
    });


    thumbnails.forEach(thumb => {
        thumb.addEventListener("click", function () {

            const newSrc = this.getAttribute("src");
            if (newSrc) {
                mainImage.src = newSrc;
            }

            thumbnails.forEach(t => t.classList.remove("active"));
            this.classList.add("active");
        });
    });

});

//SỰ kiện thêm cart
document.getElementById('formAddCart').addEventListener('submit', () => {
    const dataVariants = document.querySelectorAll('.dataVariant')
    const idForm = document.getElementById('formAddCart').idVar
    dataVariants.forEach(btn => {
        if (btn.checked) {
            idForm.value = btn.dataset.id
        }
    });
});