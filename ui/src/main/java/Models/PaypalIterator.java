package Models;

import Interfaces.Iterator;

import java.util.List;

class PaypalIterator implements Iterator {

    private final List<PaypalModel> plist;
    private int pos;

    PaypalIterator(List<PaypalModel> plist) {
        this.plist = plist;
    }

    @Override
    public void init() {
        pos = 0;
    }

    @Override
    public boolean hasNext() {
        return (pos < plist.size());
    }

    @Override
    public void next() {
        pos++;
        System.out.println("next to  " + pos);
    }

    @Override
    public PaypalModel getCurr() {
        System.out.println("pos in paypal is : " + pos);
        return plist.get(pos);
    }
}
