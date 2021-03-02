package Collector.io;

import Collector.abstracts.*;
import Collector.enums.*;
import Collector.logic.*;
import Collector.models.*;
import io.bretty.console.table.*;

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

        ColumnFormatter<String> playerFormatter = ColumnFormatter.text(Alignment.CENTER, 35);
        ColumnFormatter<String> cardFormatter = ColumnFormatter.text(Alignment.CENTER, 35);
        ColumnFormatter<String> atkFormatter = ColumnFormatter.text(Alignment.CENTER, 6);
        ColumnFormatter<String> defFormatter = ColumnFormatter.text(Alignment.CENTER, 6);
        ColumnFormatter<String> textFormatter = ColumnFormatter.text(Alignment.CENTER, 80);

        String[] players = new String[]{"-----------------------------------", opponent.getName() + " - " + opponent.getCurrentHP() + "/" + opponent.getMaxHP(), human.getName() + " - " + human.getCurrentHP() + "/" + human.getMaxHP(), "-----------------------------------"};
        String[] cards = new String[]{"-----------------------------------", enemy.getName(), player.getName(), "-----------------------------------"};
        String[] atks = new String[]{"------", "" + (enemy.getAttack() + opponent.getAttackBonus()), "" + (player.getAttack() + human.getAttackBonus()), "------"};
        String[] defs = new String[]{"------", "" + (enemy.getDefend() + opponent.getDefendBonus()), "" + (player.getDefend() + human.getDefendBonus()), "------"};
        String[] texts = new String[]{"--------------------------------------------------------------------------------", enemy.getText(), player.getText(), "--------------------------------------------------------------------------------"};

        Table.Builder builder = new Table.Builder("Player", players, playerFormatter);

        builder.addColumn("Card Drawn", cards, cardFormatter);
        builder.addColumn("ATK", atks, atkFormatter);
        builder.addColumn("DEF", defs, defFormatter);
        builder.addColumn("Card Text", texts, textFormatter);

        Table table = builder.build();
        System.out.println(table);

        //System.out.println("\nPlayer drew: " + player.getName() + " -- " + player.getAttack() + " / " + player.getDefend());
        //System.out.println("Enemy drew: " + enemy.getName() + " -- " + enemy.getAttack() + " / " + enemy.getDefend() + "\n");
    }

    public static CombatMove promptUserAfterDraw() {
        var input = getStringInput("\nAttack or Defend? A/D:\n");
        return input.equalsIgnoreCase("a") ? CombatMove.ATTACK : CombatMove.DEFEND;
    }

    public static void combatSummary(String results, Game game) {
        System.out.println("\nCombat Summary:\n" + results);
    }

    public static String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        return scanner.nextLine();
    }


}
