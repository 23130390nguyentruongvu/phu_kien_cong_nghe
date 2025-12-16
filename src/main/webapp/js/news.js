arr = Array.from(document.getElementsByClassName('news-card'))
for(let item of arr) {
    item.addEventListener('click', (e) => {
        window.location.href = '' +
            '' +
            'news_detail.jsp'
    })
}