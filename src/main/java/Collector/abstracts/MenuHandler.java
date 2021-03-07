package Collector.abstracts;

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
        optionWidth = 30;
        descriptionWidth = 90;

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
    }

    protected abstract void initializeCommands();

    public String printMenu() {
        var commandWidth = this.commandWidth;
        var optionWidth = this.optionWidth;
        var descriptionWidth = this.descriptionWidth;

        ColumnFormatter<String> commandFormatter = ColumnFormatter.text(Alignment.CENTER, commandWidth);
        ColumnFormatter<String> optionFormatter = ColumnFormatter.text(Alignment.CENTER, optionWidth);
        ColumnFormatter<String> descriptionFormatter = ColumnFormatter.text(Alignment.CENTER, descriptionWidth);

        var commandList = new ArrayList<String>();
        var optionList = new ArrayList<String>();
        var descriptionList = new ArrayList<String>();

        commandList.add(ScreenPrinter.lineBreak(commandWidth));
        optionList.add(ScreenPrinter.lineBreak(commandWidth));
        descriptionList.add(ScreenPrinter.lineBreak(commandWidth));

        for (var entry : this.commands.entrySet()) {
            commandList.add("" + entry.getKey());
            optionList.add(this.commandDisplayText.get(entry.getValue()));
            descriptionList.add(this.commandDescriptionText.get(entry.getValue()));
        }

        commandList.add("" + this.quitCommand);
        optionList.add(this.quitOption);
        descriptionList.add(this.quitDescription);

        commandList.add(ScreenPrinter.lineBreak(commandWidth));
        optionList.add(ScreenPrinter.lineBreak(commandWidth));
        descriptionList.add(ScreenPrinter.lineBreak(commandWidth));

        String[] commands = commandList.toArray(new String[0]);
        String[] options = optionList.toArray(new String[0]);
        String[] descriptions = descriptionList.toArray(new String[0]);

        Table.Builder builder = new Table.Builder("Command", commands, commandFormatter);

        builder.addColumn("Option", options, optionFormatter);
        builder.addColumn("Description", descriptions, descriptionFormatter);

        return builder.build().toString();
    }

    public void badCommand(String originalPrompt) {
        System.out.println("Bad input!\n");
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
            this.runFuncPortion(command);
        } else {
            this.runFuncPortion(command);
            this.runTextPortion(command);
        }
    }

    private void runTextPortion(MenuCommand command) {
        var prompt = this.commandText.getOrDefault(command, "");
        System.out.println(prompt);
    }

    private void runFuncPortion(MenuCommand command) {
        var func = commandFunctions.getOrDefault(command, () -> {});
        func.runCommand();
    }
}
