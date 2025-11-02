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
        containerSliderShowItem.style.transform = `translateX(${indexCur*-width}px)`
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

//Fake data: start
//Fake phụ kiện điện thoại
const pkdts = [
    {
        nameProduct: 'Giá đỡ điện thoại kim loại',
        price: '40.000đ',
        image: '../assets/image/fake_products/item_pkdt_1.webp'
    },
    {
        nameProduct: 'Ốp lưng điện thoại IPhone',
        price: '140.000đ',
        image: '../assets/image/fake_products/item_pkdt_2.webp'
    },
    {
        nameProduct: 'Quạt tản nhiệt điện thoại',
        price: '90.000đ',
        image: '../assets/image/fake_products/item_pkdt_3.webp'
    },
    {
        nameProduct: 'Kính cường lực kinh kông',
        price: '20.000đ',
        image: '../assets/image/fake_products/item_pkdt_4.webp'
    },
    {
        nameProduct: 'Dây đeo cho điện thoại',
        price: '10.000đ',
        image: '../assets/image/fake_products/item_pkdt_5.webp'
    },
    {
        nameProduct: 'Dây đính đá gắn vào điện thoại',
        price: '200.000đ',
        image: '../assets/image/fake_products/item_pkdt_6.webp'
    }
]
//Fake phụ kiện máy tính
const pkmts = [
    {
        nameProduct: 'Giá đỡ laptop kim loại',
        price: '140.000đ',
        image: '../assets/image/fake_products/item_pkmt_1.webp'
    },
    {
        nameProduct: 'Tai nghe chụp G58/G70 Pro',
        price: '240.000đ',
        image: '../assets/image/fake_products/item_pkmt_2.webp'
    },
    {
        nameProduct: 'Dụng cụ 10in1 vệ sinh máy tính',
        price: '90.000đ',
        image: '../assets/image/fake_products/item_pkmt_3.webp'
    },
    {
        nameProduct: 'Lót chuột họa tiết hoa hồng',
        price: '50.000đ',
        image: '../assets/image/fake_products/item_pkmt_4.webp'
    },
    {
        nameProduct: 'Webcam',
        price: '110.000đ',
        image: '../assets/image/fake_products/item_pkmt_5.webp'
    },
    {
        nameProduct: 'Túi chống sốc cho laptop',
        price: '100.000đ',
        image: '../assets/image/fake_products/item_pkmt_6.webp'
    }
]
//Fake bộ chia tín hiệu
const bcths = [
    {
        nameProduct: 'Switch để bàn TP-Link LS1005G 5 cổng 10/100/1000Mbps',
        price: '300.000đ',
        image: '../assets/image/fake_products/item_bcth_1.webp'
    },
    {
        nameProduct: 'Bộ chia tín hiệu AV (Video và Audio) 1 ra 8',
        price: '240.000đ',
        image: '../assets/image/fake_products/item_bcth_2.webp'
    },
    {
        nameProduct: 'Bộ chia tín hiệu âm thanh RCA 2 trong 4 R/L',
        price: '300.000đ',
        image: '../assets/image/fake_products/item_bcth_3.webp'
    },
    {
        nameProduct: 'Bộ gộp tín hiệu HDMI 2.0 4k@60Hz',
        price: '400.000đ',
        image: '../assets/image/fake_products/item_bcth_4.webp'
    },
    {
        nameProduct: 'Bộ chia tín hiệu camera, 2 camera lề, 1 camera lùi',
        price: '170.000đ',
        image: '../assets/image/fake_products/item_bcth_5.webp'
    },
    {
        nameProduct: 'Bộ khuếch đại truyền hình cáp, chia tín hiệu tivi',
        price: '270.000đ',
        image: '../assets/image/fake_products/item_bcth_6.webp'
    }
]
//Fake sản phẩm mới nhất
const spmns = [
    {
        nameProduct: 'Switch để bàn TP-Link LS1005G 5 cổng 10/100/1000Mbps',
        price: '300.000đ',
        image: '../assets/image/fake_products/item_bcth_1.webp'
    },
    {
        nameProduct: 'Túi chống sốc cho laptop',
        price: '100.000đ',
        image: '../assets/image/fake_products/item_pkmt_6.webp'
    },
    {
        nameProduct: 'Kính cường lực kinh kông',
        price: '20.000đ',
        image: '../assets/image/fake_products/item_pkdt_4.webp'
    },
    {
        nameProduct: 'Bộ gộp tín hiệu HDMI 2.0 4k@60Hz',
        price: '400.000đ',
        image: '../assets/image/fake_products/item_bcth_4.webp'
    },
    {
        nameProduct: 'Bộ chia tín hiệu camera, 2 camera lề, 1 camera lùi',
        price: '170.000đ',
        image: '../assets/image/fake_products/item_bcth_5.webp'
    },
    {
        nameProduct: 'Webcam',
        price: '110.000đ',
        image: '../assets/image/fake_products/item_pkmt_5.webp'
    }
]
//Fake sản phẩm nổi bật
const spnbs = [
    {
        nameProduct: 'Giá đỡ điện thoại kim loại',
        price: '40.000đ',
        image: '../assets/image/fake_products/item_pkdt_1.webp'
    },
    {
        nameProduct: 'Bộ chia tín hiệu AV (Video và Audio) 1 ra 8',
        price: '240.000đ',
        image: '../assets/image/fake_products/item_bcth_2.webp'
    },
    {
        nameProduct: 'Dây đeo cho điện thoại',
        price: '10.000đ',
        image: '../assets/image/fake_products/item_pkdt_5.webp'
    },
    {
        nameProduct: 'Bộ gộp tín hiệu HDMI 2.0 4k@60Hz',
        price: '400.000đ',
        image: '../assets/image/fake_products/item_bcth_4.webp'
    },
    {
        nameProduct: 'Lót chuột họa tiết hoa hồng',
        price: '50.000đ',
        image: '../assets/image/fake_products/item_pkmt_4.webp'
    },
    {
        nameProduct: 'Webcam',
        price: '110.000đ',
        image: '../assets/image/fake_products/item_pkmt_5.webp'
    }
]

