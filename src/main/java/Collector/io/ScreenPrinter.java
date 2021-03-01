package Collector.io;

import Collector.abstracts.*;
import Collector.enums.*;
import Collector.logic.*;
import Collector.models.*;
import jdk.jshell.execution.*;

import java.util.*;

public class ScreenPrinter {

    public static Boolean gameLoop() {
        return getStringInput("Would you like to do a card battle? Y/N:\n").toLowerCase().contains("y");
    }

    public static void gameOver(boolean win) {
        if (win) {
            System.out.println("\n\nGame over!\n\nYou win!!");
        } else {
            System.out.println("\n\nGame over!\n\nYou lost. Try again next time.");
        }
    }

    public static Game gameSetup() {
        var input = getStringInput("Welcome to the card battle! What is your name?\n");
        var hpOption = getStringInput("How much HP should each player start with?\n");
        try {
            var hp = Integer.parseInt(hpOption);
            var cards = Integer.parseInt(getStringInput("How many cards per deck?\n"));
            return new Game(input, hp, cards);
        } catch (Exception ex) {
            return new Game(input, 50, 30);
        }
    }

    public static void startGame() {
        System.out.println("The battle begins!");
    }

    public static void drawSummary(Player human, ComputerEnemy opponent) {
        var player = human.getCurrentCard();
        var enemy = opponent.getCurrentCard();
        System.out.println("\nPlayer drew: " + player.getName() + " -- " + player.getAttack() + " / " + player.getDefend());
        System.out.println("Enemy drew: " + enemy.getName() + " -- " + enemy.getAttack() + " / " + enemy.getDefend() + "\n");
    }

    public static CombatMove promptUserAfterDraw() {
        var input = getStringInput("\nAttack or Defend? A/D:\n");
        return input.equalsIgnoreCase("a") ? CombatMove.ATTACK : CombatMove.DEFEND;
    }

    public static void combatSummary(String results, Game game) {
        System.out.println("\nCombat Summary:\n" + results + "\nPlayer HP: " + game.getHuman().getCurrentHP() + "\nEnemy HP:" + game.getComputer().getCurrentHP());
    }

    public static String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        return scanner.nextLine();
    }


}
