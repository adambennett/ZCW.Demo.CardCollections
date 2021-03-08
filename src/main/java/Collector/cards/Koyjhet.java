package Collector.cards;

import Collector.abstracts.*;
import Collector.models.*;

public class Koyjhet extends CollectorCard {

    private static final String cardText = "If you stalemate this round, gain +!M! ATK for the rest of the game.";
    private static final Integer baseMagic = 8;

    public Koyjhet() {
        super("Koyjhet", 6, 7);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onStalemate(AbstractCard enemyCard) {
        this.getOwner().increasePermAttackBonus(this.getMagic());
    }
}
