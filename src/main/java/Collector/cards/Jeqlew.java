package Collector.cards;

import Collector.abstracts.*;

public class Jeqlew extends AbstractCard {

    private static final String cardText = "When drawn, if Keflew or Seplew are in your deck, gain +!M! ATK bonus for the rest of the game.";
    private static final Integer baseMagic = 10;

    public Jeqlew() {
        super("Jeqlew", 9, 10);
        this.text = cardText;
        this.magic = baseMagic;
    }

    public Jeqlew(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onDrawn() {
        var hasCard = this.getOwner().getDeck().isInDeck("Keplew") || this.getOwner().getDeck().isInDeck("Seplew");
        if (!hasCard) return;

        this.getOwner().increasePermAttackBonus(this.getMagic());
    }

    @Override
    public Jeqlew copy() {
        return new Jeqlew(this.getName(), this.getAttack(), this.getDefend());
    }
}
