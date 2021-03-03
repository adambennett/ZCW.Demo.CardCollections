package Collector.io;

import Collector.abstracts.*;
import Collector.enums.*;
import Collector.logic.*;
import Collector.models.*;
import Collector.utilities.*;
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
        if (input == null || input.equalsIgnoreCase("")) {
            input = Constants.DefaultPlayerName;
        }
        var hpOption = getStringInput("How much HP should each player start with?\n");
        try {
            var hp = Integer.parseInt(hpOption);
            var cards = Integer.parseInt(getStringInput("How many cards per deck?\n"));
            return new Game(input, hp, cards);
        } catch (Exception ex) {
            return new Game(input, Constants.DefaultStartHP, Constants.DefaultStartCards);
        }
    }

    public static void startGame() {
        System.out.println("The battle begins!\n");
    }

    private static int getTextWidth(AbstractCard player, AbstractCard enemy) {
        return (player.getText().length() >= enemy.getText().length()) ? player.getText().length() + 10 : enemy.getText().length() + 10;
    }

    private static int getCardWidth(AbstractCard player, AbstractCard enemy) {
        var width = (player.getName().length() >= enemy.getName().length()) ? player.getName().length() + 6 : enemy.getName().length() + 6;
        return Math.max(width, 30);
    }

    private static int getNameWidth(Player player, Player enemy) {
        var width = (player.getName().length() >= enemy.getName().length()) ? player.getName().length() + 6 : enemy.getName().length() + 6;
        return Math.max(width, 32);
    }

    public static void drawSummary(Player human, ComputerEnemy opponent) {
        var player = human.getCurrentCard();
        var enemy = opponent.getCurrentCard();

        var playerWidth = getNameWidth(human, opponent);
        var cardWidth   = getCardWidth(player, enemy);
        var leftWidth   = 8;
        var atkWidth    = 10;
        var defWidth    = 10;
        var hpWidth     = 12;
        var textWidth = getTextWidth(player, enemy);
        ColumnFormatter<String> playerFormatter = ColumnFormatter.text(Alignment.CENTER, playerWidth);
        ColumnFormatter<String> cardFormatter = ColumnFormatter.text(Alignment.CENTER, cardWidth);
        ColumnFormatter<String> cardsLeftFormatter = ColumnFormatter.text(Alignment.CENTER, leftWidth);
        ColumnFormatter<String> atkFormatter = ColumnFormatter.text(Alignment.CENTER, atkWidth);
        ColumnFormatter<String> defFormatter = ColumnFormatter.text(Alignment.CENTER, defWidth);
        ColumnFormatter<String> textFormatter = ColumnFormatter.text(Alignment.CENTER, textWidth);
        ColumnFormatter<String> hpFormatter = ColumnFormatter.text(Alignment.CENTER, hpWidth);

        String[] players = new String[]{lineBreak(playerWidth), opponent.getName(), human.getName(), lineBreak(playerWidth)};
        String[] cards = new String[]{lineBreak(cardWidth), opponent.displayCard(), human.displayCard(), lineBreak(cardWidth)};
        String[] cardsLeft = new String[]{lineBreak(leftWidth), "" + opponent.getDeck().size(), "" + human.getDeck().size(), lineBreak(leftWidth)};
        String[] atks = new String[]{lineBreak(atkWidth), opponent.displayAttack(), human.displayAttack(), lineBreak(atkWidth)};
        String[] defs = new String[]{lineBreak(defWidth), opponent.displayDefense(), human.displayDefense(), lineBreak(defWidth)};
        String[] texts = new String[]{lineBreak(textWidth), enemy.getText(), player.getText(), lineBreak(textWidth)};
        String[] hps = new String[]{lineBreak(hpWidth), opponent.getCurrentHP() + " / " + opponent.getMaxHP(), human.getCurrentHP() + " / " + human.getMaxHP(), lineBreak(hpWidth)};

        Table.Builder builder = new Table.Builder("Player", players, playerFormatter);

        builder.addColumn("HP", hps, hpFormatter);
        builder.addColumn("Cards", cardsLeft, cardsLeftFormatter);
        builder.addColumn("Active Card", cards, cardFormatter);
        builder.addColumn("ATK", atks, atkFormatter);
        builder.addColumn("DEF", defs, defFormatter);
        builder.addColumn("Card Text", texts, textFormatter);

        Table table = builder.build();
        System.out.println(table);
    }

    private static String lineBreak(int length) {
        return lineBreak(length, null);
    }

    private static String lineBreak(int length, String spacer) {
        StringBuilder out = new StringBuilder();
        if (spacer == null) {
            spacer = "-";
        }
        out.append(spacer.repeat(Math.max(0, length)));
        return out.toString();
    }

    public static CombatMove promptUserAfterDraw() {
        var input = getStringInput("\nAttack or Defend? A/D:\n");
        return input.equalsIgnoreCase("a") ? CombatMove.ATTACK : CombatMove.DEFEND;
    }

    public static void combatSummary(String results, Game game) {
        System.out.println("\n\nCOMBAT SUMMARY:\n" + results + "\n\n");
    }

    public static String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        return scanner.nextLine();
    }


}
