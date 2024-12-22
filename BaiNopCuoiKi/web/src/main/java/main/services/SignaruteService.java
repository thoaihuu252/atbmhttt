package main.services;

import main.bean.Cart;
import main.bean.DataSignature;
import main.bean.Products;
import main.bean.User;
import main.db.ConnectMysqlExample;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class SignaruteService {
    private static SignaruteService instance;
    public static SignaruteService getInstance() {
        if (instance == null) {
            instance = new SignaruteService();
        }
        return instance;
    }
    // Tạo hash dữ liệu đặt hàng
    public String createHashSignature(User user , Cart map, String ads1, String ads2) throws NoSuchAlgorithmException {
        HashMap<String, Products> data = map.getData();
        long total = map.getTotal();
        int quanity = map.getQuantity();
        String date= AppService.getNowDate().toString();
        DataSignature dataSignature = new DataSignature(data,user,total,quanity,date,ads1,ads2);
        String hashData = dataSignature.hashDataSignature();
        System.out.println("Hash : " + hashData);
        return hashData;
    }
        // Lấy key của user
    public String getPublicKeyFromUser(String id){
        try {
            Connection conn = ConnectMysqlExample.getConnection(ConnectMysqlExample.getDbUrl(), ConnectMysqlExample.getUserName(), ConnectMysqlExample.getPASSWORD());
            String query = "select keyrsa.keyRSA from keyrsa where keyrsa.id_user= ? and keyrsa.status = 'ACTIVE'";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    // Mã hóa dữ lệu đã hash bang private key
    public String encryptHashDataSignature(String data, String privateKey) throws Exception {
        RSA rsa = new RSA();
        rsa.importPrivateKey(privateKey);
        return rsa.signData(data);
    }
    // Xác thực chữ ký
    public boolean verifySignature(String dataHashSignature, String encryptHashDataSignature, String publicKey) throws Exception {

        RSA rsa = new RSA();
        rsa.importPublicKey(publicKey);
        return rsa.verifySignature(dataHashSignature,encryptHashDataSignature);
    }

    public static void main(String[] args) {
        System.out.println(SignaruteService.getInstance().getPublicKeyFromUser("USER2"));
    }
}
