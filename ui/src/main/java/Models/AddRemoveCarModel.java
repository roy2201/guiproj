package Models;

import com.example.mainapp.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class AddRemoveCarModel {

    private Connection con;

    //constructor, get db connection
    public AddRemoveCarModel() {
        try {
            Database db = Database.getInstance();
            con = db.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //add new car
    public void AddCar(String branch, String color, String year, String model, String price, String type) {
        String query = "exec dbo.spAddCar ?,?,?,?,?,?";
        try {
            CallableStatement cs = con.prepareCall(query);
            cs.setInt(1, Integer.parseInt(branch));
            cs.setString(2, color);
            cs.setString(3, year);
            cs.setString(4, model);
            cs.setString(5, price);
            cs.setString(6, type);
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //remove car by id
    public int RemoveCar(String carid) {
        String query = "exec dbo.spRemoveCar ?,?";
        try {
            CallableStatement cs = con.prepareCall(query);
            cs.setInt(1, Integer.parseInt(carid));
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
            return cs.getInt(2);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        return 0;
    }

}
