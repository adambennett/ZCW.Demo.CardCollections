package Collector.cards;

import Collector.abstracts.*;
import Collector.models.*;

public class Rhearst extends CollectorCard {

    private static final String cardText = "Give your opponent -!M! DEF for this combat. Exhaust.";
    private static final Integer baseMagic = 25;

    public Rhearst() {
        super("Rhearst", 3, 3);
        this.text = cardText;
        this.isExhaust = true;
        this.magic = baseMagic;
    }

    public Rhearst(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onPlay(AbstractCard enemyCard) {
        enemyCard.getOwner().increaseDefenseBonus(-this.getMagic());
    }

    @Override
    public Rhearst copy() {
        return new Rhearst(this.getName(), this.getAttack(), this.getDefend());
    }
}
