package Models;

import com.example.mainapp.Database;

import java.sql.*;

public class LoginModel {

    private Database db;
    private final Connection con;

    private static int cid;

    static int admin_logged_id;
    static int secretary_logged_id;
    public static int admin_secreatry;

    public LoginModel() {
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        con = db.connect();
    }

    public static int getLogged() {
        return LoginModel.cid;
    }

    public static void setLogged(int cid) {
        LoginModel.cid = cid;
    }

    public int validLogin(String uname, String password) {
        int errorcode = 0;
        cid = -1;
        String query = "exec spLogin ?, ?, ?, ?";
        try {
            CallableStatement cs = con.prepareCall(query);
            cs.setString(1,uname);
            cs.setString(2,password);
            cs.registerOutParameter(3, Types.INTEGER);
            cs.registerOutParameter(4,Types.INTEGER);
            cs.execute();
            errorcode = cs.getInt(4);
            cid = cs.getInt(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return errorcode;
    }

    public void logout(int cid) {
        String query = "exec spLogout ?";
        System.out.println("test");
        try {
            CallableStatement cst = con.prepareCall(query);
            cst.setInt(1,cid);
            cst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int validAdmin(String adminName, String adminPass) {
        int errorcode = 0;
        String query = "exec dbo.spAdminLogin ?,?,?,?";
        try {
            CallableStatement cs = con.prepareCall(query);
            cs.setString(1, adminName);
            cs.setString(2, adminPass);
            cs.registerOutParameter(3, Types.INTEGER);
            cs.registerOutParameter(4, Types.INTEGER);
            cs.execute();
            admin_logged_id = cs.getInt(3);
            errorcode = cs.getInt(4);
            admin_secreatry = 1;
            return errorcode;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return errorcode;
    }

    public int validSecretary(String secreatryName, String secretaryPass) {
        int errorcode = 0;
        String query = "exec dbo.spSecretaryLogin ?,?,?,?";
        try {
            CallableStatement cs = con.prepareCall(query);
            cs.setString(1, secreatryName);
            cs.setString(2, secretaryPass);
            cs.registerOutParameter(3, Types.INTEGER);
            cs.registerOutParameter(4, Types.INTEGER);
            cs.execute();
            secretary_logged_id = cs.getInt(3);
            errorcode = cs.getInt(4);
            admin_secreatry = 2;
            return errorcode;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return errorcode;
    }

}
