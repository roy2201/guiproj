package Models;

import Interfaces.PaymentStrategy;
import com.example.mainapp.Database;
import lombok.Getter;
import lombok.Setter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

@Getter
@Setter
public class CreditCardModel implements PaymentStrategy {

    private int cardNumber, cvv;
    private String expiryDate;
    private String cardHolderName;
    private int amount;

    Connection con;

    //constructor1
    public CreditCardModel(int cardNumber, int cvv, String expiryDate, String cardHolderName, int amount) {
        Database db = null;
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert db != null;
        con = db.connect();
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.cardHolderName = cardHolderName;
        this.amount = amount;
    }

    //constructor2
    public CreditCardModel(int cardNumber, int cvv, String expiryDate, String cardHolderName) {
        Database db = null;
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert db != null;
        con = db.connect();
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.cardHolderName = cardHolderName;
    }

    //pay through credit card, handle possible errors
    @Override
    public boolean pay(int amount) {
        System.out.println("Payed "+amount +" dollars in credit card");
        String query = "exec spPayCredit ?, ?, ?";
        CallableStatement cst;
        try {
            cst = con.prepareCall(query);
            cst.setInt(1,amount);
            cst.setInt(2, this.cardNumber);
            System.out.println("card number : " + cardNumber);
            cst.registerOutParameter(3, Types.INTEGER);
            cst.execute();
            int errorCode = cst.getInt(3);
            if(errorCode == 2) return false;
            cst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("payed "+amount+" with Credit Card");
        setAmount(amount);
        addTransaction();
        return true;
    }

    //validate credit card : check if logged in customer is paying with existing card
    public boolean isValid(int creditCardNumber, int cvv, String expiryDate, String holderName) {
        String query = "exec isValidCreditCard ?, ?, ?, ?, ?";
        try {
            CallableStatement cst = con.prepareCall(query);
            cst.setString(1, holderName);
            cst.setInt(2, cvv);
            cst.setInt(3, creditCardNumber);
            cst.registerOutParameter(4, Types.INTEGER);
            cst.setInt(5, LoginModel.getLogged());
            cst.execute();
            int errorCode = cst.getInt(4);
            if(errorCode == 1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //add credit card transaction to database, so that the customer can review it anytime
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
    }
}