<%@ page import="main.bean.User" %>
<%@ page import="main.bean.Products" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="main.bean.Order" %>
<%@ page import="main.bean.Key" %><%--
  Created by IntelliJ IDEA.
  User: thoai
  Date: 7/01/2023
  Time: 12:47 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!DOCTYPE html>
<html lang="zxx">

<head>
  <meta charset="UTF-8">
  <meta name="description" content="Ogani Template">
  <meta name="keywords" content="Ogani, unica, creative, html">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>SBE-Cơm Trưa</title>

  <!-- Google Font -->
  <link href="https://fonts.googleapis.com/css2?family=Cairo:wght@200;300;400;600;900&display=swap" rel="stylesheet">
  <script src="https://kit.fontawesome.com/b99e675b6e.js"></script>
  <!-- Css Styles -->
  <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
  <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
  <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
  <link rel="stylesheet" href="css/nice-select.css" type="text/css">
  <link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
  <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
  <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
  <link rel="stylesheet" href="css/style.css" type="text/css">
  <link rel="stylesheet" href="css/index.css"type="text/css">
  <link rel="stylesheet" href="css/User.css"type="text/css">

</head>

<body>


<!-- Header Section Begin -->
<header class="header">
  <div class="header__top">
    <div class="container">
      <div class="row">
        <div class="col-lg-6 col-md-6">
          <div class="header__top__left">
            <ul>
              <li><i class="fa fa-envelope"></i> Email: 201*046*</li>
              <li>Miễn phí giao hàng trong ngày 20-11</li>
            </ul>
          </div>
        </div>
        <div class="col-lg-6 col-md-6">
          <div class="header__top__right">
            <div class="header__top__right__social">
              <a href="#"><i class="fa fa-facebook"></i></a>
              <a href="#"><i class="fa fa-twitter"></i></a>
              <a href="#"><i class="fa fa-instagram"></i></a>
              <a href="#"><i class="fa fa-youtube-play"></i></a>
            </div>
            <div class="header__top__right__language">
              <img src="img/language.png" alt="">
              <div>Việt Nam </div>
            </div>
            <div class="header__top__right__auth">
              <%
                String a = (String) session.getAttribute("login");
                User user = (User) session.getAttribute("auth");
                Key ifkey= (Key) session.getAttribute("ifkey");
                if(ifkey==null){
                  ifkey= new Key("Chưa Tạo","");
                }

                ArrayList<Order> list = (ArrayList<Order>) session.getAttribute("alloder");
                String mess = (String)request.getAttribute("message");

              %>
              <%
                if (mess!=null) {
              %>
              <script>
                alert(" <%=mess%>");
              </script>
              <%
                }
              %>
              <%
                if (a == null) {
              %>
              <a href="DangNhap.jsp"><i class="fa fa-user"></i> Đăng Nhập</a>
              <%
              }else{
              %>
              <div class="fa fa-user" role="alert">
                <%= a+user.getName() %>
              </div>
              <%
                }
              %>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="container">
    <div class="row">
      <div class="col-lg-3">
        <div class="header__logo">
          <a href="getIndex"><img src="img/a.png" alt=""></a>
        </div>
      </div>
      <div class="col-lg-6">
        <nav class="header__menu">
          <ul>
            <li ><a href="getIndex">Trang Chủ</a></li>
            <li ><a href="getAllProduct">Gian Hàng</a></li>

            <li><a href="blog.jsp">Giới Thiệu</a></li>
            <li><a href="contact.jsp">Liên Hệ</a></li>
          </ul>
        </nav>
      </div>
      <div class="col-lg-3">
        <div class="header__cart">
          <ul>
            <li><a href="getUserInfor"><i class="fa fa-user"></i></a></li>
            <li><a href="showCart"><i class="fa fa-shopping-bag"></i> <span>3</span></a></li>
          </ul>
          <div class="header__cart__price"></div>
        </div>
      </div>
    </div>
    <div class="humberger__open">
      <i class="fa fa-bars"></i>
    </div>
  </div>
</header>
<!-- Header Section End -->


