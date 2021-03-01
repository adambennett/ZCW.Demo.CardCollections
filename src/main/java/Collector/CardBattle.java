package Collector;

import Collector.io.*;
import Collector.logic.*;

public class CardBattle {


    /**
     * The entry point of application. Starts a card battle between a player and a computer enemy.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        while (ScreenPrinter.gameLoop()) {
            Game.createGame().setupGame().playGame().finishGame();
        }
    }
}
