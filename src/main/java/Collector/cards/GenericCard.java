package Collector.cards;

import Collector.models.*;

public class GenericCard extends CollectorCard {

    public GenericCard(String name, int atk, int def) {
        super(name, atk, def);
    }

    public GenericCard copy() {
        return new GenericCard(this.getName(), this.getAttack(), this.getDefend());
    }

}
