package Models;

import com.example.mainapp.Database;
import javafx.scene.chart.PieChart;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class SignupModel {

    Connection con ;

    public SignupModel() {

        Database db = null;
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        con = db.connect();
    }

    public int registerClient(String uname, String password) {
        int errorcode = 0;
        String query = "exec spSignUp ?,?,?";
        CallableStatement cst = null;
        try {
            cst = con.prepareCall(query);
            cst.setString(1,uname);
            cst.setString(2,password);
            cst.registerOutParameter(3, Types.INTEGER);
            System.out.println("done");
            cst.execute();
            errorcode = cst.getInt(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return errorcode;
    }

}
