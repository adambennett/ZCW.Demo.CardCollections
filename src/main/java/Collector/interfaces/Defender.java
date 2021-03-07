package Collector.interfaces;

import Collector.abstracts.*;

public interface Defender {

    default Boolean autoWinCombat(AbstractCard enemyCard) {
        return false;
    }

}
