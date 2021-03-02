package Collector;

import Collector.io.*;
import Collector.logic.*;

public class CardBattle { public static void main(String[] args) { main(); }

    /**
     * The entry point of application.
     * Starts a card battle between a player and a computer enemy.
     */
    public static void main() {
        while (ScreenPrinter.gameLoop()) {
            Game.createGame().setupGame().playGame().finishGame();
        }
    }
}
