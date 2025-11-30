//Event search
arr = Array.from(document.getElementsByClassName('form-search'))
console.log(arr[0])
arr[0].addEventListener('click', (e) => {
    console.log('aa')
    window.location.href = 'search_result.html'
})

faCartShopping = document.getElementsByClassName('fa-cart-shopping')[0]
console.log(faCartShopping)
faCartShopping.addEventListener('click', (e) => {
    window.location.href = 'shopping_cart.html'
})

stateLogin = document.getElementsByClassName('wrap-state-user')[0]
stateLogin.addEventListener('click', (e) => {
    console.log("s")
    window.location.href = 'personal_info.html'
})