package Collector.abstracts;

import Collector.*;
import Collector.enums.*;
import Collector.interfaces.*;
import Collector.io.*;
import io.bretty.console.table.*;

import java.util.*;

public abstract class MenuHandler {

    protected final Map<Integer, MenuCommand> commands;
    protected final Map<MenuCommand, String> commandText;
    protected final Map<MenuCommand, String> commandDisplayText;
    protected final Map<MenuCommand, String> commandDescriptionText;
    protected final Map<MenuCommand, MenuFunction> commandFunctions;
    protected final Map<MenuCommand, Boolean> textBeforeFunctionality;
    protected String menuName;
    protected String quitOption;
    protected String quitDescription;
    protected Integer quitCommand;
    protected Integer commandWidth;
    protected Integer optionWidth;
    protected Integer descriptionWidth;

    public MenuHandler() {
        commandWidth = 10;
        optionWidth = 20;
        descriptionWidth = 40;

        quitCommand = 0;
        quitOption = "Quit";
        quitDescription = "Exit the application";

        commands = new HashMap<>();
        commandText = new HashMap<>();
        commandDisplayText = new HashMap<>();
        commandDescriptionText = new HashMap<>();
        commandFunctions = new HashMap<>();
        textBeforeFunctionality = new HashMap<>();

        this.initializeCommands();
        this.autoQuitSetup();
    }

    protected abstract void initializeCommands();

    public static String descSpacer() {
        return "    ";
    }

    protected void generateInnerMenu(List<String> commandList, List<String> optionList, List<String> descriptionList) {
        for (var entry : this.commands.entrySet()) {
            commandList.add("" + entry.getKey());
            optionList.add(descSpacer() + this.commandDisplayText.get(entry.getValue()));
            descriptionList.add(descSpacer() + this.commandDescriptionText.get(entry.getValue()));
        }
        var quitCommand = commandList.remove(1);
        var quitOption = optionList.remove(1);
        var quitDesc = descriptionList.remove(1);
        commandList.add(quitCommand);
        optionList.add(quitOption);
        descriptionList.add(quitDesc);
    }

    private Integer getDescWidth() {
        var highest = 0;
        for (var desc : this.commandDescriptionText.entrySet()) {
            highest = Math.max(desc.getValue().length(), highest);
        }
        return Math.max(this.descriptionWidth, highest + 10);
    }

    private Integer getOptionWidth() {
        var highest = 0;
        for (var desc : this.commandDisplayText.entrySet()) {
            highest = Math.max(desc.getValue().length(), highest);
        }
        return Math.max(this.optionWidth, highest + 10);
    }

    public String printMenu() {
        var commandWidth = this.commandWidth;
        var optionWidth = getOptionWidth();
        var descriptionWidth = getDescWidth();
        var totalWidth = commandWidth + optionWidth + descriptionWidth + 4;

        ColumnFormatter<String> commandFormatter = ColumnFormatter.text(Alignment.CENTER, commandWidth);
        ColumnFormatter<String> optionFormatter = ColumnFormatter.text(Alignment.LEFT, optionWidth);
        ColumnFormatter<String> descriptionFormatter = ColumnFormatter.text(Alignment.LEFT, descriptionWidth);

        var commandList = new ArrayList<String>();
        var optionList = new ArrayList<String>();
        var descriptionList = new ArrayList<String>();

        commandList.add(ScreenPrinter.lineBreak(commandWidth));
        optionList.add(ScreenPrinter.lineBreak(optionWidth));
        descriptionList.add(ScreenPrinter.lineBreak(descriptionWidth));

        generateInnerMenu(commandList, optionList, descriptionList);

        commandList.add(ScreenPrinter.lineBreak(commandWidth));
        optionList.add(ScreenPrinter.lineBreak(optionWidth));
        descriptionList.add(ScreenPrinter.lineBreak(descriptionWidth));

        String[] commands = commandList.toArray(new String[0]);
        String[] options = optionList.toArray(new String[0]);
        String[] descriptions = descriptionList.toArray(new String[0]);

        Table.Builder builder = new Table.Builder("Command", commands, commandFormatter);

        builder.addColumn("Option", options, optionFormatter);
        builder.addColumn("Description", descriptions, descriptionFormatter);



        return "\n" + ScreenPrinter.spacer(totalWidth / 3) + this.menuName + "\n" + ScreenPrinter.lineBreak(totalWidth) + "\n" + builder.build();
    }

    public void notImplemented(int input) {
        System.out.println("Option " + input + " not yet implemented!\n\n");
        CardBattle.navigation.goToMenu(this);
    }

    public void badCommand(String originalPrompt) {
        if (!CardBattle.gameIsStarted) {
            System.out.println("Bad input!\n");
        }
        var command = ScreenPrinter.getStringInput(originalPrompt);
        try {
            var intCommand = Integer.parseInt(command);
            processCommand(intCommand, originalPrompt);
        } catch (Exception ex) {
            badCommand(originalPrompt);
        }
    }

    public void processCommand(Integer input, String originalPrompt) {
        if (this.commands.size() < 1) {
            System.out.println("This menu is not properly configured!");
            return;
        }
        var command = this.commands.getOrDefault(input, null);
        if (command == null) {
            badCommand(originalPrompt);
            return;
        }

        var textFirst = textBeforeFunctionality.getOrDefault(command, true);
        if (textFirst) {
            this.runTextPortion(command);
            this.runFuncPortion(command, input);
        } else {
            this.runFuncPortion(command, input);
            this.runTextPortion(command);
        }
    }

    public void autoQuitSetup() {
        this.commands.put(this.quitCommand, MenuCommand.GO_BACK);
        this.commandText.put(MenuCommand.GO_BACK, "");
        this.commandDisplayText.put(MenuCommand.GO_BACK, this.quitOption);
        this.commandDescriptionText.put(MenuCommand.GO_BACK, this.quitDescription);
    }

    private void runTextPortion(MenuCommand command) {
        var prompt = this.commandText.getOrDefault(command, "");
        System.out.println(prompt);
    }

    private void runFuncPortion(MenuCommand command, int input) {
        var func = commandFunctions.getOrDefault(command, (unused) -> {});
        func.runCommand(input);
    }
}
