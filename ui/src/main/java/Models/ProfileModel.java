package Models;

import com.example.mainapp.Database;

import java.sql.*;

public class ProfileModel {

    private Database db;
    private Connection con;

    public ProfileModel() {
        try {
            db = Database.getInstance();
            con = db.connect();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet loadTransactions(int cid) {
        String query = "exec spShowTran ?";
        try {
            CallableStatement cst = con.prepareCall(query);
            cst.setInt(cid, 1);
            return cst.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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