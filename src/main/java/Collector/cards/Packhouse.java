package Collector.cards;

import Collector.abstracts.*;
import Collector.models.*;

public class Packhouse extends CollectorCard {

    private static final String cardText = "If you attack with this card, add !M! copies of Tokan to your deck.";
    private static final Integer baseMagic = 3;

    public Packhouse() {
        super("Packhouse", 2, 3);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onAttack(AbstractCard enemyCard) {
        for (var i = 0; i < this.getMagic(); i++) {
            this.getOwner().getDeck().add(new Tokan(), true, false);
        }
    }
}
