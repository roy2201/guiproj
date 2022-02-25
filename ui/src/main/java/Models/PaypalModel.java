package Models;

import Interfaces.PaymentStrategy;
import com.example.mainapp.Database;
import lombok.Data;

import java.sql.*;

@Data
public class PaypalModel implements PaymentStrategy {

    private String email;
    private String password;
    private int amount;

    Connection con;

    public PaypalModel(String email, String password) {
        Database db = null;
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert db != null;
        con = db.connect();

        this.email = email;
        this.password = password;
    }

    public PaypalModel(String email, String pass, int balance) {
        this.email = email;
        this.password = pass;
        this.amount = balance;
    }

    @Override
    public boolean pay(int amount) {
        String query = "exec spPaypal ?, ?, ?";
        try {
            CallableStatement cst = con.prepareCall(query);
            cst.setInt(1,amount);
            cst.setString(2, this.email);
            System.out.println("email : "+ this.email);
            cst.registerOutParameter(3, Types.INTEGER);
            cst.execute();
            int errorCode = cst.getInt(3);
            if(errorCode == 2) return false;
            cst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("payed "+amount+" with Paypal");
        setAmount(amount);
        System.out.println("adding transaction");
        addTransaction();
        return true;
    }

    public boolean validateInfo(String email, String password) {
        String query = "exec isValidPaypal ?, ?, ?, ?";
        try {
            CallableStatement cst = con.prepareCall(query);
            cst.setString(1, email);
            cst.setString(2, password);
            cst.registerOutParameter(3, Types.INTEGER);
            cst.setInt(4, LoginModel.getLogged());
            cst.execute();
            int errorCode = cst.getInt(3);
            if (errorCode == 1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addTransaction() {
        String query = "exec spAddTran ?, ?, ?, ?";
        try {
            CallableStatement cst = con.prepareCall(query);
            cst.setInt(1, LoginModel.getLogged());
            cst.setInt(2, DCModel.getCarId());
            cst.setInt(3,DCModel.getNbDays());
            cst.setInt(4, this.getAmount());
            cst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Transaction succeeded");
    }

}
