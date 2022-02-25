package com.example.mainapp;

import Models.CreditCardModel;
import Models.LoginModel;
import Models.PaypalModel;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private static Database databaseInstance;
    public static Connection connection;

    private Database() {
    }

    private static final String USERNAME = "sa";
    private static final String PASSWORD = "rooter-01101";
    private static final String CONN_STR = "jdbc:sqlserver://localhost\\sqlexpress:1433;databaseName=sparkrentdb";

    public static Database getInstance() throws SQLException {
        if (databaseInstance == null) {
            System.out.println("Getting your instance");
            databaseInstance = new Database();
        }
        return databaseInstance;
    }

    public Connection connect() {
        try {
            System.out.println("Connecting");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(CONN_STR, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            connection = null;
        }
        if (connection == null) {
            System.out.println("Null connection");
            System.exit(1);
        }
        else System.out.println("Connected");
        return connection;
    }

    private final ArrayList<String> comments = new ArrayList<>();
    public ArrayList<String> loadComments() {
        String query = "select * from vwUnameWithComment";
        ResultSet rs = null;
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(query);
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

    public ArrayList<CreditCardModel> loadCards() {
        ArrayList<CreditCardModel> cards = new ArrayList<>();
        String query = "exec spGetCards ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
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

    public ArrayList<PaypalModel> loadPaypal() {
        ArrayList<PaypalModel> plist = new ArrayList<>();
        String query = "exec spGetPaypal ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
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