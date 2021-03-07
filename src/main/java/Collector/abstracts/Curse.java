package Collector.abstracts;

import Collector.models.*;

public abstract class Curse extends CollectorCard {

    public Curse(String name, int atk, Integer def) {
        super(name, atk, def);
    }

    protected abstract void curse();

    @Override
    public abstract Curse copy();

    @Override
    public void onDrawn(Player enemy) { curse(); }
}
