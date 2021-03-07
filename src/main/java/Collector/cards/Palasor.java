package Collector.cards;

import Collector.abstracts.*;
import Collector.models.*;

public class Palasor extends CollectorCard {

    private static final String cardText = "If you defend with this card, give all cards in your deck +!M! ATK and DEF.";
    private static final Integer baseMagic = 4;

    public Palasor() {
        super("Palasor", 7, 0);
        this.text = cardText;
        this.magic = baseMagic;
    }

    public Palasor(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onDefend(AbstractCard enemyCard) {
        for (var c : this.getOwner().getDeck()) {
            c.setAttack(c.getAttack() + this.getMagic());
            c.setDefend(c.getDefend() + this.getMagic());
        }
    }

    @Override
    public Palasor copy() {
        return new Palasor(this.getName(), this.getAttack(), this.getDefend());
    }
}
