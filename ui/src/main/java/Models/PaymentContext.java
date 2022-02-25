package Models;

import Interfaces.PaymentStrategy;
import com.example.mainapp.Database;

import java.sql.*;

public class PaymentContext {

    Connection con;
    PaymentStrategy paymentStrategy;

    public PaymentContext() {
        Database db = null;
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        con = db.connect();
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean execute(int amount) {
        return paymentStrategy.pay(amount);
    }

    public boolean checkPremium(int cid) {
        CallableStatement cst = null;
        String query = "exec isPremium ?, ?";
        boolean premium = false;
        try {
            cst = con.prepareCall(query);
            cst.setInt(1, LoginModel.getLogged());
            cst.registerOutParameter(2, Types.BOOLEAN);
            cst.execute();
            premium = cst.getBoolean(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return premium;
    }

    public int calculateTotal(int carId, int nbDays) {
        CallableStatement cst = null;
        String query = "{ ? = call fnViewReceipt ( ?, ? ) }";
        int amount = -1;
        try {
            cst = con.prepareCall(query);
            cst.registerOutParameter(1, Types.INTEGER);
            cst.setInt(2,carId);
            cst.setInt(3, nbDays);
            cst.execute();
            amount = cst.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amount;
    }

}
