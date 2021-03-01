package Collector.interfaces;

import Collector.abstracts.*;

@FunctionalInterface
public interface UnaryCardHook {

    void hook(AbstractCard card);

}
