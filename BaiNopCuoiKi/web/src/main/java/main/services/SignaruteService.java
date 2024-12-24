package main.services;

import main.bean.*;
import main.db.ConnectMysqlExample;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignaruteService {
    private static SignaruteService instance;
    public static SignaruteService getInstance() {
        if (instance == null) {
            instance = new SignaruteService();
        }
        return instance;
    }
    // Tạo hash dữ liệu đặt hàng
    public String createHashSignature(User user ,  ArrayList<OderCart> listproduct,long total, int quanity, String ads1, String ads2, Timestamp timestamp) throws NoSuchAlgorithmException {
        String date= AppService.getNowDate().toString();
        DataSignature dataSignature = new DataSignature(listproduct,user,total,quanity,date,ads1,ads2,timestamp);
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
    public String getPublicKeyByID(String id){
        try {
            Connection conn = ConnectMysqlExample.getConnection(ConnectMysqlExample.getDbUrl(), ConnectMysqlExample.getUserName(), ConnectMysqlExample.getPASSWORD());
            String query = "select keyrsa.keyRSA from keyrsa where keyrsa.ID_key= ?  ";
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
    public String getIdKeyUser(String id){
        try {
            Connection conn = ConnectMysqlExample.getConnection(ConnectMysqlExample.getDbUrl(), ConnectMysqlExample.getUserName(), ConnectMysqlExample.getPASSWORD());
            String query = "select keyrsa.ID_key from keyrsa where keyrsa.id_user= ? and keyrsa.status = 'ACTIVE'";
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
    public Key getTimeByIdKey(String id){
        try {
            Connection conn = ConnectMysqlExample.getConnection(ConnectMysqlExample.getDbUrl(), ConnectMysqlExample.getUserName(), ConnectMysqlExample.getPASSWORD());
            String query = "select keyrsa.time_active,keyrsa.time_expired from keyrsa where keyrsa.ID_key= ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            Key a;
            while (rs.next()) {
                Timestamp   timeactive= rs.getTimestamp(1);
                Timestamp timeExpired=    rs.getTimestamp(2);
               a= new Key(timeactive,timeExpired);
                return a;
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
    public String getSignatureFromForm(String signatureFromInput,String signatureFromFile){
        if (signatureFromFile != null){
            return signatureFromFile;
        } else {
            return signatureFromInput;
        }
    }
    public boolean updateKeyStatus(String idKey) {
        long currentTimeMillis = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(currentTimeMillis);
        try {
            Connection conn = ConnectMysqlExample.getConnection(
                    ConnectMysqlExample.getDbUrl(),
                    ConnectMysqlExample.getUserName(),
                    ConnectMysqlExample.getPASSWORD()
            );
            String query = "UPDATE keyrsa SET status = ?, time_expired = ? WHERE ID_key = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, "DISABLE");        // Cập nhật trạng thái thành "DISABLE"
            ps.setTimestamp(2, timestamp);  // Cập nhật thời gian hết hạn
            ps.setString(3, idKey);           // ID của khóa cần cập nhật

            int rowsUpdated = ps.executeUpdate();
            conn.close();
            return rowsUpdated > 0; // Trả về true nếu cập nhật thành công
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }
    public java.sql.Timestamp getTimeExpired(String idKey){
        try {
            Connection conn = ConnectMysqlExample.getConnection(ConnectMysqlExample.getDbUrl(), ConnectMysqlExample.getUserName(), ConnectMysqlExample.getPASSWORD());
            String query = "select keyrsa.time_active from keyrsa where keyrsa.id_user= ? and keyrsa.status = 'ACTIVE'";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, idKey);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getTimestamp(1);
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



    public static void main(String[] args) {
        System.out.println(SignaruteService.getInstance().getIdKeyUser("USER1"));
    }
}
