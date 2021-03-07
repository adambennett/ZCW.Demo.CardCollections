package Collector.cards;

import Collector.interfaces.*;
import Collector.models.*;

public class Powrel extends CollectorCard implements Vicious {

    private static final String cardText = "Vicious.";

    public Powrel() {
        super("Powrel", 9, 3);
        this.text = cardText;
    }

    public Powrel(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
    }

    @Override
    public Powrel copy() {
        return new Powrel(this.getName(), this.getAttack(), this.getDefend());
    }
}