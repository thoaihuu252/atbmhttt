package main.controller;

import main.bean.Key;
import main.bean.User;
import main.services.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

@WebServlet(name = "ServletAddKeyFromUser", value = "/ServletAddKeyFromUser")
public class ServletAddKeyFromUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth");
        String signatureFromFile = request.getParameter("signatureFromFile");
        String signatureFromInput = request.getParameter("signatureFromInput");
        String signature = SignaruteService.getInstance().getSignatureFromForm(signatureFromInput,signatureFromFile);
        System.out.println("signatureFromFile : "+ signatureFromFile);
        System.out.println("signatureFromInput : "+ signatureFromInput);
        System.out.println("signature : "+ signature);

        String keyNow = SignaruteService.getInstance().getIdKeyUser(user.getUserId());
        System.out.println("thành công get key : "+ keyNow);
        keyService.getInstance().importNewKey(user.getUserId(),signature);
        System.out.println("imporrt thành công"+ keyNow);
        SignaruteService.getInstance().updateKeyStatus(keyNow);
        String iduser = user.getUserId();
        Key ifkey= keyService.getInstance().getKey(iduser);
        session.setAttribute("ifkey",ifkey);
        request.getRequestDispatcher("/key.jsp").forward(request, response);


    }
}

