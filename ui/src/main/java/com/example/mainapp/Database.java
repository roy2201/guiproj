package com.example.mainapp;

import Models.CreditCardModel;
import Models.LoginModel;
import Models.PaypalModel;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private static Database dbInstance;
    public static Connection con;

    private Database() {
    }

    //constant connection values
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "rooter-01101";
    private static final String CONN_STR = "jdbc:sqlserver://localhost\\sqlexpress:1433;databaseName=sparkrentdb";

    //return the instance if exists, otherwise created new one
    public static Database getInstance() throws SQLException {
        if (dbInstance == null) {
            System.out.println("Getting your instance");
            dbInstance = new Database();
        }
        return dbInstance;
    }

    //actual database connection establishing
    public Connection connect() {
        try {
            System.out.println("Connecting");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(CONN_STR, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            con = null;
        }
        if (con == null) {
            System.out.println("Null connection");
            System.exit(1);
        }
        else System.out.println("Connected");
        return con;
    }

    //get all comments stored in database
    //used in channel model to load all comments
    public ArrayList<String> loadComments() {
        ArrayList<String> comments = new ArrayList<>();
        String query = "select * from vwUnameWithComment";
        PreparedStatement ps;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while(true) {
            try {
                assert rs != null;
                if (!rs.next()) break;
                comments.add(rs.getString("cuname")
                        + ": "+(rs.getString("ctvalue")));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return comments;
    }

    //get all credit cards for specific customer into arraylist
    public ArrayList<CreditCardModel> loadCards() {
        ArrayList<CreditCardModel> cards = new ArrayList<>();
        String query = "exec spGetCards ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, LoginModel.getLogged());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                CreditCardModel cr = new CreditCardModel(
                rs.getInt("cardnumber"),
                rs.getInt("cvv"),
                String.valueOf(rs.getDate("expirydate")),
                rs.getString("holdername"),
                rs.getInt("balance")
                );
                cards.add(cr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    //get all paypal accounts to specific customer
    public ArrayList<PaypalModel> loadPaypal() {
        ArrayList<PaypalModel> plist = new ArrayList<>();
        String query = "exec spGetPaypal ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, LoginModel.getLogged());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                PaypalModel pm = new PaypalModel(
                        rs.getString("email"),
                        rs.getString("ppass"),
                        rs.getInt("balance")
                );
                plist.add(pm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plist;
    }
}