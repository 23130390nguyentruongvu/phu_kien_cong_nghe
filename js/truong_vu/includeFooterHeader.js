const loadFileShared = (filePath, idElement) => {
    fetch(filePath)
        .then(response => response.text()) //load noi dung html
        .then(text => document.getElementById(idElement).innerHTML = text)
        .catch(error => console.log(error))
}

document.addEventListener('DOMContentLoaded', () => {
    loadFileShared('../../shared/footer.html', 'footer')
    loadFileShared('../../shared/header.html', 'header')
})