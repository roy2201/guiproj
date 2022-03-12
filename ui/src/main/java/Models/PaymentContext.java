package Models;

import Interfaces.PaymentStrategy;
import com.example.mainapp.Database;

import java.sql.*;

public class PaymentContext {

    final private Connection con;
    private PaymentStrategy paymentStrategy;

    //constructor,
    //get db connection
    public PaymentContext() {
        Database db = null;
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert db != null;
        con = db.connect();
    }

    //set payment method
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    //execute pay => uses dynamic binding
    public boolean execute(int amount) {
        return paymentStrategy.pay(amount);
    }

    //check if logged in customer is premium
    public boolean checkPremium(int cid) {
        String query = "exec isPremium ?, ?";
        try {
            CallableStatement cst = con.prepareCall(query);
            cst.setInt(1, cid);
            cst.registerOutParameter(2, Types.BOOLEAN);
            cst.execute();
            return cst.getBoolean(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //calculate total amount without discount
    public int calculateTotal(int carId, int nbDays) {
        String query = "{ ? = call fnViewReceipt ( ?, ? ) }";
        try {
            CallableStatement cst = con.prepareCall(query);
            cst.registerOutParameter(1, Types.INTEGER);
            cst.setInt(2,carId);
            cst.setInt(3, nbDays);
            cst.execute();
            return cst.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println("Total in total is " + amount);
        return -1;
    }

}
