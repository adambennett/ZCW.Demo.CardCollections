package Collector.io;

import Collector.abstracts.*;

public class MenuNavigation {

    public void initializeMenus() {
        goToMenu(new InitialMenu());
    }

    public void goToMenu(MenuHandler menu) {
        var prompt = menu.printMenu();
        var command = ScreenPrinter.getStringInput(prompt);
        try {
            var intCommand = Integer.parseInt(command);
            menu.processCommand(intCommand, prompt);
        } catch (Exception ex) {
            menu.badCommand(prompt);
        }
    }
}
