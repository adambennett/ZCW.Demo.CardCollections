package Collector.cards;

import Collector.interfaces.*;
import Collector.models.*;

public class Powrel extends CollectorCard implements Vicious {

    private static final String cardText = "Vicious.";

    public Powrel() {
        super("Powrel", 9, 3);
        this.text = cardText;
    }
}
