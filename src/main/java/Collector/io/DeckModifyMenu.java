package Collector.io;

import Collector.*;
import Collector.abstracts.*;
import Collector.enums.*;
import Collector.models.*;

public class DeckModifyMenu extends MenuHandler {

    @Override
    protected void initializeCommands() {
        this.quitCommand = 0;
        this.quitOption = "Return";
        this.quitDescription = "Go back to the Main Menu";
    }

    public void setPlayer(Player player) {
        this.menuName = "Deck Editing Menu";
        this.commands.put(1, MenuCommand.ADD_CARDS);
        this.commands.put(2, MenuCommand.REMOVE_CARDS);

        this.commandDisplayText.put(MenuCommand.ADD_CARDS, "Add Cards");
        this.commandDisplayText.put(MenuCommand.REMOVE_CARDS, "Remove Cards");

        this.commandDescriptionText.put(MenuCommand.ADD_CARDS, "Add cards to your deck");
        this.commandDescriptionText.put(MenuCommand.REMOVE_CARDS, "Remove cards from your deck");

        this.commandFunctions.put(MenuCommand.GO_BACK, (unused) -> CardBattle.navigation.goToMenu(CardBattle.mainMenu));

        this.commandFunctions.put(MenuCommand.ADD_CARDS, this::notImplemented);

        this.commandFunctions.put(MenuCommand.REMOVE_CARDS, (unused) -> {
            var menu = new RemoveCardsMenu();
            menu.setPlayer(player, this);
            CardBattle.navigation.goToMenu(menu);
        });
    }
}
