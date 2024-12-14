package main.services;

import main.db.ConnectMysqlExample;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignaruteService {
    private static SignaruteService instance;
    public static SignaruteService getInstance() {
        if (instance == null) {
            instance = new SignaruteService();
        }
        return instance;
    }

    public void createSignature(){

    }
}
