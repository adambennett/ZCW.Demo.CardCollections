package Collector;

import Collector.io.*;
import Collector.logic.*;

public class CardBattle { public static void main(String[] args) { main(); }

    public static MenuNavigation navigation;
    public static Game currentGame;

    static { navigation = new MenuNavigation(); }

    public static void main() {
        navigation.initializeMenus();
    }



}
