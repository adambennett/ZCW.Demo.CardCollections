package Collector.models;

import Collector.abstracts.*;

public class CollectorCard extends AbstractCard {

    public CollectorCard(String name, int atk, Integer def) {
        super(name, atk, def);
    }

    @Override
    public CollectorCard copy() {
        var copy = new CollectorCard(this.getName(), this.getAttack(), this.getDefend());
        copy.setOwner(this.getOwner());
        copy.setMagic(this.magic);
        copy.setText(this.text);
        copy.isExhaust = this.isExhaust;
        return copy;
    }
}
