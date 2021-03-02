package Collector.logic;

import Collector.abstracts.*;
import Collector.cards.*;
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
            this.human.setCurrentCard(this.human.draw(1).get(0));
            this.computer.setCurrentCard(this.computer.draw(1).get(0));
            var enemyMove = this.computer.calculateMove(this.human);
            ScreenPrinter.drawSummary(this.human, this.computer);
            this.human.getCurrentCard().afterDrawn();
            this.computer.getCurrentCard().afterDrawn();
            var playerMove = ScreenPrinter.promptUserAfterDraw();
            var results = Game.calculateCombat(playerMove, enemyMove, this.human, this.computer, false);
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

        if (playerMove == CombatMove.ATTACK && enemyMove == CombatMove.ATTACK) {
            if (playerCard.getAttack() > enemyCard.getAttack()) {
                computer.damage(human.getFullDamage());
                return "Vicious combat! Enemy took " + human.getFullDamage() + " damage.";
            } else if (playerCard.getAttack() < enemyCard.getAttack()) {
                human.damage(computer.getFullDamage());
                return "Vicious combat! You took " + computer.getFullDamage() + " damage.";
            } else {
                return "Both players attacked, but nothing happened.";
            }
        } else if (playerMove == CombatMove.DEFEND && enemyMove == CombatMove.DEFEND) {
            human.heal(playerCard.getDefend());
            computer.heal(enemyCard.getDefend());
            if (!simulated) {
                playerCard.onStalemate(enemyCard);
                enemyCard.onStalemate(playerCard);
            }
            return "Stalemate - both players healed.";
        } else if (playerMove == CombatMove.ATTACK) {
            if (human.getFullDamage() > computer.getFullDefense()) {
                var dmg = human.getFullDamage() - computer.getFullDefense();
                computer.damage(dmg);
                return "Enemy took " + dmg + " damage.";
            } else {
                return "The enemy defended your attack!";
            }
        } else if (playerMove == CombatMove.DEFEND) {
            if (computer.getFullDamage() > human.getFullDefense()) {
                var dmg = computer.getFullDamage() - human.getFullDefense();
                human.damage(dmg);
                return  "You took " + dmg + " damage.";
            } else {
                return "You defended the enemy attack!";
            }
        } else {
            return "Something unknown occurred?";
        }
    }
}
