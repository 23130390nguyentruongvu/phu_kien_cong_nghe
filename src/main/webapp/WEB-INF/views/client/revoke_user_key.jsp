<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thông báo trạng thái xác nhận</title>
</head>
<body style="font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f3f4f6; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0;">

<div class="container" style="background: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); text-align: center; width: 100%; max-width: 500px;">
    <h2 style="margin-top: 0; color: #1f2937; margin-bottom: 20px;">Kết Quả Xử Lý</h2>
        <div id="notificationAlert" style="display: flex; align-items: center; justify-content: space-between; padding: 15px 20px; margin-bottom: 20px; border-radius: 4px; font-size: 15px; font-weight: 500; background-color: #ffffff; border: 1px solid #e5e7eb; transition: opacity 0.3s ease;">
            <div style="display: flex; align-items: center; gap: 12px;">
                <span style="text-align: left;">${requestScope.message}</span>
            </div>
        </div>
</div>
</body>
</html>