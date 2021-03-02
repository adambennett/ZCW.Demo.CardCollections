package Collector.cards;

import Collector.abstracts.*;

import java.util.concurrent.*;

public class Spimoky extends AbstractCard {

    private static final String cardText = "Gains a random amount of ATK and DEF before combat.";

    public Spimoky() {
        super("Spimoky", 0, 0);
        this.text = cardText;
    }

    public Spimoky(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
    }

    @Override
    public void afterDrawn() {
        this.setAttack(ThreadLocalRandom.current().nextInt(3, 20));
        this.setDefend(ThreadLocalRandom.current().nextInt(3, this.getOwner().getMaxHP()));
    }

    @Override
    public Spimoky copy() {
        return new Spimoky(this.getName(), this.getAttack(), this.getDefend());
    }
}
