package Collector.cards;

import Collector.models.*;

public class Halfrot extends CollectorCard {

    private static final String cardText = "Exhaust.";

    public Halfrot() {
        super("Halfrot", 20, 20);
        this.text = cardText;
        this.isExhaust = true;
    }
}
