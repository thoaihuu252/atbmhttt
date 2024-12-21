package main.bean;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class DataSignature {
    private HashMap<String, Products> data;
    private User customer;
    private long total;
    private int quantity;
    private String date;
    private String add1;
    private String add2;

    // Constructor
    public DataSignature(HashMap<String, Products> data, User customer, long total, int quantity,String date,String add1,String add2) {
        this.data = data;
        this.customer = customer;
        this.total = total;
        this.quantity = quantity;
        this.date = date;
        this.add1 = add1;
        this.add2 = add2;
    }

    // Getter và Setter
    public HashMap<String, Products> getData() {
        return data;
    }

    public void setData(HashMap<String, Products> data) {
        this.data = data;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getAdd1(){
        return add1;
    }
    public void setAdd1(String add1){
        this.add1 = add1;
    }
    public String getAdd2(){
        return add2;
    }
    public void setAdd2(String add2){
        this.add2 = add2;
    }


    // Hàm hash đối tượng DataSignature
    public String hashDataSignature() throws NoSuchAlgorithmException {
        Gson gson = new Gson();

        // Chuyển đổi đối tượng thành chuỗi JSON
        String jsonData = gson.toJson(this);

        // Tạo đối tượng MessageDigest với thuật toán SHA-256
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        // Cập nhật dữ liệu cần hash vào MessageDigest
        byte[] hashBytes = messageDigest.digest(jsonData.getBytes());

        // Chuyển đổi kết quả hash thành chuỗi hex
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        // Trả về chuỗi hash (mã băm)
        return hexString.toString();
    }

}
