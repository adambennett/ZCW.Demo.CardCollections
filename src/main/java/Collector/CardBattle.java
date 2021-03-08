package Collector;

import Collector.io.*;
import Collector.models.*;

public class CardBattle {

    public static MenuNavigation navigation;
    public static Game currentGame;
    public static Boolean gameIsStarted = false;

    public static void main(String[] args) {
        navigation = new MenuNavigation();
        navigation.initializeMenus();
    }

    public static void loadPlayer(Player player) {
        var loadedMenu = new MainMenu();
        loadedMenu.setPlayer(player);
        navigation.goToMenu(loadedMenu);
    }
}
