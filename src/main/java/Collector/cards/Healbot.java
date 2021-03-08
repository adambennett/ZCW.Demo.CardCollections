package Collector.cards;

import Collector.models.*;

public class Healbot extends CollectorCard {

    private static final String cardText = "When drawn, heal !M! HP.";
    private static final Integer baseMagic = 10;

    public Healbot() {
        super("Healbot", 0, 0);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onDrawn(Player enemy) {
        this.getOwner().heal(this.getMagic());
    }
}
