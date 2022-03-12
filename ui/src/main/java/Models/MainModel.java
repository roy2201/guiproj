package Models;

import com.example.mainapp.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainModel {

    private Connection con;

    //constructor
    //get db connection
    public MainModel() {
        try {
            Database db = Database.getInstance();
            con = db.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //load all cars from database
    public ResultSet loadCars() throws SQLException {
        String query = "SELECT * FROM vwCarsAndBranch";
        PreparedStatement ps = con.prepareStatement(query);
        return ps.executeQuery();
    }

    //load all branches from database
    public ResultSet loadBranches() throws SQLException {
        String query = "SELECT bname FROM TBLBRANCH";
        PreparedStatement ps = con.prepareStatement(query);
        return ps.executeQuery();
    }

    //load all car models from database
    public ResultSet loadModels() throws SQLException {
        String query = "SELECT distinct cmodel FROM TBLCAR";
        PreparedStatement ps = con.prepareStatement(query);
        System.out.println("models loaded");
        return ps.executeQuery();
    }

    //load filtered search results from database
    public ResultSet loadFilteredResults(String model, String location) throws SQLException {
        String query = "select * from vwCarsAndBranch where bname = ? and cmodel = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1,location);
        ps.setString(2,model);
        return ps.executeQuery();
    }
}
