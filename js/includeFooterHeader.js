const loadFileShared = (filePath, idElement) => {
    fetch(filePath)
        .then(response => response.text()) //load noi dung html
        .then(text => {
            document.getElementById(idElement).innerHTML = text
        })
        .catch(error => console.log(error))
}

//DOMContentLoaded đảm bảo nội dung cây DOM đã tải xong thì thực hiện câu lệnh bên trong nó
document.addEventListener('DOMContentLoaded', () => {
    loadFileShared('../../shared/footer.html', 'footer')
    loadFileShared('../../shared/header.html', 'header')
})