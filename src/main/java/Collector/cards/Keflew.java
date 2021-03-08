package Collector.cards;

import Collector.abstracts.*;
import Collector.interfaces.*;
import Collector.models.*;

public class Keflew extends CollectorCard implements Unique {

    private static final String cardText = "After combat, loses 2 ATK.";

    public Keflew() {
        super("Keflew", 16, 9);
        this.text = cardText;
    }

    @Override
    public void afterCombat(AbstractCard enemyCard) {
        this.setAttack(this.getAttack() - 2);
    }
}
