package Models;

import com.example.mainapp.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainModel {

    private Connection con;

    public MainModel() {
        try {
            Database db = Database.getInstance();
            con = db.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet loadCars() throws SQLException {
        String query = "SELECT * FROM vwCarsAndBranch";
        PreparedStatement ps = con.prepareStatement(query);
        return ps.executeQuery();
    }

    public ResultSet loadBranches() throws SQLException {
        String query = "SELECT bname FROM TBLBRANCH";
        PreparedStatement ps = con.prepareStatement(query);
        return ps.executeQuery();
    }

    public ResultSet loadModels() throws SQLException {
        String query = "SELECT distinct cmodel FROM TBLCAR";
        PreparedStatement ps = con.prepareStatement(query);
        System.out.println("models loaded");
        return ps.executeQuery();
    }

    public ResultSet loadFilteredResults(String model, String location) throws SQLException {
        String query = "select * from vwCarsAndBranch where bname = ? and cmodel = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1,location);
        ps.setString(2,model);
        return ps.executeQuery();
    }
}
