package Collector.cards;

import Collector.abstracts.*;

public class Healbot extends AbstractCard {

    private static final String cardText = "When drawn, heal 10 HP.";
    private static final Integer baseMagic = 10;

    public Healbot() {
        super("Healbot", 0, 0);
        this.text = cardText;
        this.magic = baseMagic;
    }

    public Healbot(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onDrawn() {
        this.getOwner().heal(this.getMagic());
    }

    @Override
    public Healbot copy() {
        return new Healbot(this.getName(), this.getAttack(), this.getDefend());
    }
}
