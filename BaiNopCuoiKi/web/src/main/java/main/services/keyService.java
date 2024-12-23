package main.services;

import main.bean.Key;
import main.db.ConnectMysqlExample;

import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class keyService {
    public static keyService instance;

    public static keyService getInstance() {
        if (instance == null) {
            instance = new keyService();
        }
        return instance;
    }
    public Key getKey(String iduser) {
        try {
            Connection conn = ConnectMysqlExample.getConnection(ConnectMysqlExample.getDbUrl(), ConnectMysqlExample.getUserName(), ConnectMysqlExample.getPASSWORD());


            PreparedStatement ps = conn.prepareStatement("SELECT keyrsa.ID_key,keyrsa.KeyRSA,keyrsa.status,keyrsa.time_active From keyrsa " +
                    "WHERE ID_USER = ?AND status=? ");
            ps.setString(1, iduser);
            ps.setString(2, "ACTIVE");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String idKey = rs.getString(1);
                String Key = rs.getString(2);
                String status = rs.getString(3);
                String timeactive = rs.getString(4);

                return new Key(idKey,Key,status,timeactive);
            }
            // close connection
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }  public int checkCOUNT(String iduser) {
        try {
            Connection conn = ConnectMysqlExample.getConnection(ConnectMysqlExample.getDbUrl(), ConnectMysqlExample.getUserName(), ConnectMysqlExample.getPASSWORD());
            String query = "SELECT COUNT(*) AS total_records FROM keyrsa WHERE ID_USER = ?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, iduser);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int totalRecords = rs.getInt("total_records");
                return totalRecords;
            }
            // close connection
            conn.close()                                                                    ;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    public String Check(String iduser) {
        try {
            Connection conn = ConnectMysqlExample.getConnection(ConnectMysqlExample.getDbUrl(), ConnectMysqlExample.getUserName(), ConnectMysqlExample.getPASSWORD());


            PreparedStatement ps = conn.prepareStatement("SELECT keyrsa.ID_key,keyrsa.KeyRSA,keyrsa.status,keyrsa.time_active From keyrsa " +
                    "WHERE ID_USER = ? AND status=? ");
            ps.setString(1, iduser);
            ps.setString(2, "ACTIVE");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String idKey = rs.getString(1);
                return idKey;
            }
            // close connection
            conn.close()                                                                    ;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public void lockKey(String idKey) {
        try {
            Connection conn = ConnectMysqlExample.getConnection(ConnectMysqlExample.getDbUrl(), ConnectMysqlExample.getUserName(), ConnectMysqlExample.getPASSWORD());

            long dateTime = System.currentTimeMillis() ;
            java.sql.Timestamp timeExpired = new java.sql.Timestamp(dateTime);
            PreparedStatement ps = conn.prepareStatement("UPDATE keyrsa SET status = ?, dt_expired = ? WHERE  ID_key = ?;");
            ps.setString(1, "DISABLED");
            ps.setTimestamp(2, timeExpired);
            ps.setString(3, idKey);

            ps.executeUpdate();

            // close connection
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //Tạo khóa mới
    public void importNewKey(String iduser, String publicKey) {
        try {
            Connection conn = ConnectMysqlExample.getConnection(ConnectMysqlExample.getDbUrl(), ConnectMysqlExample.getUserName(), ConnectMysqlExample.getPASSWORD());
            String idkeycheck =keyService.getInstance().Check(iduser);
            if(idkeycheck!=null) keyService.getInstance().lockKey(idkeycheck);
            long dateTime = System.currentTimeMillis();
            java.sql.Timestamp timeActive = new java.sql.Timestamp(dateTime);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO keyrsa (ID_key, KeyRSA, status, time_active, ID_USER)\n" +
                    "VALUES (?, ?, ?, ?, ?);");
            ps.setString(1, iduser +"key"+(keyService.getInstance().checkCOUNT(iduser)+1));
            ps.setString(2, publicKey);
            ps.setString(3, "ACTIVE");
            ps.setTimestamp(4, timeActive);
            ps.setString(5, iduser);

            ps.executeUpdate();
            // close connection
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
       // keyService .getInstance().importNewKey("USER2","aa");
       String a= keyService.getInstance().Check("USER2");
       System.out.println(a);
    }

}
