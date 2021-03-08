package Collector.cards.curses;

import Collector.abstracts.*;

public class Malice extends Curse {

    private static final String cardText = "When drawn, exhaust !M! random cards from your deck. Exhaust.";
    private static final Integer baseMagic = 2;

    public Malice() {
        super("Malice", 5, 0);
        this.text = cardText;
        this.magic = baseMagic;
        this.isExhaust = true;
    }

    public Malice(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.magic = baseMagic;
        this.isExhaust = true;
    }

    @Override
    protected void curse() {
        for (var i = 0; i < this.getMagic(); i++) {
            var card = this.getOwner().getDeck().remove(null);
            if (card == null) return;
            this.getOwner().getExhaust().add(card);
            card.onExhaust();
        }
    }

    @Override
    public Malice copy() {
        return new Malice(this.getName(), this.getAttack(), this.getDefend());
    }
}
