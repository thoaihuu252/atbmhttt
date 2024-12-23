package main.bean;

import main.services.AppService;
import main.services.SignaruteService;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable  {

    String idOder;
    String pbuyName;
    String phoneNumber;
    String Note;
    Date dayCrate;
    String status;
    String idAccount;
    int cost;
    int ship;
    int net;
    int total;
    Address address;
    String addresss;
    String statusSignature;
    String wardID;
    String integrity;
    String districtID;
    Voucher voucher;
    ArrayList<OderCart> allOderCart;
    String oder_RSA;
    Timestamp time;
    public Order() {
    }

    public Order(String idOder, String pbuyName, String phoneNumber, String note, Date dayCrate, String status, String idAccount, Address address, Voucher voucher, ArrayList<OderCart> allproduct) {
        this.idOder = idOder;
        this.pbuyName = pbuyName;
        this.phoneNumber = phoneNumber;
        Note = note;
        this.dayCrate = dayCrate;
        this.status = status;
        this.idAccount = idAccount;
        this.address = address;
        this.voucher = voucher;
        this.allOderCart = allproduct;
    }
    public Order(String idOder, String pbuyName, String phoneNumber, String note, Date dayCrate, String status, String idAccount, Address address, Voucher voucher, ArrayList<OderCart> allproduct,String string1, String string2) {
        this.idOder = idOder;
        this.pbuyName = pbuyName;
        this.phoneNumber = phoneNumber;
        Note = note;
        this.dayCrate = dayCrate;
        this.status = status;
        this.idAccount = idAccount;
        this.address = address;
        this.voucher = voucher;
        this.allOderCart = allproduct;
        this.districtID =string1;
        this.wardID = string2;
    }

    public Order(String idOder, String idAccount, Address address, ArrayList<OderCart> products, String status, Voucher voucher) {
        this.idOder = idOder;
        this.status = status;
        this.allOderCart = products;
        this.idAccount = idAccount;
        this.address = address;
        this.voucher = voucher;

    }
    public Order(String idOder, String idAccount, Address address, ArrayList<OderCart> products, String status, Voucher voucher,String string1, String string2) {
        this.idOder = idOder;
        this.status = status;
        this.allOderCart = products;
        this.idAccount = idAccount;
        this.address = address;
        this.voucher = voucher;
        this.districtID =string1;
        this.wardID = string2;

    }

    public void setDayCrate(Date dayCrate) {
        this.dayCrate = dayCrate;
    }
    public void update() throws IOException {
        setAddresss();
        //setIntegrity();
    }
    public void updateIntegrity(User user) throws Exception {
        setIntegrity(user);
    }


    public String getIntegrity() {
        return integrity;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setIntegrity(User user) throws Exception {
        ArrayList<OderCart> listproduct= new ArrayList<>();
        int quanity = 0;
        for(OderCart a : allOderCart){
            OderCart product= new OderCart(new Products(a.Item.getID_food(),a.Item.getPrice(),a.Item.getFoodName()),a.value);
            listproduct.add(product);
            quanity+=a.value;
        }
        long total = getProfit();

        String hashData = SignaruteService.getInstance().createHashSignature(user, listproduct, total,quanity,getDistrictID(), getWardID(),getTime());
        String keyUser = SignaruteService.getInstance().getPublicKeyFromUser(user.userId);

        if(getStatusSignature().equals("Có chữ ký")){
            boolean test = SignaruteService.getInstance().verifySignature(hashData, getOder_RSA(), keyUser);
            if(test){this.integrity="Dữ liệu toàn vẹn";}
            else {this.integrity="Dữ liệu đã bị thay đổi";}

        }else{ this.integrity="";}

    }

    public void setAddresss() throws IOException {
        ApiController control = new ApiController();
        this.addresss = control.getLocation(districtID,wardID);
    }
    public String getStatusSignature(){
        return statusSignature;
    }
    public void setStatusSignature(String statusSignature){
        this.statusSignature = statusSignature;
    }

    public String getOder_RSA() {
        return oder_RSA;
    }

    public void setOder_RSA(String oder_RSA) {
        this.oder_RSA = oder_RSA;
    }

    public String getWardID() {
        return wardID;
    }

    public void setWardID(String wardID) {
        this.wardID = wardID;
    }

    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
    }

    public Order(String idOder) {
        this.idOder = idOder;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public String getIdOder() {
        return idOder;
    }

    public String getPbuyName() {
        return pbuyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getNote() {
        return Note;
    }

    public Date getDayCrate() {
        return dayCrate;
    }

    public String getStatus() {
        return status;
    }

    public int getShip() {
        return ship;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setIdOder(String idOder) {
        this.idOder = idOder;
    }

    public void setPbuyName(String pbuyName) {
        this.pbuyName = pbuyName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNote(String note) {
        Note = note;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setShip(int ship) {
        this.ship = ship;
    }

    public void setNet(int net) {
        this.net = net;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public void setAllOderCart(ArrayList<OderCart> allOderCart) {
        this.allOderCart = allOderCart;
    }

    public String getAddress() {
        return addresss;
    }

    public int getMouth(){
        return dayCrate.getMonth();
    }
    public int getProfit() {
        int value = 0;
        for (OderCart item : getAllOderCart()) {
            value += item.getThisodercartvl();
        }
        return value;
    }

    public String getTotalValue() {
        return AppService.intToVND(getProfit());
    }
    public String getUserTotalCost() {
        return getTotalAfterVoucher();
    }
    public String getTotalAfterVoucher() {
        int rs = getProfit();
        int newShip = ship;
        int voucherValue = voucher.getIntDiscount();
        switch (voucher.getTypeInt()) {
            case 0:
                if (voucherValue > rs)
                    rs = 0;
                else
                    rs -= voucherValue;
                break;
            case 1:
                if (voucherValue >= 100) {
                    rs = 0;
                } else
                    rs -= (rs * voucherValue) / 100;
                break;
            case 2:
                if (voucherValue > ship)
                    newShip = 0;
                else
                    newShip -= voucherValue;
                break;
        }
        rs += newShip;
        return AppService.intToVND(rs);
    }

    public void getAll(){

    }
    public int getNewShip() {
        int newShip = ship;
        int voucherValue = voucher.getIntDiscount();
        if (voucher.typeInt == 2 ) {
            if (voucherValue > ship)
                newShip = 0;
            else
                newShip -= voucherValue;
        }
        return newShip;
    }

    public String getDiscount() {
        if (voucher.getTypeInt() == 1)
            return voucher.disCount + "%";
        else if (voucher.getTypeInt() == 0)
            return AppService.intToVND(voucher.disCount);
        else
            return "0VND";
    }

    public int getAllTotal() {
        int rs = 0;
        for (OderCart item : allOderCart) {
            rs += item.getValue();
        }
        return rs;
    }

    public String revice() {
        if ("Đã Giao".equals(status)) {
            return getTotalAfterVoucher();
        } else if ("Chưa Giao".equals(status))
            return "0VND";
        return "0VND";
    }

    public ArrayList<OderCart> getAllOderCart() {
        return allOderCart;
    }
}
