package Collector.io;

import Collector.abstracts.*;
import Collector.enums.*;
import Collector.models.*;
import Collector.utilities.*;

public class MainMenu extends MenuHandler {

    private Player player;

    @Override
    protected void initializeCommands() {
        this.menuName = "Main Menu";
        this.commands.put(1, MenuCommand.START_GAME);
        this.commands.put(2, MenuCommand.EDIT_DECK);
        this.commands.put(3, MenuCommand.CHANGE_HP);
        this.commands.put(4, MenuCommand.CHANGE_NAME);
        this.commands.put(5, MenuCommand.CARD_LIBRARY);
        this.commands.put(9, MenuCommand.DELETE_PLAYER);


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

    public void setPlayer(Player player) {
        this.player = player;
    }
}
