package main.controller;

import main.bean.*;
import main.services.AddOderService;
import main.services.SignaruteService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@WebServlet(name = "ServletResetFormoder", value = "/ServletResetFormoder")
public class ServletResetFormoder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
       int stage = Integer.parseInt(request.getParameter("stage")) ;
       if (stage==0) {
           ApiController control = new ApiController();
           request.setAttribute("listdiachi1", control.getDistrictbyPvID("202"));
           ArrayList<APIwardRespone.data> rs = control.getWardbyDTid(request.getParameter("diachi1").toString());
           request.setAttribute("listdiachi2", rs);
           request.setAttribute("stage",1);
           request.getRequestDispatcher("/GioHang.jsp").forward(request, response);
       }
       if (stage==1){
           User user = (User) session.getAttribute("auth");
           Cart cart = (Cart) session.getAttribute("cart");
           String adrs1 = request.getParameter("diachi1");
           String adrs2 = request.getParameter("diachi2");
           String signature = request.getParameter("signature");
           String vouch = (String) request.getParameter("voucher");
           System.out.println("signature 1 : "+ signature);
           if (user!=null) {
               if (signature != null){
                   try {
                       String keyUser = SignaruteService.getInstance().getPublicKeyFromUser(user.getUserId());
                       System.out.println("keyUser 1 : "+ keyUser);
                       // hash don hang
                       String hashData = SignaruteService.getInstance().createHashSignature(user, cart, adrs1, adrs2);
                       System.out.println("HashData 1 : " + hashData);
                       // ma hoa hash bang privatekey
                       String encryptHash = SignaruteService.getInstance().encryptHashDataSignature(hashData, signature);
                       // verify
                       boolean verify = SignaruteService.getInstance().verifySignature(hashData, encryptHash, keyUser);
                       System.out.println("verify : " + verify);
                       if (verify) {
                           if (AddOderService.getInstance().adODer(user.getIdacc(), cart, vouch, adrs1, adrs2)) {
                               request.setAttribute("error", "Thanh toán thành công");
                               session.setAttribute("cart", new Cart());
                               request.getRequestDispatcher("./yah.html").forward(request, response);

                           } else {
                               request.setAttribute("error", "Thanh toán thất bại");
                               response.sendRedirect("./404ne.html");
                           }
                       } else {
                           request.setAttribute("error", "Xác thực chữ ký thất bại");
                           request.getRequestDispatcher("./getIndex").forward(request, response);
                       }
                   } catch (Exception e) {
                       throw new RuntimeException(e);
                   }
               } else {
                   System.out.println("signature null "+ signature);
                   if (AddOderService.getInstance().adODer(user.getIdacc(), cart, vouch,adrs1,adrs2)) {
                       request.setAttribute("error", "Thanh toán thành công");
                       session.setAttribute("cart", new Cart());
                       request.getRequestDispatcher("./yah.html").forward(request, response);
                   } else {
                       request.setAttribute("error", "Thanh toán thất bại");
                       response.sendRedirect("./404ne.html");
                   }
               }
           }else{
               request.setAttribute("error", "Vui lòng đang nhập để thanh toán");
               request.getRequestDispatcher("./getIndex").forward(request, response);
           }
       }
    }
}
