package Models;

import com.example.mainapp.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class AddRemoveCarModel {

    Database db;
    Connection con;

    public AddRemoveCarModel() {
        try {
            db = Database.getInstance();
            con = db.connect();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void AddCar(String branch, String color, String year, String model, String price, String type) {

        CallableStatement cs = null;
        String query = "exec dbo.spAddCar ?,?,?,?,?,?";
        try {
            cs = con.prepareCall(query);

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


    public int RemoveCar(String carid) {
        int errorcode =0;
        CallableStatement cs = null;
        String query = "exec dbo.spRemoveCar ?,?";
        try {
            cs = con.prepareCall(query);
            cs.setInt(1, Integer.parseInt(carid));
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
            errorcode = cs.getInt(2);

            }

            catch (SQLException e) {
                e.printStackTrace();
            }


        return errorcode;
    }

}
