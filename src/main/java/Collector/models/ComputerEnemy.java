package Collector.models;

import Collector.abstracts.*;
import Collector.enums.*;
import Collector.logic.*;

import java.util.*;

public class ComputerEnemy extends Player {

    public ComputerEnemy(String name, int maxHP, int numCards) {
        super(name, maxHP, numCards);
    }

    public ComputerEnemy(String name, int maxHP, Collection<AbstractCard> cards) {
        super(name, maxHP, cards);
    }

    public CombatMove calculateMove(Player enemy) {
        var bothAttackScore = simulate(CombatMove.ATTACK, CombatMove.ATTACK, enemy);
        var bothDefendScore = simulate(CombatMove.DEFEND, CombatMove.DEFEND, enemy);
        var myAttackScore = simulate(CombatMove.DEFEND, CombatMove.ATTACK, enemy);
        var myDefendScore = simulate(CombatMove.ATTACK, CombatMove.DEFEND, enemy);

        // Calculate
        if (bothAttackScore > bothDefendScore && bothAttackScore > myDefendScore) {
            return CombatMove.ATTACK;
        } else if (getFullDamage() >= enemy.getFullDamage() && myAttackScore > bothDefendScore && myAttackScore > myDefendScore) {
            return CombatMove.ATTACK;
        } else {
            return CombatMove.DEFEND;
        }
    }

    private int simulate(CombatMove simulatedPlayerMove, CombatMove simulatedEnemyMove, Player player) {

        // Copy player objects so we don't actually modify HP values during simulations
        Player playerCopy           = player.copy();
        ComputerEnemy computerCopy  = this.copy();

        // Run the combat step for the desired simulated moves
        Game.calculateCombat(simulatedPlayerMove, simulatedEnemyMove, playerCopy, computerCopy, true);

        // Check actual HP values
        var playerHP     = player.getCurrentHP();
        var computerHP   = this.getCurrentHP();

        // Check simulated HP values
        var playerDamage = playerCopy.getCurrentHP();
        var compDamage   = computerCopy.getCurrentHP();

        // Return score based on comparison of HP values between players
        return playerHP - playerDamage - (computerHP - compDamage);
    }

    @Override
    public ComputerEnemy copy() {
        var out = new ComputerEnemy(this.getName(), this.getMaxHP(), this.getDeck().getList());
        out.setCurrentHP(this.getCurrentHP());
        out.setCurrentCard(this.getCurrentCard());
        return out;
    }
}
