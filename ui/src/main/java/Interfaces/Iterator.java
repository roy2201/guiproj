package Interfaces;

public interface Iterator {

    void init();
    boolean hasNext();
    void next();
    //returning payment strategy because current may be credit card
    //or it can be paypal object -> dynamic binding
    PaymentStrategy getCurr();

}