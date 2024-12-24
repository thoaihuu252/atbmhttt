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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
           String idKeyUser = SignaruteService.getInstance().getIdKeyUser(user.getUserId());
           Cart cart = (Cart) session.getAttribute("cart");
           String adrs1 = request.getParameter("diachi1");
           System.out.println("adrs1 : "+ adrs1);
           String adrs2 = request.getParameter("diachi2");
           System.out.println("adrs2 : "+ adrs2);
           String signatureFromFile = request.getParameter("signatureFromFile");
           String signatureFromInput = request.getParameter("signatureFromInput");
           String signature =SignaruteService.getInstance().getSignatureFromForm(signatureFromInput,signatureFromFile);
           String vouch = (String) request.getParameter("voucher");
           System.out.println("signatureFromFile : "+ signatureFromFile);
           System.out.println("signatureFromInput : "+ signatureFromInput);
           System.out.println("signature : "+ signature);
           long currentTimeMillis = System.currentTimeMillis();
           java.sql.Timestamp timestamp = new java.sql.Timestamp(currentTimeMillis);
           if (user!=null) {
               if (signature != null){
                   try {
                       String keyUser = SignaruteService.getInstance().getPublicKeyFromUser(user.getUserId());
                       System.out.println("keyUser 1 : "+ keyUser);
                       // hash don hang
                       HashMap<String, Products> data = cart.getData();
                       ArrayList<OderCart> listproduct= new ArrayList<>();
                       List<OderCart> tempList = new ArrayList<>();
                       for (Map.Entry<String, Products> entry : data.entrySet()) {
                           Products item = entry.getValue();
                           int quantity = item.getQuantity();
                           Products product= new Products(item.getID_food(),item.getPrice(),item.getFoodName());

                           // Tạo đối tượng OrderCart với sản phẩm và số lượng
                           OderCart orderCart = new OderCart(product, quantity);
                           tempList .add(orderCart);
                       }
                       tempList.sort((o1, o2) -> o1.getItem().getID_food().compareTo(o2.getItem().getID_food()));
                       listproduct.addAll(tempList);
                       long total = cart.getTotal();
                       int quanity = cart.getQuantity();
                       String hashData = SignaruteService.getInstance().createHashSignature(user, listproduct,total,quanity, adrs1, adrs2,timestamp);
                       System.out.println("HashData 1 : " + hashData);
                       // ma hoa hash bang privatekey
                       String encryptHash = SignaruteService.getInstance().encryptHashDataSignature(hashData, signature);
                       // verify
                       boolean verify = SignaruteService.getInstance().verifySignature(hashData, encryptHash, keyUser);
                       System.out.println("verify : " + verify);
                       if (verify) {
                           if (AddOderService.getInstance().adODer(user.getIdacc(), cart, vouch, adrs1, adrs2,encryptHash,timestamp,idKeyUser,"Có chữ ký")) {
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
                   if (AddOderService.getInstance().adODer(user.getIdacc(), cart, vouch,adrs1,adrs2,"N/A",timestamp,"N/A","Không chữ ký")) {
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
