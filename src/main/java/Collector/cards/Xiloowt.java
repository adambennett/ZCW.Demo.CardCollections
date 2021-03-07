package Collector.cards;

import Collector.abstracts.*;
import Collector.models.*;

import java.util.*;

public class Xiloowt extends CollectorCard {

    private static final String cardText = "If you attack, heal !M! HP. If you defend, add !M! copies of this card to your deck.";
    private static final Integer baseMagic = 2;

    public Xiloowt() {
        super("Xiloowt", 4, 6);
        this.text = cardText;
        this.magic = baseMagic;
    }

    public Xiloowt(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onAttack(AbstractCard enemyCard) {
        this.getOwner().heal(this.getMagic());
    }

    @Override
    public void onDefend(AbstractCard enemyCard) {
        var list = new ArrayList<AbstractCard>();
        for (var i = 0; i < this.getMagic(); i++) {
            list.add(this.copy());
        }
        this.getOwner().getDeck().addAll(list, false, true);
    }

    @Override
    public Xiloowt copy() {
        return new Xiloowt(this.getName(), this.getAttack(), this.getDefend());
    }
}
