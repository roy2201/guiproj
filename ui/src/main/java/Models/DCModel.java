package Models;

import com.example.mainapp.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DCModel {

    private static int cid;
    private static int nbDays;
    private final Connection con;

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

    public static int getCarId() {
        return cid;
    }

    public static void setCid(int cid) {
        DCModel.cid = cid;
    }

    public static int getNbDays() {
        return DCModel.nbDays;
    }

    public static void setNbDays(int nbDays) {
        DCModel.nbDays = nbDays;
    }

    public ResultSet loadCarInformation(int cid) throws SQLException {
        String query = "select * from vwCarsAndBranch where crid = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, cid);
        return ps.executeQuery();
    }
}
