//Slider show: start
//Thiết lập thời gian cập nhật image
const containerSliderShowItem =
    document.querySelector('.slider-show-items')
const items =
    containerSliderShowItem.getElementsByClassName('slider-show-item')
const containerDots = document.querySelector('.index-dots')

let width = items[0].offsetWidth
let indexCur = 0

//Thiết lập index dot
for (let i = 0; i < items.length; i++) {
    //<div class="dot"></div>
    const dot = document.createElement('div')
    dot.classList.add('dot')
    if (i === indexCur) dot.classList.add('active')
    dot.addEventListener('click', () => {
        clearInterval(currentInterval)
        indexCur = i
        containerSliderShowItem.style.transform = `translateX(${indexCur * -width}px)`
        currentInterval = setupIntervalSlider()
        updateActiveDot()
    })
    containerDots.appendChild(dot)
    console.log(containerDots.innerHTML)
}

//Phương thức cập nhật active dot
const updateActiveDot = () => {
    let dots = containerDots.getElementsByClassName('dot')
    //Xóa active và cập nhật lại active
    for (let i = 0; i < dots.length; i++) {
        if (i === indexCur)
            dots[i].classList.add('active')
        else
            dots[i].classList.remove('active')
    }
}

//Thiết lập Interval
const setupIntervalSlider = () => {
    return setInterval(() => {
        if (indexCur < 0) {
            containerSliderShowItem.style.transform = `translateX(${width * -1 * (items.length - 1)}px)`
        } else if (indexCur >= items.length - 1) {
            containerSliderShowItem.style.transform = `translateX(${0}px)`
            indexCur = 0
        } else {
            containerSliderShowItem.style.transform = `translateX(${width * -1 * ++indexCur}px)`
        }
        updateActiveDot()
    }, 3000)
}

let currentInterval = setupIntervalSlider()

//Thiết lập sự kiện cho chuyển ảnh trái phải trên slider show
const navLeft = () => {
    clearInterval(currentInterval)
    if (indexCur - 1 < 0)
        indexCur = items.length - 1
    else
        indexCur--
    containerSliderShowItem.style.transform = `translateX(${-width * indexCur}px)`
    updateActiveDot()
    currentInterval = setupIntervalSlider()
}
const rightNav = () => {
    clearInterval(currentInterval)
    if (indexCur + 1 > items.length - 1)
        indexCur = 0
    else
        indexCur++
    containerSliderShowItem.style.transform = `translateX(${-width * indexCur}px)`
    updateActiveDot()
    currentInterval = setupIntervalSlider()
}
//Slider show: end
//Event click product
arr = Array.from(document.getElementsByClassName('container-product-item'))
for (let item of arr) {
    console.log(item)
    item.addEventListener('click', (e) => {
        window.location.href = 'product_detail.html'
    })
}
//Event show more
arr = Array.from(document.getElementsByClassName('show-more-section'))
for (let item of arr) {
    console.log(item)
    item.addEventListener('click', (e) => {
        window.location.href = 'product_category.html'
    })
}

wrapUser = document.getElementsByClassName('wrap-state-user')[0]
wrapUser.addEventListener('click', (e) => {
    window.location.href = 'personal_info.html'
})