<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-section set-bg" data-setbg="img/ad.jpg">
  <div class="container">
    <div class="row">
      <div class="col-lg-12 text-center">
        <div class="breadcrumb__text">
          <h2>SBE Shop</h2>
          <div class="breadcrumb__option">
            <a href="./index.html">Trang chủ</a>
            <span>User</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<!-- Breadcrumb Section End -->
<section class="product spad">
  <div class="userr">
    <div class="container">
      <div class="row">


        <div class="align-self-start">
          <div class="sidebar">
            <div class="sidebar__item">
              <ul>
                <li ><a href="getUser"><i class="fa fa-user-circle-o" aria-hidden="true"></i> Thông Tin </a></li>
                <li><a href="ServletGetKeyInfor"><i class="fa fa-shopping-bag" aria-hidden="true"></i> Chữ Ký </a></li>
                <li><a href="getUIFOder"><i class="fa fa-shopping-bag" aria-hidden="true"></i> Đơn Hàng </a></li>
                <li><a href="getAllFavourite"><i class="fa fa-heart" aria-hidden="true"></i> Yêu Thích </a></li>
                <li><a href="Doimk"><i class="fa fa-refresh" aria-hidden="true"></i>Đổi Mật Khẩu</a></li>
                <li><a href="GetVoucherUser"><i class="fa fa-gift" aria-hidden="true"></i>Voucher</a></li>
              </ul>
            </div>
          </div>
        </div>

        <div class="col-lg-10 col-md-5" style="height: 364px;">
          <!-- Thong tin -->
          <!-- Thong tin -->
          <!-- Thong tin -->
          <%
            User userID = (User) session.getAttribute("userID");
          %>
          <div class="wrapper">
            <div class="left">
              <img src="<%=userID.getAvatar()%>" alt="user" width="100">
              <h4><%=user.getUserName()%></h4>
              <p></p>
            </div>
            <div class="right">
              <div class="info">

                <div class="chinhsuainfo"> <h3>Thông tin</h3>    <a href="ServletLockKey" style="background-color: #2ec791;margin-left: 23%;height: 40px;width: 100px;color: #000000;border-radius: 3px;padding: 8px 0px 5px 15px;">Vô hiệu hóa</a></div>

                <div class="info_data"> <div class="data">
                  <h4> Trạng thái </h4>
                  <p><%=ifkey.getStatus()%></p>
                </div>
                  <div class="data">
                    <h4> Ngày Active </h4>
                    <p><%=ifkey.getTimeActive()%></p>
                  </div>
                </div>

                <div class="projects_data">
                  <% if (ifkey.getStatus().equals("NEED TO CREATE")){
                  %>
                  <div class="data">
                    <h4>Tạo khóa </h4>
                    <a href="ServletCreateKey" style="background-color: #2ec791;height: 40px;width: 100px;color: #000000;border-radius: 3px;padding: 8px 14px 5px 15px;">Tạo Khóa</a>
                  </div>
                  <%
                    }else{
                  %>
                  <div class="data">
                    <h4>Tạo khóa khác</h4>
                    <a href="ServletCreateKey" style="background-color: #2ec791;height: 40px;width: 100px;color: #000000;border-radius: 3px;padding: 8px 14px 5px 15px;">Thay khóa khác</a>
                  </div>
                  <%
                    }
                  %>
                  <div class="data">
                    <h4>Sử dụng Khóa của bạn</h4>
                    <form id="signatureForm" action="ServletAddKeyFromUser" method="POST">
                      <div>
                        <input type="text" id="signatureInput" placeholder="Nhập chữ ký điện tử của bạn" style="width: 100%; margin-bottom: 10px;"><br>
                        <label for="signatureFile">Hoặc chọn file .pem chứa chữ ký:</label><br>
                        <input type="file" id="signatureFile" accept=".pem" style="width: 100%; margin-bottom: 10px;"><br>
                      </div>
                      <div class="data">
                        <button type="button" onclick="handleSignatureSubmit()" style="background-color: #2ec791; height: 40px; width: 100px; color: #000000; border-radius: 3px; padding: 8px 14px 5px 15px;">Gửi</button>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

          </div>

          <!--  End -->


        </div>



    </div>
  </div>

  </div>
