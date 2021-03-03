package Collector.logic;

import Collector.abstracts.*;
import Collector.enums.*;
import Collector.io.*;
import Collector.models.*;

import java.util.*;

public class Game {

    private final Integer startingHP;
    private final Integer cards;
    private final String playerName;
    private final String computerName;
    private Player human;
    private ComputerEnemy computer;

    public Game(String player, int startHP, int cards) {
        this.playerName = player;
        this.startingHP = startHP;
        this.cards = cards;
        this.computerName = "Kris Younger";
    }

    public static Game createGame() {
        Game newGame = ScreenPrinter.gameSetup();
        ScreenPrinter.startGame();
        return newGame;
    }

    public Game setupGame() {
        Collection<AbstractCard> playerCards = new ArrayList<>();
        Collection<AbstractCard> computerCards = new ArrayList<>();
        for (var i = 0; i < this.cards; i++) {
            playerCards.add(CardArchive.randomCard());
        }
        for (var i = 0; i < this.cards; i++) {
            computerCards.add(CardArchive.randomCard());
        }
        this.human = new Player(playerName, playerName + "'s Deck", startingHP, playerCards);
        this.computer = new ComputerEnemy(computerName, computerName + "'s Deck", startingHP, computerCards);
        return this;
    }

    public Game playGame() {
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
            this.human.setCurrentCard(this.human.draw(1).get(0));
            this.computer.setCurrentCard(this.computer.draw(1).get(0));

            // Calculate enemy move
            var enemyMove = this.computer.calculateMove(this.human);

            // afterDraw() hooks and summary
            ScreenPrinter.drawSummary(this.human, this.computer);
            this.human.getCurrentCard().afterDrawn();
            this.computer.getCurrentCard().afterDrawn();

            // Get user move and trigger onPlay() hooks
            var playerMove = ScreenPrinter.promptUserAfterDraw();
            this.human.getCurrentCard().onPlay(this.computer.getCurrentCard());
            this.computer.getCurrentCard().onPlay(this.human.getCurrentCard());

            // Combat Step
            var results = Game.calculateCombat(playerMove, enemyMove, this.human, this.computer, false);

            // afterCombat() hooks and show summary
            this.human.getCurrentCard().afterCombat(this.computer.getCurrentCard());
            this.computer.getCurrentCard().afterCombat(this.human.getCurrentCard());
            ScreenPrinter.combatSummary(results, this);
        }
        return this;
    }

    private Boolean isFinished() {
        return this.human.getCurrentHP() < 1 || this.computer.getCurrentHP() < 1;
    }

    public void finishGame() {
        ScreenPrinter.gameOver(this.human.getCurrentHP() > this.computer.getCurrentHP());
    }

    public static String calculateCombat(CombatMove playerMove, CombatMove enemyMove, Player human, ComputerEnemy computer, boolean simulated) {
        var playerCard = human.getCurrentCard();
        var enemyCard = computer.getCurrentCard();
        String results;

        // BOTH ATTACK
        if (playerMove == CombatMove.ATTACK && enemyMove == CombatMove.ATTACK) {
            if (playerCard.getAttack() > enemyCard.getAttack()) {
                computer.damage(human.getFullDamage());
                results = "Vicious combat! Enemy took " + human.getFullDamage() + " damage.";
            } else if (playerCard.getAttack() < enemyCard.getAttack()) {
                human.damage(computer.getFullDamage());
                results = "Vicious combat! You took " + computer.getFullDamage() + " damage.";
            } else {
                results = "Both players attacked, but nothing happened.";
            }
            if (!simulated) {
                enemyCard.onAttack(playerCard);
                enemyCard.onViciousCombat(playerCard);
                playerCard.onAttack(enemyCard);
                playerCard.onViciousCombat(enemyCard);
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
            }
            results = "Stalemate - both players healed.";
        }

        // PLAYER ATTACK - COMPUTER DEFEND
        else if (playerMove == CombatMove.ATTACK) {
            if (human.getFullDamage() > computer.getFullDefense()) {
                var dmg = human.getFullDamage() - computer.getFullDefense();
                computer.damage(dmg);
                results = "Enemy took " + dmg + " damage.";
            } else {
                results = "The enemy defended your attack!";
            }
            if (!simulated) {
                playerCard.onAttack(enemyCard);
                enemyCard.onDefend(playerCard);
            }
        }

        // PLAYER DEFEND - COMPUTER ATTACK
        else if (playerMove == CombatMove.DEFEND) {
            if (computer.getFullDamage() > human.getFullDefense()) {
                var dmg = computer.getFullDamage() - human.getFullDefense();
                human.damage(dmg);
                results =  "You took " + dmg + " damage.";
            } else {
                results = "You defended the enemy attack!";
            }
            if (!simulated) {
                playerCard.onDefend(enemyCard);
                enemyCard.onAttack(playerCard);
            }
        } else {
            results = "Something unknown occurred?";
        }

        // return summary for ScreenPrinter
        return results;
    }
}
