package Collector.models;

import Collector.abstracts.*;
import Collector.enums.*;
import Collector.logic.*;

import java.util.*;

public class ComputerEnemy extends Player {

    private Boolean isEnraged = false;
    private Boolean isCautious = false;
    private Boolean isLowHP = false;

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

    public CombatMove newCalc(Player enemy) {

        // Both attack
        Player pCopy = enemy.copy();
        ComputerEnemy compCopy = this.copy();
        Game.calculateCombat(CombatMove.ATTACK, CombatMove.ATTACK, pCopy, compCopy);
        var compDamage = compCopy.getCurrentHP();
        var humanDamage = pCopy.getCurrentHP();

        var bothAttackScore = enemy.getCurrentHP() - humanDamage - (this.getCurrentHP() - compDamage);

        // Both defend
        pCopy = enemy.copy();
        compCopy = this.copy();
        Game.calculateCombat(CombatMove.DEFEND, CombatMove.DEFEND, pCopy, compCopy);
        compDamage = compCopy.getCurrentHP();
        humanDamage = pCopy.getCurrentHP();

        var bothDefendScore = enemy.getCurrentHP() - humanDamage - (this.getCurrentHP() - compDamage);

        // My attack
        pCopy = enemy.copy();
        compCopy = this.copy();
        Game.calculateCombat(CombatMove.DEFEND, CombatMove.ATTACK, pCopy, compCopy);
        compDamage = compCopy.getCurrentHP();
        humanDamage = pCopy.getCurrentHP();

        var myAttackScore = enemy.getCurrentHP() - humanDamage - (this.getCurrentHP() - compDamage);

        // Their attack
        pCopy = enemy.copy();
        compCopy = this.copy();
        Game.calculateCombat(CombatMove.ATTACK, CombatMove.DEFEND, pCopy, compCopy);
        compDamage = compCopy.getCurrentHP();
        humanDamage = pCopy.getCurrentHP();

        var myDefendScore = enemy.getCurrentHP() - humanDamage - (this.getCurrentHP() - compDamage);

        // Calculate
        if (bothAttackScore > bothDefendScore && bothAttackScore > myDefendScore) {
            return CombatMove.ATTACK;
        } else if (getCurrentCard().getAttack() >= enemy.getCurrentCard().getAttack() && myAttackScore > bothDefendScore && myAttackScore > myDefendScore) {
            return CombatMove.ATTACK;
        } else {
            return CombatMove.DEFEND;
        }
    }

    @Override
    public ComputerEnemy copy() {
        var out = new ComputerEnemy(this.getName(), this.getDeck().getName(), this.getMaxHP(), this.getDeck().getList());
        out.setCurrentHP(this.getCurrentHP());
        out.setCurrentCard(this.getCurrentCard());
        out.setCautious(this.isCautious);
        out.setEnraged(this.isEnraged);
        return out;
    }

    public CombatMove calculateMove(Player enemy) {

        /*isLowHP = getCurrentHP() <= getMaxHP() * 0.1;

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

        if (myAtk == 0) {
            return CombatMove.DEFEND;
        } else if (myDefend == 0) {
            return CombatMove.ATTACK;
        }

        int takeDefendEnemyAttack = 0;
        int takeAttackEnemyAttack = 0;
        int dealAttackEnemyAttack = 0;
        int dealAttackEnemyDefend = 0;

        takeDefendEnemyAttack = (enemyAtk > myDefend) ? enemyAtk - myDefend : 0;
        takeAttackEnemyAttack = (myAtk > enemyAtk) ? 0 : enemyAtk;
        dealAttackEnemyAttack = (myAtk > enemyAtk) ? myAtk : 0;
        dealAttackEnemyDefend = (myAtk > enemyDefend) ? myAtk - enemyDefend : 0;

        if (healMod > atkMod) {
            if (takeDefendEnemyAttack > takeAttackEnemyAttack) {
                return CombatMove.ATTACK;
            } else {
                return CombatMove.DEFEND;
            }
        } else {

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
        return CombatMove.DEFEND;*/
        return newCalc(enemy);
    }

    public void setEnraged(Boolean enraged) {
        isEnraged = enraged;
    }

    public void setCautious(Boolean cautious) {
        isCautious = cautious;
    }
}
