package Interfaces;

public interface Iterator {

    void init();
    boolean hasNext();
    void next();
    PaymentStrategy getCurr();

}