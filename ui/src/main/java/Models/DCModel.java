package Models;

import com.example.mainapp.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DCModel {

    //these static variables are needed across multiple windows
    private static int cid;
    private static int nbDays;
    private final Connection con;

    //constructor,
    //get db connection
    public DCModel() {
        Database db = null;
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert db != null;
        con = db.connect();
    }

    //getter for car id
    public static int getCarId() {
        return cid;
    }

    //setter for car id => used to set car id that was selected in first window
    public static void setCid(int cid) {
        DCModel.cid = cid;
    }

    //getter for nbDays => used in DCModel to check total
    public static int getNbDays() {
        return DCModel.nbDays;
    }

    //setter for nbDays => used in DCController to set nbDays from spinner
    public static void setNbDays(int nbDays) {
        DCModel.nbDays = nbDays;
    }

    //used to display car info in detailed list view (used in DCController)
    public ResultSet loadCarInformation(int cid) throws SQLException {
        String query = "select * from vwCarsAndBranch where crid = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, cid);
        return ps.executeQuery();
    }
}
