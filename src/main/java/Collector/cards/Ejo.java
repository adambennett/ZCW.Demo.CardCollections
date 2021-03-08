package Collector.cards;

import Collector.models.*;

public class Ejo extends CollectorCard {

    private static final String cardText = "When you draw this card, add !M! random Curses to your opponent's deck.";
    private static final Integer baseMagic = 3;

    public Ejo() {
        super("Ejo", 3, 6);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onDrawn(Player enemy) {
        for (var i = 0; i < this.getMagic(); i++) {
            var curse = CardArchive.randomCurse();
            if (curse == null) return;
            enemy.getDeck().add(curse, false, true);
        }
    }
}
