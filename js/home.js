//Carousel start
//Lay ra danh sach cac item carousel kha dung
const carousel = document.querySelector('.carousel-items')
const carouselButtons = document.querySelector('.carousel-buttons')
const listButtonsCarousel = []
const listItemCarousel = carousel.getElementsByClassName('carousel-item')

for (let i = 0; i < listItemCarousel.length; i++) {
    //Tao ra the span dai dien cho button dieu huong carousel
    let carouselButton = document.createElement('span')
    carouselButton.classList.add('carousel-button')

    //Su li su kien button carosel duoc click
    carouselButton.addEventListener('click', (evt) => {
        //Xoa tat ca class cua cac carouselButton va carouselItem la show
        listButtonsCarousel.forEach((item, index) => {
            listItemCarousel[index].classList.remove('carousel-item-show')
            item.classList.remove('carousel-button-show')
        })
        //Set lai class show cho button duoc click
        const btnClicked = evt.currentTarget //Lay ra button vua duoc click
        evt.target.classList.add('carousel-button-show') //Thiet lap cho button do mau sac ro rang hon cac button khac
        let indexAt = listButtonsCarousel.indexOf(btnClicked)
        listItemCarousel[indexAt].classList.add('carousel-item-show')
    })

    listButtonsCarousel.push(carouselButton)
    carouselButtons.appendChild(carouselButton)
    //Mac dinh item 1 va button carousel tai index 0 la show
    listItemCarousel[0].classList.add('carousel-item-show')
    listButtonsCarousel[0].classList.add('carousel-button-show')
}
//Carousel end