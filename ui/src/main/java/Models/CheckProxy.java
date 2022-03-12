package Models;

import Interfaces.Iterable;
import Interfaces.Iterator;
import Interfaces.WindowP;
import com.example.mainapp.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class CheckProxy implements WindowP , Iterable {

    private final ArrayList<CreditCardModel> cards;
    private final ArrayList<PaypalModel> paypalAccounts;
    private final int amount;   //amount that should be paid

    //get PayPal and credit card accounts frm database
    public CheckProxy(int total) {
        Database db = null;
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert false;
        db.connect();
        cards = db.loadCards();
        paypalAccounts = db.loadPaypal();
        this.amount = total;
    }

    @Override
    public CreditCardIterator createIterator() {
        return new CreditCardIterator(cards);
    }

    private PaypalIterator createPaypalIterator() {
        return new PaypalIterator(paypalAccounts);
    }

    //using two different iterators to iterate through different types of objects
    @Override
    //proxy method
    public boolean checkBalance() {
        // iterating through credit cards
        CreditCardIterator it = createIterator();
        for (it.init(); it.hasNext(); it.next()) {
            //System.out.println("credit balance is " + it.getCurr().getAmount());
            if(it.getCurr().getAmount() >= amount)
                return true;
        }
        // iterating through PayPal accounts
        PaypalIterator pit = createPaypalIterator();
        for (pit.init(); pit.hasNext(); pit.next()) {
            //System.out.println("paypal balance is " + pit.getCurr().getAmount());
            //System.out.println("found amount = " + pit.getCurr().getAmount());
            if(pit.getCurr().getAmount() >= amount) {
                return true;
            }
        }
        return false;
    }
}
