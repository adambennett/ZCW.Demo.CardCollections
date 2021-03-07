package Collector.cards;

import Collector.abstracts.*;
import Collector.models.*;

public class Quilx extends CollectorCard {

    private static final String cardText = "When you defend with this card, gain !M! maximum HP for each card left in your deck.";
    private static final Integer baseMagic = 1;

    public Quilx() {
        super("Quilx", 5, 4);
        this.text = cardText;
        this.magic = baseMagic;
    }

    public Quilx(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onDefend(AbstractCard enemyCard) {
        this.getOwner().increaseMaxHP(this.getOwner().getDeck().size());
    }

    @Override
    public Quilx copy() {
        return new Quilx(this.getName(), this.getAttack(), this.getDefend());
    }
}
