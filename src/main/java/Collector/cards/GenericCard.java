package Collector.cards;

import Collector.abstracts.*;

public class GenericCard extends AbstractCard {

    public GenericCard(String name, int atk, int def) {
        super(name, atk, def);
    }

    public GenericCard copy() {
        return new GenericCard(this.getName(), this.getAttack(), this.getDefend());
    }

}
