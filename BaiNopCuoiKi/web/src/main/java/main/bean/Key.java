package main.bean;

import java.sql.Timestamp;

public class Key {
    String id_key;
    String key;
    String status;
    String timeActive;
    String timeExpired;
    Timestamp timeActived;
    Timestamp timeExpiredd;
    public Key() {
    }


    public Key(String id_key, String key, String status, String timeActive) {
        this.id_key = id_key;
        this.key = key;
        this.status = status;
        this.timeActive = timeActive;
    }
    public Key(Timestamp timeActived,Timestamp timeExpiredd) {
        this.timeExpiredd = timeExpiredd;
        this.timeActived= timeActived;

    }

    public Key(String status,String timeActive) {
        this.status = status;
        this.timeActive = timeActive;

    }
    public String getTimeExpired() {
        return timeExpired;
    }

    public Timestamp getTimeActived() {
        return timeActived;
    }

    public void setTimeActived(Timestamp timeActived) {
        this.timeActived = timeActived;
    }

    public Timestamp getTimeExpiredd() {
        return timeExpiredd;
    }

    public void setTimeExpiredd(Timestamp timeExpiredd) {
        this.timeExpiredd = timeExpiredd;
    }

    public void setTimeExpired(String timeExpired) {
        this.timeExpired = timeExpired;
    }

    public String getId_key() {
        return id_key;
    }

    public String getKey() {
        return key;
    }

    public String getStatus() {
        return status;
    }

    public String getTimeActive() {
        return timeActive;
    }

    public void setId_key(String id_key) {
        this.id_key = id_key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimeActive(String timeActive) {
        this.timeActive = timeActive;
    }
}
