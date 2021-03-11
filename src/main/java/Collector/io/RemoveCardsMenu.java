package Collector.io;

import Collector.*;
import Collector.abstracts.*;
import Collector.enums.*;
import Collector.models.*;
import io.bretty.console.table.*;

import java.util.*;

public class RemoveCardsMenu extends MenuHandler {

    private HashMap<Integer, AbstractCard> cards;
    private Player player;

    public void setPlayer(Player player, DeckModifyMenu lastMenu) {
        this.player = player;
        this.menuName = "Removing Cards";

        this.cards = new HashMap<>();
        var deck = this.player.getDeck().getList();
        deck.sort(Comparator.comparing(AbstractCard::getName));
        for (var i = 1; i < deck.size() + 1; i++) {
            var card = deck.get(i - 1);
            this.commands.put(i, MenuCommand.CHOOSE_CARD);
            this.cards.put(i, card);
        }

        this.commandText.put(MenuCommand.CHOOSE_CARD, "Card removed!");
        this.commandFunctions.put(MenuCommand.GO_BACK, (unused) -> CardBattle.navigation.goToMenu(lastMenu));

        this.commandFunctions.put(MenuCommand.CHOOSE_CARD, (index) -> {
           var card = this.cards.getOrDefault(index, null);
           if (card == null) {
               this.badCommand(this.printMenu());
               return;
           }
           this.player.getDeck().remove(card);
           this.cards.remove(index);
            CardBattle.navigation.goToMenu(this);
        });
    }

    @Override
    protected void initializeCommands() {
        this.quitCommand = 0;
        this.quitOption = "Return";
        this.quitDescription = "Go back to the Deck Editing Menu";
        this.cardMenu = true;
    }

    @Override
    public int generateCardMenu(Table.Builder builder) {

        var nameList = new ArrayList<String>();
        var atkList = new ArrayList<String>();
        var defList = new ArrayList<String>();
        var textList = new ArrayList<String>();

        for (var entry : this.cards.entrySet()) {
            var card = entry.getValue();
            var name = card.getName();
            var atk = card.getAttack();
            var def = card.getDefend();
            var text = card.getText();
            nameList.add(descSpacer() + name);
            atkList.add("" + atk);
            defList.add("" + def);
            textList.add(descSpacer() + text);
        }

        int nameWidth = getWidth(nameList, 25, 10);
        int atkWidth = getWidth(atkList, 12, 4);
        int defWidth = getWidth(defList, 12, 4);
        int textWidth = getWidth(textList, 90, 10);
        ColumnFormatter<String> nameFormatter = ColumnFormatter.text(Alignment.LEFT, nameWidth);
        ColumnFormatter<String> atkFormatter = ColumnFormatter.text(Alignment.CENTER, atkWidth);
        ColumnFormatter<String> defFormatter = ColumnFormatter.text(Alignment.CENTER, defWidth);
        ColumnFormatter<String> textFormatter = ColumnFormatter.text(Alignment.LEFT, textWidth);

        nameList.add(ScreenPrinter.lineBreak(nameWidth));
        atkList.add(ScreenPrinter.lineBreak(atkWidth));
        defList.add(ScreenPrinter.lineBreak(defWidth));
        textList.add(ScreenPrinter.lineBreak(textWidth));

        nameList.add(descSpacer() + this.quitOption);
        atkList.add("--");
        defList.add("--");
        textList.add(descSpacer() + this.quitDescription);

        nameList.add(ScreenPrinter.lineBreak(nameWidth));
        nameList.add(0, ScreenPrinter.lineBreak(nameWidth));

        atkList.add(ScreenPrinter.lineBreak(atkWidth));
        atkList.add(0, ScreenPrinter.lineBreak(atkWidth));

        defList.add(ScreenPrinter.lineBreak(defWidth));
        defList.add(0, ScreenPrinter.lineBreak(defWidth));

        textList.add(ScreenPrinter.lineBreak(textWidth));
        textList.add(0, ScreenPrinter.lineBreak(textWidth));

        String[] names = nameList.toArray(new String[0]);
        String[] atks = atkList.toArray(new String[0]);
        String[] defs = defList.toArray(new String[0]);
        String[] texts = textList.toArray(new String[0]);

        builder.addColumn("Card Name", names, nameFormatter);
        builder.addColumn("ATK", atks, atkFormatter);
        builder.addColumn("DEF", defs, defFormatter);
        builder.addColumn("Text", texts, textFormatter);
        return nameWidth + atkWidth + defWidth + textWidth;
    }

    @Override
    protected void generateInnerMenu(List<String> commandList, List<String> optionList, List<String> descriptionList) {
        for (var entry : this.commands.entrySet()) {
            var card = this.cards.getOrDefault(entry.getKey(), null);
            if (card == null) continue;
            commandList.add("" + entry.getKey());
        }

        commandList.add(ScreenPrinter.lineBreak(this.commandWidth));
        commandList.add("" + this.quitCommand);
    }
}
