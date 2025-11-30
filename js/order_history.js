arr = Array.from(document.getElementsByClassName('wrap-content-order'))
for(let item of arr) {
    item.addEventListener('click', (e) => {
        window.location.href = 'order_detail.html'
    })
}