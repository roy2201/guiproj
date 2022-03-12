package Models;

import com.example.mainapp.Database;

import java.sql.*;

public class ProfileModel {

    private Connection con;

    //constructor
    public ProfileModel() {
        try {
            Database db = Database.getInstance();
            con = db.connect();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //get all transactions for specific customer
    public ResultSet loadTransactions(int cid) {
        String query = "exec spShowTran ?";
        try{
            CallableStatement cst = con.prepareCall(query);
            cst.setInt(cid, 1);
            return cst.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //get all credit cards for specific customer
    public ResultSet loadCards(int cid) {
        String query = "exec spShowCards ?";
        try {
            CallableStatement cst = con.prepareCall(query);
            cst.setInt(cid, 1);
            return cst.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //get all PayPal accounts for specific customer
    public ResultSet loadPaypalAccounts(int cid) {
        String query = "exec spGetPaypal ?";
        try {
            CallableStatement cst = con.prepareCall(query);
            cst.setInt(cid, 1);
            return cst.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //get customer name , to display it on screen
    public String getName(int cid) {
        String query = "exec spGetLoggedUserName ?, ?";
        try {
            CallableStatement cst = con.prepareCall(query);
            cst.setInt(cid, 1);
            cst.registerOutParameter(2, Types.VARCHAR);
            cst.execute();
            return cst.getString(2);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}