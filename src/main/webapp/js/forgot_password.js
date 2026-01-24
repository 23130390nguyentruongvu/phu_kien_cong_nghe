import {hideLoading, showLoading} from "./overlay_processing.js";

const contextPath = window.contextPath

//thiết lập sự kiện click thẻ a quên mật khẩu
document.querySelector('.forgot-pass').addEventListener('click', async (e) => {
    e.preventDefault()

    const email = prompt("Vui lòng nhập email của bạn:");
    if (email === null) return;
    if (!email.includes("@")) {
        alert("Email không đúng định dạng")
        return;
    }
    showLoading()
    const res = await fetch(`${contextPath}/forgot-email`, {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: new URLSearchParams({email: email})
    });

    const resJson = await res.json()
    hideLoading()
    alert(resJson.message);
});