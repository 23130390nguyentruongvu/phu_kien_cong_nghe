//Carousel: start
//Lay ra danh sach cac item carousel kha dung
const carousel = document.querySelector('.carousel-items')
const carouselButtons = document.querySelector('.carousel-buttons')
const listButtonsCarousel = []
const listItemCarousel = carousel.getElementsByClassName('carousel-item')

for (let i = 0; i < listItemCarousel.length; i++) {
    //Tao ra the span dai dien cho button dieu huong carousel
    let carouselButton = document.createElement('span')
    carouselButton.classList.add('carousel-button')

    //Xử lí sự kiện carousel button được click
    carouselButton.addEventListener('click', (evt) => {
        //Đưa về trạng thái không show
        listButtonsCarousel.forEach((item, index) => {
            listItemCarousel[index].classList.remove('carousel-item-show')
            item.classList.remove('carousel-button-show')
        })
        //Thiết lập lại show cho button được click với image hiện tương ứng
        const btnClicked = evt.currentTarget //Lay ra button vua duoc click
        evt.target.classList.add('carousel-button-show')
        let indexAt = listButtonsCarousel.indexOf(btnClicked)
        listItemCarousel[indexAt].classList.add('carousel-item-show')
    })

    listButtonsCarousel.push(carouselButton)
    carouselButtons.appendChild(carouselButton)
    //Mặc định button và image đầu tiên sẽ được hiển thị khi load vào trang
    listItemCarousel[0].classList.add('carousel-item-show')
    listButtonsCarousel[0].classList.add('carousel-button-show')
}
//Carousel: end

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
        image: '../assets/image/fake_products/item_pkdt_5.webp'
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
        image: '../assets/image/fake_products/item_pkdt_5.webp'
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
        image: '../assets/image/fake_products/item_pkdt_5.webp'
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
