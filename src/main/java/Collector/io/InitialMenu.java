package Collector.io;

import Collector.*;
import Collector.abstracts.*;
import Collector.enums.*;
import Collector.models.*;
import Collector.utilities.*;

public class InitialMenu extends MenuHandler {

    @Override
    protected void initializeCommands() {
        this.menuName = "Card Collector";
        this.commands.put(1, MenuCommand.NEW_PLAYER);
        this.commands.put(2, MenuCommand.LOAD_PLAYER);
        this.commandText.put(MenuCommand.LOAD_PLAYER, "");
        this.commandText.put(MenuCommand.NEW_PLAYER, "");
        this.textBeforeFunctionality.put(MenuCommand.LOAD_PLAYER, true);
        this.textBeforeFunctionality.put(MenuCommand.NEW_PLAYER, true);
        this.commandDisplayText.put(MenuCommand.NEW_PLAYER, "New Player");
        this.commandDisplayText.put(MenuCommand.LOAD_PLAYER, "Load Player");
        this.commandDescriptionText.put(MenuCommand.NEW_PLAYER, "Create a new player");
        this.commandDescriptionText.put(MenuCommand.LOAD_PLAYER, "Load an existing player");

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

            // Save player
            var player = new Player(input, hp, cards);
            PlayerArchive.add(player);

            // Load main menu
            var loadedMenu = new MainMenu();
            loadedMenu.setPlayer(player);
            CardBattle.navigation.goToMenu(loadedMenu);
        });
    }
}