const sectionsData = [spmns, spnbs, pkdts, pkmts, bcths]

const renderDataIntoSections = () => {
    //section newProducts
    let newProducts = document.getElementById('newProducts')
    let listProductByNewProducts = newProducts.querySelector('.list-product-by-section')
    //section featuredProducts
    let featuredProducts = document.getElementById('featuredProducts')
    let listProductByFeturedProducts = featuredProducts.querySelector('.list-product-by-section')
    //section phoneProducts
    let phoneProducts = document.getElementById('phoneProducts')
    let listProductByPhoneProducts = phoneProducts.querySelector('.list-product-by-section')
    //section computerProducts
    let computerProducts = document.getElementById('computerProducts')
    let listProductByComputerProducts = computerProducts.querySelector('.list-product-by-section')
    //section signalSplitterProducts
    let signalSplitterProducts = document.getElementById('signalSplitterProducts')
    let listProductBySignalSplitterProducts = signalSplitterProducts.querySelector('.list-product-by-section')

    const listOfSections = [
        listProductByNewProducts,
        listProductByFeturedProducts,
        listProductByPhoneProducts,
        listProductByComputerProducts,
        listProductBySignalSplitterProducts
    ]

    //lấy ra cấu trúc html của itemProduct.html
    fetch('../shared/itemProduct.html')
        .then(response => response.text())
        .then(data => {
            //duyệt qua từng list-product-by-section của mỗi section tương ứng
            for (let i = 0; i < listOfSections.length; i++) {
                //tại vị trí của list-product-by-section ta lấy ra tập data tương ứng với section chứa nó
                for (let j = 0; j < sectionsData[i].length; j++) {
                    //tiến hành bơm dữ liệu vào itemProduct.html
                    const divContain = document.createElement('div')
                    divContain.innerHTML = data //đổ html itemProduct.html vào thẻ div này
                    //lấy các thẻ bên itemProduct.html để đổ dữ liệu vào
                    divContain.querySelector('.image-product-item img').src = sectionsData[i][j].image
                    divContain.querySelector('.title-product-item').textContent = sectionsData[i][j].nameProduct
                    divContain.querySelector('.price-product-item').textContent = sectionsData[i][j].price

                    //sau đó gắn nó vào list quản lí sản phẩm tương ứng
                    listOfSections[i].appendChild(divContain)
                }
            }
        })
}

renderDataIntoSections()
