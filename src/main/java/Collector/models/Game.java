package Collector.models;

import Collector.*;
import Collector.enums.*;
import Collector.interfaces.*;
import Collector.io.*;
import Collector.utilities.*;

public class Game {

    private record CombatResults(CombatResult result, String text) {}

    private Player human;
    private ComputerEnemy computer;
    private Integer turn;
    private Integer stalemates;
    private Integer viciousCombats;

    public static void startGame(Player player) {
        CardBattle.gameIsStarted = true;
        CardBattle.currentGame = new Game();
        CardBattle.currentGame.setup(player).playGame().finishGame();
        CardBattle.gameIsStarted = false;
        CardBattle.currentGame.getHuman().resetAfterCombat();
        CardBattle.navigation.loadMenu();
    }

    private Game setup(Player player) {
        this.human = player;
        this.computer = new ComputerEnemy(getEnemyName(player), player.getMaxHP(), player.getDeck().size());
        this.turn = 0;
        this.stalemates = 0;
        this.viciousCombats = 0;
        return this;
    }

    public static String getEnemyName(Player player) {
        if (player.getWins() > 99) {
            return Constants.FifthEnemy;
        }
        if (player.getWins() > 49) {
            return Constants.FourthEnemy;
        }
        if (player.getWins() > 9) {
            return Constants.ThirdEnemy;
        }
        if (player.getWins() > 4) {
            return Constants.SecondEnemy;
        }
        return Constants.FirstEnemy;
    }

    private Game playGame() {
        while (!isFinished()) {

            // 'Discard' last turn cards (or exhaust)
            if (this.human.getCurrentCard() != null) {
                var card = this.human.getCurrentCard();
                if (card.isExhaust()) {
                    this.human.getCurrentCard().onExhaust();
                } else {
                    this.human.getCurrentCard().onDiscard();
                }
            }
            if (this.computer.getCurrentCard() != null) {
                var card = this.computer.getCurrentCard();
                if (card.isExhaust()) {
                    this.computer.getCurrentCard().onExhaust();
                } else {
                    this.computer.getCurrentCard().onDiscard();
                }
            }

            // Draw cards
            this.human.setCurrentCard(this.human.draw(1, this.computer).get(0));
            this.computer.setCurrentCard(this.computer.draw(1, this.human).get(0));

            // Calculate enemy move
            var enemyMove = this.computer.calculateMove(this.human);

            // afterDraw() hooks and summary
            ScreenPrinter.drawSummary(this.human, this.computer);
            this.human.getCurrentCard().afterDrawn();
            this.computer.getCurrentCard().afterDrawn();

            // Get user move and trigger onPlay() hooks
            var playerMove = ScreenPrinter.promptUserAfterDraw();
            this.human.getCurrentCard().onPlay(this.computer.getCurrentCard(), playerMove);
            this.computer.getCurrentCard().onPlay(this.human.getCurrentCard(), playerMove);

            // Combat Step
            var results = Game.calculateCombat(playerMove, enemyMove, this.human, this.computer, false);

            // Update battle stats
            this.turn++;
            this.human.getStats().hasAttacked = playerMove == CombatMove.ATTACK || this.human.getStats().hasAttacked;
            this.human.getStats().hasDefended = playerMove == CombatMove.DEFEND || this.human.getStats().hasDefended;
            this.computer.getStats().hasAttacked = enemyMove == CombatMove.ATTACK || this.computer.getStats().hasAttacked;
            this.computer.getStats().hasDefended = enemyMove == CombatMove.DEFEND || this.computer.getStats().hasDefended;
            this.human.getStats().turnsSurvived++;
            this.computer.getStats().turnsSurvived++;
            switch (results.result()) {
                case STALEMATE -> this.stalemates++;
                case VICIOUS -> this.viciousCombats++;
            }

            // afterCombat() hooks and show summary
            this.human.getCurrentCard().afterCombat(this.computer.getCurrentCard());
            this.computer.getCurrentCard().afterCombat(this.human.getCurrentCard());
            ScreenPrinter.combatSummary(results.text(), this);
        }
        return this;
    }