</section>
<!-- Footer Section Begin -->
<footer class="footer spad">
  <div class="container">
    <div class="row">
      <div class="col-lg-3 col-md-6 col-sm-6">
        <div class="footer__about">
          <div class="footer__about__logo">
            <a href="./index.html"><img src="img/a.png" alt=""></a>
          </div>
          <ul>
            <li>Địa chỉ: Khu Phố 6, Thủ Đức, Thành phố Hồ Chí Minh</li>
            <li>Số điện thoại: +*********</li>
            <li>Email: 201*046*</li>
          </ul>
        </div>
      </div>
      <div class="col-lg-4 col-md-6 col-sm-6 offset-lg-1">
        <div class="footer__widget">
          <h6>Liên kết hữu ích</h6>
          <ul>
            <li ><a href="./index.html">Trang Chủ</a></li>
            <li><a href="./shop-grid.html">Gian Hàng</a></li>
            <li><a href="./blog.html">Giới Thiệu</a></li>
            <li><a href="./contact.html">Liên Hệ</a></li>
          </ul>
        </div>
      </div>
      <div class="col-lg-4 col-md-12">
        <div class="footer__widget">
          <h6>Cập Nhật Thông Tin</h6>
          <p>Nhận thông tin cập nhật e-mail về cửa hàng mới nhất của chúng tôi và các ưu đãi đặc biệt của chúng tôi.</p>
          <form action="#">
            <input type="text" placeholder="Nhập Email">
            <button type="submit" class="site-btn">Xác Nhận</button>
          </form>
          <div class="footer__widget__social">
            <a href="#"><i class="fa fa-facebook"></i></a>
            <a href="#"><i class="fa fa-instagram"></i></a>
            <a href="#"><i class="fa fa-twitter"></i></a>
            <a href="#"><i class="fa fa-pinterest"></i></a>
          </div>
        </div>
      </div>
    </div>
  </div>
</footer>
<!-- Footer Section End -->

<!-- Js Plugins -->
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.nice-select.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/jquery.slicknav.js"></script>
<script src="js/mixitup.min.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/main.js"></script>

<script>
  function handleSignatureSubmit() {
    const signatureInput = document.getElementById("signatureInput").value.trim();
    const fileInput = document.getElementById("signatureFile").files[0];
    const form = document.getElementById("signatureForm");

    // Kiểm tra nếu cả hai đều trống
    if (!signatureInput && !fileInput) {
      alert("Vui lòng nhập chữ ký hoặc chọn file!");
      return; // Ngừng xử lý nếu cả hai đều trống
    }

    // Tạo hidden input cho chữ ký từ input text
    if (signatureInput) {
      const hiddenInput = document.createElement("input");
      hiddenInput.type = "hidden";
      hiddenInput.name = "signatureFromInput";
      hiddenInput.value = signatureInput; // Giá trị chữ ký từ input
      form.appendChild(hiddenInput);
    }

    if (fileInput) {
      const allowedExtensions = ["pem"]; // Chỉ cho phép file .pem
      const fileExtension = fileInput.name.split(".").pop().toLowerCase();

      // Kiểm tra định dạng file
      if (!allowedExtensions.includes(fileExtension)) {
        alert("Vui lòng chọn file định dạng .pem!");
        return; // Ngừng xử lý nếu định dạng file không hợp lệ
      }

      // Đọc file và thêm hidden input cho file
      const reader = new FileReader();
      reader.onload = function (e) {
        const fileContent = e.target.result.trim();

        const hiddenInputFile = document.createElement("input");
        hiddenInputFile.type = "hidden";
        hiddenInputFile.name = "signatureFromFile";
        hiddenInputFile.value = fileContent; // Nội dung đã xử lý từ file
        form.appendChild(hiddenInputFile);

        form.submit(); // Gửi form sau khi file được đọc xong
      };
      reader.readAsText(fileInput);
    } else {
      // Nếu không có file, chỉ gửi form với chữ ký từ text
      form.submit();
    }
  }
</script>

</body>

</html>
</html>
