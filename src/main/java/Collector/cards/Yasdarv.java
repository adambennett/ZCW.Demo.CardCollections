package Collector.cards;

import Collector.abstracts.*;
import Collector.interfaces.*;
import Collector.models.*;

import java.util.concurrent.*;

public class Yasdarv extends CollectorCard implements Unique {

    private static final String cardText = "When you draw this card, copy a random card into your deck and increase it's magic number by !M!.";
    private static final Integer baseMagic = 10;

    public Yasdarv() {
        super("Yasdarv", 11, 8);
        this.text = cardText;
        this.magic = baseMagic;
    }

    public Yasdarv(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void onDrawn(Player enemy) {
        if (this.getOwner().getDeck().size() < 1) return;
        AbstractCard card;
        var list = this.getOwner().getDeck().getList();
        if (this.getOwner().getDeck().size() == 1) {
            card = list.get(0);
        } else {
            var size = this.getOwner().getDeck().size();
            card = list.get(ThreadLocalRandom.current().nextInt(size - 1)).copy();
        }

        card.setMagic(card.getMagic() + this.getMagic());
        this.getOwner().getDeck().add(card, false, true);
    }

    @Override
    public Yasdarv copy() {
        return new Yasdarv(this.getName(), this.getAttack(), this.getDefend());
    }
}
