package Collector.cards;

import Collector.abstracts.*;
import Collector.models.*;

public class Seplew extends AbstractCard {

    private static final String cardText = "When drawn, add !M! random cards to your deck.";
    private static final Integer baseMagic = 6;

    public Seplew() {
        super("Seplew", 6, 6);
        this.text = cardText;
        this.magic = baseMagic;
    }

    public Seplew(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onDrawn() {
        for (var i = 0; i < this.getMagic(); i++) {
            this.getOwner().getDeck().add(CardArchive.randomCard(), true, false);
        }
    }

    @Override
    public Seplew copy() {
        return new Seplew(this.getName(), this.getAttack(), this.getDefend());
    }
}
