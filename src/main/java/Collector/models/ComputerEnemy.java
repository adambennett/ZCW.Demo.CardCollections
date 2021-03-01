package Collector.models;

import Collector.abstracts.*;
import Collector.enums.*;

import java.util.*;

public class ComputerEnemy extends Player {

    private Boolean isEnraged;
    private Boolean isCautious;
    private Boolean isLowHP;

    public ComputerEnemy(String name, String deckName, int maxHP, Collection<AbstractCard> cards) {
        super(name, deckName, maxHP, cards);
    }


    // We both attack
        // Is my attack == their attack?
            // If so => Nothing happens (0)
        // Is my attack > their attack?
            // If so  => I deal damage = my attack (+MyATK * atkMod)
            // If not => I take damage = his attack (-EnemyATK)

    // We both defend
        // Is my heal amount == 0?
            // If so  => They might heal (-EnemyHeal)
        // Is my heal amount == their heal amount?
            // If so  => Nothing happens (0)
        // Is my heal amount > their heal amount?
            // If so  => I am ahead (+MyHeal * healMod)
            // If not => I am behind (-EnemyHeal)

    // I attack, they defend
        // Is my attack > their defend?
            // If so  => I deal damage = difference (+(MyATK - EnemyDEF) * atkMod)
            // If not => Nothing happens (0)

    // I defend, they attack
        // Is my defend >= their attack?
            // If so  => Nothing happens (0)
            // If not => I take damage = (-(EnemyATK - MyDEF))
    public CombatMove calculateMove(Player enemy) {

        AbstractCard playerCard = enemy.getCurrentCard();

        double healMod = 0.8 + (isLowHP ? 1.2 : 0.0) + (isCautious ? 0.3 : 0.0);
        double atkMod = 1.0 + (isEnraged ? 2.0 : 0.0) - (isCautious ? 0.5 : 0.0);

        int myAtk = this.getCurrentCard().getAttack();
        int enemyAtk = playerCard.getAttack();

        int myDefend = this.getCurrentCard().getDefend();
        int enemyDefend = playerCard.getDefend();

        int enemyHeal = enemyDefend;
        while (enemyHeal > (enemy.getMaxHP() - enemy.getCurrentHP())) {
            enemyHeal--;
        }

        int myHeal = myDefend;
        while (myHeal > (this.getMaxHP() - this.getCurrentHP())) {
            myHeal--;
        }

        double bothAttackScore = myAtk == enemyAtk ? 0 : myAtk > enemyAtk ? (myAtk * atkMod) : (-enemyAtk);
        double bothDefendScore = myHeal == 0 && enemyHeal > 0 ? -enemyHeal : myHeal == enemyHeal ? 0 : myHeal > enemyHeal ? (myHeal * healMod) : (-enemyHeal);
        double myAttackScore = myAtk > enemyDefend ? ((myAtk - enemyDefend) * atkMod) : 0;
        double myDefendScore = myDefend >= enemyAtk ? 0 : (-(enemyAtk - myDefend));

        double attackScore = bothAttackScore + myAttackScore;
        double defendScore = bothDefendScore + myDefendScore;
        if (attackScore > defendScore) {
            return CombatMove.ATTACK;
        }
        return CombatMove.DEFEND;
    }
}
