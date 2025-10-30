const addItemToCart = (item) => {
    //Đầu tiên lấy ra value tương úng với key là item-in-cart
    let stringJson = localStorage.getItem('item-in-cart')
    //Nếu giỏ hàng chưa có item nào thì nó là null nên ta tạo mảng rỗng, nếu không null ta tiến hành parse json string
    //về đối tượng mảng
    let value = stringJson === null ? [] : JSON.parse(stringJson);
    //Sau đó push item là object ta được truyền khi ta thiết lập sự kiện click bên home.js
    value.push(item)
    //Tiến hành set lại valuee mới cho item-in-cart
    localStorage.setItem('item-in-cart', JSON.stringify(value));
    console.log(localStorage.getItem('item-in-cart'));
    console.log(typeof item)

    //Sau đó ta cập nhật value của badge giỏ hàng phía header
    const badgeHeader = document.getElementById('badgeNumItemCart')
    badgeHeader.textContent = ''+value.length
}