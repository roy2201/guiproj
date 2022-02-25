package Models;

import Interfaces.Iterator;

import java.util.List;

public class CreditCardIterator implements Iterator {

    private final List<CreditCardModel> cards;
    private int pos;

    CreditCardIterator(List<CreditCardModel> cards) {
        this.cards = cards;
    }

    @Override
    public void init() {
        System.out.println("init iterator");
        pos = 0;
    }

    @Override
    public boolean hasNext() {
        return ( pos < cards.size() );
    }

    @Override
    public void next() {
        System.out.println("moving");
        pos++;
    }

    @Override
    public CreditCardModel getCurr() {
        System.out.println("getting current");
        return cards.get(pos);
    }
}
