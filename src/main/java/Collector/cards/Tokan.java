package Collector.cards;

import Collector.models.*;

public class Tokan extends CollectorCard {

    private static final String cardText = "When drawn, gain !M! maximum HP.";
    private static final Integer baseMagic = 5;

    public Tokan() {
        super("Tokan", 0, 4);
        this.text = cardText;
        this.magic = baseMagic;
    }

    public Tokan(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onDrawn(Player enemy) {
        this.getOwner().increaseMaxHP(this.getMagic());
    }

    @Override
    public Tokan copy() {
        return new Tokan(this.getName(), this.getAttack(), this.getDefend());
    }
}
