package main.services;

import main.bean.Cart;
import main.bean.DataSignature;
import main.bean.Products;
import main.bean.User;
import main.db.ConnectMysqlExample;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

public class SignaruteService {
    private static SignaruteService instance;
    public static SignaruteService getInstance() {
        if (instance == null) {
            instance = new SignaruteService();
        }
        return instance;
    }

    public void createSignature(User user , Cart map, String ads1, String ads2) throws NoSuchAlgorithmException {
        HashMap<String, Products> data = map.getData();
        long total = map.getTotal();
        int quanity = map.getQuantity();
        String date= AppService.getNowDate().toString();
        DataSignature dataSignature = new DataSignature(data,user,total,quanity,date,ads1,ads2);
        String hashData = dataSignature.hashDataSignature();
        System.out.println("Hash" + hashData);

    }
}
