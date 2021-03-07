package Collector.cards;

import Collector.abstracts.*;
import Collector.enums.*;
import Collector.models.*;

public class Builto extends CollectorCard {

    private static final String cardText = "Gain a DEF bonus for the rest of combat equal to the number of cards left in your deck.";

    public Builto() {
        super("Builto", 4, 12);
        this.text = cardText;
    }

    public Builto(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
    }

    @Override
    public void onPlay(AbstractCard enemyCard, CombatMove playerMove) {
        this.getOwner().increasePermDefenseBonus(this.getOwner().getDeck().size());
    }

    @Override
    public Builto copy() {
        return new Builto(this.getName(), this.getAttack(), this.getDefend());
    }
}
