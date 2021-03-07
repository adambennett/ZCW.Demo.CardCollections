package Collector.io;

import Collector.abstracts.*;
import Collector.enums.*;
import Collector.models.*;
import Collector.utilities.*;

import java.util.*;

public class LoadPlayerMenu extends MenuHandler {

    private Map<Integer, Player> players;

    @Override
    protected void initializeCommands() {
        this.menuName = "Load Player";
        this.players = new HashMap<>();
        var players = PlayerArchive.list();
        for (var i = 1; i < players.size(); i++) {
            var player = players.get(i);
            this.commands.put(i, MenuCommand.CHOOSE_PLAYER);
            this.players.put(i, player);
        }
        this.commandText.put(MenuCommand.CHOOSE_PLAYER, "Player loaded!");
        this.textBeforeFunctionality.put(MenuCommand.CHOOSE_PLAYER, true);
        this.commandFunctions.put(MenuCommand.CHOOSE_PLAYER, () -> {});
        this.commands.put(0, MenuCommand.GO_BACK);
        this.commandText.put(MenuCommand.GO_BACK, "");

        this.commandFunctions.put(MenuCommand.GO_BACK, () -> {

        });

        this.commands.put(1, MenuCommand.LOAD_PLAYER);
        this.commands.put(2, MenuCommand.NEW_PLAYER);
        this.commandText.put(MenuCommand.LOAD_PLAYER, "");
        this.commandText.put(MenuCommand.NEW_PLAYER, "");
        this.textBeforeFunctionality.put(MenuCommand.LOAD_PLAYER, true);
        this.textBeforeFunctionality.put(MenuCommand.NEW_PLAYER, true);

        this.commandFunctions.put(MenuCommand.LOAD_PLAYER, () -> {

        });

        this.commandFunctions.put(MenuCommand.NEW_PLAYER, () -> {

            // Player name
            var input = ScreenPrinter.getStringInput("Welcome to the card battle! What is your name?\n");
            if (input == null || input.equalsIgnoreCase("")) {
                input = Constants.DefaultPlayerName;
            }

            // HP and random starting cards
            int hp;
            int cards;
            try {
                hp = Integer.parseInt(ScreenPrinter.getStringInput("How starting HP should this player have?\n"));
            } catch (Exception ex) {
                hp = Constants.DefaultStartHP;
            }
            try {
                cards = Integer.parseInt(ScreenPrinter.getStringInput("How many random cards would you like to add to your starting deck?\n"));
            } catch (Exception ex) {
                cards = Constants.DefaultStartCards;
            }
            var player = new Player(input, hp, cards);
            PlayerArchive.add(player);
            // loaded menu
        });
    }
}
