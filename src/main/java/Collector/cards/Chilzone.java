package Collector.cards;

import Collector.abstracts.*;
import Collector.enums.*;
import Collector.interfaces.*;
import Collector.models.*;

public class Chilzone extends CollectorCard implements Unique {

    private static final String cardText = "If you defend with this card, reduce the enemy DEF by !M!.";
    private static final Integer baseMagic = 8;

    public Chilzone() {
        super("Chilzone", 11, 10);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onPlay(AbstractCard enemyCard, CombatMove playerMove) {
        if (playerMove == CombatMove.DEFEND) {
            enemyCard.setDefend(enemyCard.getDefend() - this.getMagic());
        }
    }
}
