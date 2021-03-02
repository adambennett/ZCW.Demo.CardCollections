package Collector.cards;

import Collector.abstracts.*;

public class Koyjhet extends AbstractCard {

    private static final String cardText = "If you stalemate this round, gain +!M! ATK for the rest of the game.";
    private static final Integer baseMagic = 5;

    public Koyjhet() {
        super("Koyjhet", 6, 7);
        this.text = cardText;
        this.magic = baseMagic;
    }

    public Koyjhet(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onStalemate(AbstractCard enemyCard) {
        this.getOwner().increasePermAttackBonus(this.getMagic());
    }

    @Override
    public Koyjhet copy() {
        return new Koyjhet(this.getName(), this.getAttack(), this.getDefend());
    }
}
