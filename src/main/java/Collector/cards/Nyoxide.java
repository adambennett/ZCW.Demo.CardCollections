package Collector.cards;

import Collector.abstracts.*;

public class Nyoxide extends AbstractCard {

    private static final String cardText = "Increase the magic number of all cards in your deck by !M!.";
    private static final Integer baseMagic = 2;

    public Nyoxide() {
        super("Nyoxide", 4, 9);
        this.text = cardText;
        this.magic = baseMagic;
    }

    public Nyoxide(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onPlay(AbstractCard enemyCard) {
        for (var c : getOwner().getDeck()) {
            c.setMagic(c.getMagic() + this.getMagic());
        }
    }

    @Override
    public Nyoxide copy() {
        return new Nyoxide(this.getName(), this.getAttack(), this.getDefend());
    }
}
