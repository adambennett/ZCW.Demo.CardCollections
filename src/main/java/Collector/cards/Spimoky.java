package Collector.cards;

import Collector.abstracts.*;

import java.util.concurrent.*;

public class Spimoky extends AbstractCard {

    private static final String cardText = "When you draw this card, set its ATK and DEF to random values.";
    private static final Integer baseMagic = 5;

    public Spimoky() {
        super("Spimoky", -1, null);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void afterDrawn() {
        if (this.getMagic() <= 0) {
            return;
        }
        this.setAttack(ThreadLocalRandom.current().nextInt(0, this.getMagic() * 2));
        this.setDefend(ThreadLocalRandom.current().nextInt(0, this.getMagic() * 2));
    }

    @Override
    public void afterCombat(AbstractCard enemyCard) {
        System.out.print("\n" + this.getOwner().getName() + "'s " + this.getName() + " rolled stats: " + this.getAttack() + " / " + this.getDefend());
    }

    @Override
    public Spimoky copy() {
        return new Spimoky();
    }
}
