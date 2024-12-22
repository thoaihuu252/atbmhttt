<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.bean.Key" %>
<%@ page import="main.bean.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Xác nhận Vô hiệu hóa Key</title>
    <!-- Link CSS Bootstrap (bạn có thể dùng CDN hoặc file local) -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <!-- Thẻ card để bọc nội dung -->
            <div class="card shadow-sm">
                <div class="card-header bg-danger text-white text-center">
                    <h4>Xác nhận Vô hiệu hóa Key</h4>
                </div>
                <div class="card-body">
                    <!-- Nội dung thông báo -->
                    <p class="lead text-center mb-4">
                        Bạn có chắc chắn muốn vô hiệu hóa Key này?
                    </p>

                    <!-- Hiển thị ID Key -->
                    <p class="text-center">
                        <span class="text-muted">
                              <p class="lead text-center mb-5">
                      KHi Key này của bạn đã được VÔ HIÊU HÓA. Bạn không thể sử dụng Key này để xác thực các giao dịch tiếp theo.
                    </p>

                        </span>
                    </p>

                    <!-- Form xác nhận khoá Key -->
                    <form action="ServletLockKey" method="get" class="text-center mt-4">
                        <input type="hidden" name="action" value="confirm"/>

                        <button type="submit" class="btn btn-danger mx-2">
                            Xác nhận
                        </button>
                        <a href="/key.jsp" class="btn btn-secondary mx-2">
                            Hủy bỏ
                        </a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Link JS Bootstrap (nếu cần) -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
