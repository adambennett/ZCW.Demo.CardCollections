package Collector.io;

import Collector.*;
import Collector.abstracts.*;
import Collector.enums.*;
import Collector.logic.*;
import Collector.models.*;

public class MainMenu extends MenuHandler {

    private Player player;

    @Override
    protected void initializeCommands() {
        this.quitCommand = 0;
        this.quitOption = "Logout";
        this.quitDescription = "Log off and return to the Card Collector Menu";
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.menuName = "Main Menu - " + this.player.getName();

        this.commands.put(1, MenuCommand.START_COMBAT);
        this.commands.put(2, MenuCommand.START_BRAWL);
        this.commands.put(3, MenuCommand.EDIT_DECK);
        this.commands.put(4, MenuCommand.CHANGE_HP);
        this.commands.put(5, MenuCommand.CHANGE_NAME);
        this.commands.put(6, MenuCommand.CARD_LIBRARY);
        this.commands.put(9, MenuCommand.DELETE_PLAYER);

        this.commandText.put(MenuCommand.START_COMBAT, "");
        this.commandText.put(MenuCommand.START_BRAWL, "");
        this.commandText.put(MenuCommand.EDIT_DECK, "");
        this.commandText.put(MenuCommand.CHANGE_HP, "");
        this.commandText.put(MenuCommand.CHANGE_NAME, "");
        this.commandText.put(MenuCommand.CARD_LIBRARY, "");
        this.commandText.put(MenuCommand.DELETE_PLAYER, "");

        this.commandDisplayText.put(MenuCommand.START_COMBAT, "Start Battle");
        this.commandDisplayText.put(MenuCommand.START_BRAWL, "Start Brawl");
        this.commandDisplayText.put(MenuCommand.EDIT_DECK, "Edit Deck");
        this.commandDisplayText.put(MenuCommand.CHANGE_HP, "Change Starting HP");
        this.commandDisplayText.put(MenuCommand.CHANGE_NAME, "Change Name");
        this.commandDisplayText.put(MenuCommand.CARD_LIBRARY, "Card Library");
        this.commandDisplayText.put(MenuCommand.DELETE_PLAYER, "Delete Player");

        this.commandDescriptionText.put(MenuCommand.START_COMBAT, "Start a standard battle against a random enemy");
        this.commandDescriptionText.put(MenuCommand.START_BRAWL, "Choose a special battle to start against a random enemy");
        this.commandDescriptionText.put(MenuCommand.EDIT_DECK, "Modify the contents of your deck");
        this.commandDescriptionText.put(MenuCommand.CHANGE_HP, "Modify starting battle HP");
        this.commandDescriptionText.put(MenuCommand.CHANGE_NAME, "Change your player name");
        this.commandDescriptionText.put(MenuCommand.CARD_LIBRARY, "View the entire list of cards");
        this.commandDescriptionText.put(MenuCommand.DELETE_PLAYER, "Delete this player");

        this.commandFunctions.put(MenuCommand.GO_BACK, (unused) -> {
            if (PlayerArchive.save()) {
                System.out.println("\nLogged off.\n\n");
            }
            CardBattle.navigation.initializeMenus();
        });
        this.commandFunctions.put(MenuCommand.START_COMBAT, (unused) -> Game.startGame(this.player));

        this.commandFunctions.put(MenuCommand.START_BRAWL, this::notImplemented);

        this.commandFunctions.put(MenuCommand.EDIT_DECK, this::notImplemented);

        this.commandFunctions.put(MenuCommand.CHANGE_HP, (unused) -> {
            var input = ScreenPrinter.getStringInput("What would you like to change your starting HP to?");
            try {
                var num = Integer.parseInt(input);
                var newPlayer = new Player(this.player.getName(), num, this.player.getDeck().getList());
                if (!PlayerArchive.modifyPlayer(newPlayer, this.player.getName())) {
                    System.out.println("Error modifying starting HP!");
                    CardBattle.navigation.loadMenu();
                    return;
                }
                System.out.println("\nStarting HP modified!\n");
                CardBattle.loadPlayer(PlayerArchive.get(input));
            } catch (Exception ex) { CardBattle.navigation.loadMenu(); }
        });

        this.commandFunctions.put(MenuCommand.CHANGE_NAME, (unused) -> {
            var input = ScreenPrinter.getStringInput("What would you like to change your name to?");
            var newPlayer = new Player(input, this.player.getMaxHP(), this.player.getDeck().getList());
            if (!PlayerArchive.modifyPlayer(newPlayer, this.player.getName())) {
                System.out.println("Player already exists!");
                CardBattle.navigation.loadMenu();
                return;
            }
            CardBattle.loadPlayer(PlayerArchive.get(input));
        });

        this.commandFunctions.put(MenuCommand.CARD_LIBRARY, (unused) -> {
            System.out.println(CardArchive.printArchive());
            ScreenPrinter.getStringInput("\n\nPress any key to return to the Main Menu\n");
            CardBattle.navigation.loadMenu();
        });

        this.commandFunctions.put(MenuCommand.DELETE_PLAYER, (unused) -> {
            var input = ScreenPrinter.getStringInput("Are you sure you want to delete this player? This cannot be undone. Enter the player name exactly to confirm deletion: \n");
            if (!input.equals(this.player.getName())) {
                CardBattle.navigation.loadMenu();
                return;
            }
            PlayerArchive.remove(input);
            CardBattle.navigation.initializeMenus();
        });
    }
}
