package Collector.io;

import Collector.abstracts.*;

public class MenuNavigation {

    private MenuHandler activeMenu;

    public void initializeMenus() { this.goToMenu(new InitialMenu()); }

    public void goToMenu(MenuHandler menu) {
        this.activeMenu = menu;
        var prompt = this.activeMenu.printMenu();
        var command = ScreenPrinter.getStringInput(prompt);
        try {
            var intCommand = Integer.parseInt(command);
            this.activeMenu.processCommand(intCommand, prompt);
        } catch (Exception ex) {
            this.activeMenu.badCommand(prompt);
        }
    }

    public void loadMenu() {
        this.goToMenu(this.activeMenu);
    }
}
