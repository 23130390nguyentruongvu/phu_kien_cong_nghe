const loadFileShared = (filePath, idElement) => {
    fetch(filePath)
        .then(response => response.text()) //load noi dung html
        .then(text => {
            document.getElementById(idElement).innerHTML = text
            //Ta lấy ra danh sách các item đã được add vào cart để cập nhật lên badge
            let stringJsonValueInCart = localStorage.getItem('item-in-cart')
            let size = stringJsonValueInCart === null ? 0 : JSON.parse(stringJsonValueInCart).length
            const badgeHeader = document.getElementById('badgeNumItemCart')
            badgeHeader.textContent = '' + size
        })
        .catch(error => console.log(error))
}

//DOMContentLoaded đảm bảo nội dung cây DOM đã tải xong thì thực hiện câu lệnh bên trong nó
document.addEventListener('DOMContentLoaded', () => {
    loadFileShared('../../shared/footer.html', 'footer')
    loadFileShared('../../shared/header.html', 'header')
})