package Collector.cards;

import Collector.abstracts.*;
import Collector.enums.*;
import Collector.models.*;

public class Nyoxide extends CollectorCard {

    private static final String cardText = "Increase the magic number of all cards in your deck by !M!.";
    private static final Integer baseMagic = 2;

    public Nyoxide() {
        super("Nyoxide", 4, 9);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onPlay(AbstractCard enemyCard, CombatMove playerMove) {
        for (var c : getOwner().getDeck()) {
            c.setMagic(c.getMagic() + this.getMagic());
        }
    }
}
