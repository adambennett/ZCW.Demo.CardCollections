package Collector.cards;

import Collector.abstracts.*;
import Collector.interfaces.*;
import Collector.models.*;

import java.util.concurrent.*;

public class Hewslet extends CollectorCard implements Defender {

    private static final String cardText = "Defender !M!.";
    private static final Integer baseMagic = 4;

    public Hewslet() {
        super("Hewslet", 11, 6);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public Boolean autoWinCombat(AbstractCard enemyCard) {
        if (this.getMagic() < 1) return false;
        return ThreadLocalRandom.current().nextInt(0, this.getMagic()) == 0;
    }
}
