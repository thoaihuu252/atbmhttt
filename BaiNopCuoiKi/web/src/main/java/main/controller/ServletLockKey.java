package main.controller;

import main.bean.Key;
import main.bean.User;
import main.services.keyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ServletLockKey", value = "/ServletLockKey")
public class ServletLockKey extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth");
        Key ifkey = (Key) session.getAttribute("ifkey");

        if (user != null) {
            String action = request.getParameter("action");

            if ("confirm".equals(action)) {
                // Nếu người dùng đã xác nhận
                keyService.getInstance().lockKey(ifkey.getId_key());
                String mess = "Key của bạn đã được VÔ HIÊU HÓA. Bạn không thể sử dụng Key này để xác thực các giao dịch tiếp theo.";
                request.setAttribute("message", mess);
                request.getRequestDispatcher("ServletGetKeyInfor").forward(request, response);
            } else {
                // Nếu chưa xác nhận, hiển thị trang xác nhận
                request.setAttribute("key", ifkey);
                request.getRequestDispatcher("/confirmLockKey.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("/DangNhap.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Có thể xử lý thêm trong doPost nếu cần
    }
}