    private Boolean isFinished() {
        return this.human.getCurrentHP() < 1 || this.computer.getCurrentHP() < 1;
    }

    private void finishGame() {
        ScreenPrinter.gameOver(this.human.getCurrentHP() > this.computer.getCurrentHP());
    }

    public static CombatResults calculateCombat(CombatMove playerMove, CombatMove enemyMove, Player human, ComputerEnemy computer, boolean simulated) {
        var playerCard = human.getCurrentCard();
        var enemyCard = computer.getCurrentCard();
        String results;
        CombatResult result = null;

        // BOTH ATTACK
        if (playerMove == CombatMove.ATTACK && enemyMove == CombatMove.ATTACK) {
            if (playerCard.getAttack() > enemyCard.getAttack()) {
                computer.damage(human.getFullDamage(), human);
                results = "Vicious combat! Enemy took " + human.getFullDamage() + " damage.";
            } else if (playerCard.getAttack() < enemyCard.getAttack()) {
                human.damage(computer.getFullDamage(), computer);
                results = "Vicious combat! You took " + computer.getFullDamage() + " damage.";
            } else {
                results = "Both players attacked, but nothing happened.";
            }
            if (!simulated) {
                enemyCard.onAttack(playerCard);
                enemyCard.onViciousCombat(playerCard);
                playerCard.onAttack(enemyCard);
                playerCard.onViciousCombat(enemyCard);
                result = CombatResult.VICIOUS;
            }
        }

        // BOTH DEFEND
        else if (playerMove == CombatMove.DEFEND && enemyMove == CombatMove.DEFEND) {
            human.heal(playerCard.getDefend());
            computer.heal(enemyCard.getDefend());
            if (!simulated) {
                playerCard.onDefend(enemyCard);
                playerCard.onStalemate(enemyCard);
                enemyCard.onDefend(playerCard);
                enemyCard.onStalemate(playerCard);
                result = CombatResult.STALEMATE;
            }
            results = "Stalemate - both players healed.";
        }

        // PLAYER ATTACK - COMPUTER DEFEND
        else if (playerMove == CombatMove.ATTACK) {
            boolean enemyAutoWins = enemyCard instanceof Defender defCard && defCard.autoWinCombat(playerCard);
            if (human.getFullDamage() > computer.getFullDefense() && !enemyAutoWins) {
                var dmg = human.getFullDamage() - computer.getFullDefense();
                computer.damage(dmg, human);
                results = "Enemy took " + dmg + " damage.";
            } else {
                results = "The enemy defended your attack!";
            }
            if (!simulated) {
                playerCard.onAttack(enemyCard);
                enemyCard.onDefend(playerCard);
                result = CombatResult.COMBAT;
            }
        }

        // PLAYER DEFEND - COMPUTER ATTACK
        else if (playerMove == CombatMove.DEFEND) {
            boolean playerAutoWins = playerCard instanceof Defender defCard && defCard.autoWinCombat(enemyCard);
            if (computer.getFullDamage() > human.getFullDefense() && !playerAutoWins) {
                var dmg = computer.getFullDamage() - human.getFullDefense();
                human.damage(dmg, computer);
                results =  "You took " + dmg + " damage.";
            } else {
                results = "You defended the enemy attack!";
            }
            if (!simulated) {
                playerCard.onDefend(enemyCard);
                enemyCard.onAttack(playerCard);
                result = CombatResult.COMBAT;
            }
        } else {
            results = "Something unknown occurred?";
        }

        // return summary for ScreenPrinter
        return new CombatResults(result, results);
    }

    public Player getHuman() {
        return human;
    }

    public ComputerEnemy getComputer() {
        return computer;
    }

    public Integer getTurn() {
        return turn;
    }

    public Integer getStalemates() {
        return stalemates;
    }

    public Integer getViciousCombats() {
        return viciousCombats;
    }
}
