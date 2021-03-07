package Collector.cards;

import Collector.models.*;

import java.util.concurrent.*;

public class Bufftoss extends CollectorCard {

    private static final String cardText = "When drawn, gain ATK equal to the ATK of a random card in your deck.";

    public Bufftoss() {
        super("Bufftoss", 2, 5);
        this.text = cardText;
    }

    public Bufftoss(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
    }

    @Override
    public void onDrawn(Player enemy) {
        var deckSize = this.getOwner().getDeck().size();
        int inc;
        if (deckSize == 1) {
            inc = this.getOwner().getDeck().getList().get(0).getAttack();
            this.setAttack(this.getAttack() + inc);
        } else if (deckSize > 0) {
            var randIndex = ThreadLocalRandom.current().nextInt(deckSize - 1);
            inc = this.getOwner().getDeck().getList().get(randIndex).getAttack();
            this.setAttack(this.getAttack() + inc);
        } else {
            return;
        }
        System.out.println(this.getOwner().getName() + "'s " + this.getName() + " had it's ATK increased by " + inc);
    }

    @Override
    public Bufftoss copy() {
        return new Bufftoss(this.getName(), this.getAttack(), this.getDefend());
    }
}
