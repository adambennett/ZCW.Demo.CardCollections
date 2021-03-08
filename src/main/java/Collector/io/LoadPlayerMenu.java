package Collector.io;

import Collector.*;
import Collector.abstracts.*;
import Collector.enums.*;
import Collector.models.*;

import java.util.*;

public class LoadPlayerMenu extends MenuHandler {

    private HashMap<Integer, Player> players;

    @Override
    protected void initializeCommands() {
        this.menuName = "Load Player Menu";

        this.quitCommand = 0;
        this.quitOption = "Return";
        this.quitDescription = "Go back to the Card Collector Menu";

        this.players = new HashMap<>();
        var players = PlayerArchive.list();
        for (var i = 1; i < players.size() + 1; i++) {
            var player = players.get(i - 1);
            this.commands.put(i, MenuCommand.CHOOSE_PLAYER);
            this.players.put(i, player);
        }

        this.commandText.put(MenuCommand.CHOOSE_PLAYER, "Player loaded!");

        this.commandFunctions.put(MenuCommand.GO_BACK, (unused) -> CardBattle.navigation.initializeMenus());

        this.commandFunctions.put(MenuCommand.CHOOSE_PLAYER, (index) -> {
            var player = this.players.getOrDefault(index, null);
            if (player == null) {
                this.badCommand(this.printMenu());
                return;
            }
            CardBattle.loadPlayer(player);
        });
    }

    @Override
    protected void generateInnerMenu(List<String> commandList, List<String> optionList, List<String> descriptionList) {
        for (var entry : this.commands.entrySet()) {
            var player = this.players.getOrDefault(entry.getKey(), null);
            if (player == null) continue;
            commandList.add("" + entry.getKey());
            optionList.add(descSpacer() + player.getName());
            descriptionList.add(descSpacer() + player.getMaxHP() + "HP, " + player.getDeck().size() + " cards, " + player.getWins() + "W - " + player.getLosses() + "L");
        }

        commandList.add("" + this.quitCommand);
        optionList.add(descSpacer() + this.quitOption);
        descriptionList.add(descSpacer() + this.quitDescription);
    }
}
