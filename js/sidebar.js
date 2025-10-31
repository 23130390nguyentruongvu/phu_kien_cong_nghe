// shared/sidebar.js
document.addEventListener("DOMContentLoaded", function () {
  const sidebarContainer = document.getElementById("sidebar");
  if (sidebarContainer) {
    fetch("../../shared/sidebar.html")
      .then(response => {
        if (!response.ok) throw new Error("Không thể tải sidebar");
        return response.text();
      })
      .then(html => {
        sidebarContainer.innerHTML = html;
      })
      .catch(error => console.error("Lỗi khi tải sidebar:", error));
  }
});
