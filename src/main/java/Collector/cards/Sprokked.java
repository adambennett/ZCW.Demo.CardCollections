package Collector.cards;

import Collector.abstracts.*;
import Collector.models.*;

public class Sprokked extends CollectorCard {

    private static final String cardText = "After combat, lose !M! HP.";
    private static final Integer baseMagic = 10;

    public Sprokked() {
        super("Sprokked", 19, 5);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void afterCombat(AbstractCard enemyCard) {
        this.getOwner().damage(this.getMagic(), this.getOwner());
    }
}
