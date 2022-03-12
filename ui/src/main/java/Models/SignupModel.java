package Models;

import com.example.mainapp.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.concurrent.atomic.AtomicInteger;

public class SignupModel {

    final private Connection con ;

    public SignupModel() {

        Database db = null;
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert db != null;
        con = db.connect();
    }

    //register new client => add client record to database
    //many values can be checked before adding to database
    //checking if name is not number , verify pass, ...
    public int registerClient(String uname, String password) {
        AtomicInteger errorCode = new AtomicInteger();
        String query = "exec spSignUp ?,?,?";
        try {
            CallableStatement cst = con.prepareCall(query);
            cst.setString(1,uname);
            cst.setString(2,password);
            cst.registerOutParameter(3, Types.INTEGER);
            System.out.println("done");
            cst.execute();
            errorCode.set(cst.getInt(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return errorCode.get();
    }

}
