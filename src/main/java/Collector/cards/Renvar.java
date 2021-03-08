package Collector.cards;

import Collector.abstracts.*;
import Collector.models.*;

public class Renvar extends CollectorCard {

    private static final String cardText = "After combat, deal !M! damage to the opponent directly.";
    private static final Integer baseMagic = 5;

    public Renvar() {
        super("Renvar", 3, 3);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void afterCombat(AbstractCard enemyCard) {
        enemyCard.getOwner().damage(this.getMagic(), this.getOwner());
    }
}
