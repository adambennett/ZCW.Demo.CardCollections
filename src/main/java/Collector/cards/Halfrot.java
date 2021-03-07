package Collector.cards;

import Collector.models.*;

public class Halfrot extends CollectorCard {

    private static final String cardText = "Exhaust.";

    public Halfrot() {
        super("Halfrot", 20, 20);
        this.text = cardText;
        this.isExhaust = true;
    }

    public Halfrot(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.isExhaust = true;
    }

    @Override
    public Halfrot copy() {
        return new Halfrot(this.getName(), this.getAttack(), this.getDefend());
    }
}
