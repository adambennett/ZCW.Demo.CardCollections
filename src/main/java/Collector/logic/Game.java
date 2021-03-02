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

    // Set game settings - prompt
    // Load decks
    // Load players
    // Start game

    // Draw a card for each player
    //* Modify cards from residual effects
    // Calculate enemy move
    // Display cards drawn to screen
    // Prompt for user move - display either "enemy has chosen" OR enemy's exact move (if has Vision)
    // Process user input
    // Handle card effects
    // Calculate combat events
    // Display combat summary
    // Repeat until a player dies

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
        Player human = new Player(playerName, playerName + "'s Deck", startingHP, playerCards);
        ComputerEnemy computer = new ComputerEnemy(computerName, computerName + "'s Deck", startingHP, computerCards);
        this.human = human;
        this.computer = computer;
        return this;
    }

    public Game playGame() {
        while (!isFinished()) {
            this.human.setCurrentCard(this.human.getDeck().draw(1).get(0));
            this.computer.setCurrentCard(this.computer.getDeck().draw(1).get(0));
            var enemyMove = this.computer.calculateMove(this.human);
            ScreenPrinter.drawSummary(this.human, this.computer);
            var playerMove = ScreenPrinter.promptUserAfterDraw();
            var results = Game.calculateCombat(playerMove, enemyMove, this.human, this.computer);
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

    public static String calculateCombat(CombatMove playerMove, CombatMove enemyMove, Player human, ComputerEnemy computer) {
        var playerCard = human.getCurrentCard();
        var enemyCard = computer.getCurrentCard();

        if (playerMove == CombatMove.ATTACK && enemyMove == CombatMove.ATTACK) {
            if (playerCard.getAttack() > enemyCard.getAttack()) {
                computer.damage(playerCard.getAttack());
                return "Enemy took " + playerCard.getAttack() + " damage.";
            } else if (playerCard.getAttack() < enemyCard.getAttack()) {
                human.damage(enemyCard.getAttack());
                return "You took " + enemyCard.getAttack() + " damage.";
            } else {
                return "Both players attacked, but nothing happened.";
            }
        } else if (playerMove == CombatMove.DEFEND && enemyMove == CombatMove.DEFEND) {
            human.heal(playerCard.getDefend());
            computer.heal(enemyCard.getDefend());
            return "Both players healed.";
        } else if (playerMove == CombatMove.ATTACK) {
            if (playerCard.getAttack() > enemyCard.getDefend()) {
                var dmg = playerCard.getAttack() - enemyCard.getDefend();
                computer.damage(dmg);
                return "Enemy took " + dmg + " damage.";
            } else {
                return "The enemy defended your attack!";
            }
        } else if (playerMove == CombatMove.DEFEND) {
            if (enemyCard.getAttack() > playerCard.getDefend()) {
                var dmg = enemyCard.getAttack() - playerCard.getDefend();
                human.damage(dmg);
                return  "You took " + dmg + " damage.";
            } else {
                return "You defended the enemy attack!";
            }
        } else {
            return "Something unknown occurred?";
        }
    }

    public Player getHuman() {
        return human;
    }

    public ComputerEnemy getComputer() {
        return computer;
    }
}
