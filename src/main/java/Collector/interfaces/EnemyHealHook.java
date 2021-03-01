package Collector.interfaces;

import Collector.abstracts.*;

@FunctionalInterface
public interface EnemyHealHook {

    void enemyHeal(AbstractCard enemyCard, int healedFor, int hpAfterHealing);
}
