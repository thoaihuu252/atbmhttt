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

    // Constructor
    public DataSignature(HashMap<String, Products> data, User customer, long total, int quantity) {
        this.data = data;
        this.customer = customer;
        this.total = total;
        this.quantity = quantity;
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

    // Hàm hash đối tượng DataSignature
    public byte[] hashDataSignature() throws NoSuchAlgorithmException {
        Gson gson = new Gson();

        // Chuyển đổi đối tượng thành chuỗi JSON
        String jsonData = gson.toJson(this);

        // Tạo đối tượng MessageDigest với thuật toán SHA-256
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        // Cập nhật dữ liệu cần hash vào MessageDigest
        byte[] hashBytes = messageDigest.digest(jsonData.getBytes());

        return hashBytes;
    }

}
