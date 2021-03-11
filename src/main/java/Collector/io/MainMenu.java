package Collector.io;

import Collector.*;
import Collector.abstracts.*;
import Collector.enums.*;
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
        CardBattle.mainMenu = this;
        this.player = player;
        this.menuName = "Main Menu - " + this.player.getName();

        this.commands.put(1, MenuCommand.LOCAL_GAME);
        this.commands.put(2, MenuCommand.LOCAL_BRAWL);
        this.commands.put(3, MenuCommand.PLAY_ONLINE);
        this.commands.put(4, MenuCommand.EDIT_LOCAL_DECK);
        this.commands.put(5, MenuCommand.CHANGE_HP);
        this.commands.put(6, MenuCommand.CHANGE_NAME);
        this.commands.put(7, MenuCommand.CARD_LIBRARY);
        this.commands.put(8, MenuCommand.KEYWORDS);
        this.commands.put(9, MenuCommand.DELETE_PLAYER);

        this.commandDisplayText.put(MenuCommand.LOCAL_GAME, "Local Battle");
        this.commandDisplayText.put(MenuCommand.LOCAL_BRAWL, "Local Brawl");
        this.commandDisplayText.put(MenuCommand.PLAY_ONLINE, "Play Online");
        this.commandDisplayText.put(MenuCommand.EDIT_LOCAL_DECK, "Edit Local Deck");
        this.commandDisplayText.put(MenuCommand.CHANGE_HP, "Change Starting HP");
        this.commandDisplayText.put(MenuCommand.CHANGE_NAME, "Change Name");
        this.commandDisplayText.put(MenuCommand.CARD_LIBRARY, "Card Library");
        this.commandDisplayText.put(MenuCommand.KEYWORDS, "Keyword Glossary");
        this.commandDisplayText.put(MenuCommand.DELETE_PLAYER, "Delete Account");

        this.commandDescriptionText.put(MenuCommand.LOCAL_GAME, "Start a standard battle against " + Game.getEnemyName(player));
        this.commandDescriptionText.put(MenuCommand.LOCAL_BRAWL, "Choose a special battle to start against " + Game.getEnemyName(player));
        this.commandDescriptionText.put(MenuCommand.PLAY_ONLINE, "Connect to the CardCollector online service to host and join networked games");
        this.commandDescriptionText.put(MenuCommand.EDIT_LOCAL_DECK, "Modify the contents of your deck");
        this.commandDescriptionText.put(MenuCommand.CHANGE_HP, "Modify starting battle HP");
        this.commandDescriptionText.put(MenuCommand.CHANGE_NAME, "Change your player name");
        this.commandDescriptionText.put(MenuCommand.CARD_LIBRARY, "View the entire list of cards");
        this.commandDescriptionText.put(MenuCommand.KEYWORDS, "View a list of all keywords and definitions of terms that appear on cards");
        this.commandDescriptionText.put(MenuCommand.DELETE_PLAYER, "Delete your account");

        this.commandFunctions.put(MenuCommand.GO_BACK, (unused) -> {
            if (PlayerArchive.save()) {
                System.out.println("\nLogged off.\n\n");
            }
            CardBattle.navigation.initializeMenus();
        });
        this.commandFunctions.put(MenuCommand.LOCAL_GAME, (unused) -> Game.startGame(this.player));

        this.commandFunctions.put(MenuCommand.LOCAL_BRAWL, this::notImplemented);

        this.commandFunctions.put(MenuCommand.PLAY_ONLINE, this::notImplemented);

        this.commandFunctions.put(MenuCommand.EDIT_LOCAL_DECK, (unused) -> {
            var menu = new DeckModifyMenu();
            menu.setPlayer(player);
            CardBattle.navigation.goToMenu(menu);
        });

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

        this.commandFunctions.put(MenuCommand.KEYWORDS, this::notImplemented);

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
