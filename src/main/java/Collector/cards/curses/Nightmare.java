package Collector.cards.curses;

import Collector.abstracts.*;

public class Nightmare extends Curse {

    private static final String cardText = "When drawn, gain a -!M! ATK bonus for the rest of the battle. Exhaust.";
    private static final Integer baseMagic = 2;

    public Nightmare() {
        super("Nightmare", 13, 0);
        this.text = cardText;
        this.magic = baseMagic;
        this.isExhaust = true;
    }

    public Nightmare(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.magic = baseMagic;
        this.isExhaust = true;
    }

    @Override
    protected void curse() {
        this.getOwner().increasePermAttackBonus(-this.getMagic());
    }

    @Override
    public Nightmare copy() {
        return new Nightmare(this.getName(), this.getAttack(), this.getDefend());
    }
}
