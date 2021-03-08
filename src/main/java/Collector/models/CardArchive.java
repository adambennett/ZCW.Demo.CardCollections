package Collector.models;

import Collector.abstracts.*;
import Collector.cards.*;
import Collector.cards.curses.*;
import Collector.io.*;
import io.bretty.console.table.*;

import java.util.*;
import java.util.concurrent.*;

public class CardArchive {

    private static final List<Curse> curses;
    private static final List<AbstractCard> archive;
    private static final Map<String, AbstractCard> archived;
    private static final Map<String, Curse> curseArchive;

    public static AbstractCard randomCard() {
        return archive == null || archive.size() < 1 ? null : archive.get(ThreadLocalRandom.current().nextInt(archive.size() - 1)).copy();
    }

    public static Curse randomCurse() {
        return curses == null || curses.size() < 1 ? null : curses.get(ThreadLocalRandom.current().nextInt(curses.size() - 1)).copy();
    }

    public static AbstractCard get(String card) {
        return archived.containsKey(card) ? archived.get(card).copy() : null;
    }

    public static Curse getCurse(String curse) {
        return curseArchive.containsKey(curse) ? curseArchive.get(curse).copy() : null;
    }

    public static AbstractCard get(AbstractCard card) {
        return archived.getOrDefault(card.getName(), null);
    }

    public static String printArchive() {

        var cardWidth = 25;
        var atkWidth = 11;
        var defWidth = 11;
        var textWidth = 125;

        ColumnFormatter<String> cardFormatter = ColumnFormatter.text(Alignment.CENTER, cardWidth);
        ColumnFormatter<String> atkFormatter = ColumnFormatter.text(Alignment.CENTER, atkWidth);
        ColumnFormatter<String> defFormatter = ColumnFormatter.text(Alignment.CENTER, defWidth);
        ColumnFormatter<String> textFormatter = ColumnFormatter.text(Alignment.LEFT, textWidth);

        var cardsList = new ArrayList<String>();
        var atkList = new ArrayList<String>();
        var defList = new ArrayList<String>();
        var textList = new ArrayList<String>();

        cardsList.add(ScreenPrinter.lineBreak(cardWidth));
        atkList.add(ScreenPrinter.lineBreak(atkWidth));
        defList.add(ScreenPrinter.lineBreak(defWidth));
        textList.add(ScreenPrinter.lineBreak(textWidth));

        var sorted = new ArrayList<>(archive);
        sorted.sort(Comparator.comparing(AbstractCard::getName));
        for (var c : sorted) {
            cardsList.add(c.getName());
            textList.add(MenuHandler.descSpacer() + c.getText());
            if (c instanceof Spimoky) {
                atkList.add("??");
                defList.add("??");
            } else {
                var atk = c.getAttack() < 10 && c.getAttack() > -1 ? "0" + c.getAttack() : "" + c.getAttack();
                var def = c.getDefend() < 10 && c.getDefend() > -1 ? "0" + c.getDefend() : "" + c.getDefend();
                atkList.add(atk);
                defList.add(def);
            }
            cardsList.add(ScreenPrinter.lineBreak(cardWidth));
            atkList.add(ScreenPrinter.lineBreak(atkWidth));
            defList.add(ScreenPrinter.lineBreak(defWidth));
            textList.add(ScreenPrinter.lineBreak(textWidth));
        }

        String[] cards = cardsList.toArray(new String[0]);
        String[] atk = atkList.toArray(new String[0]);
        String[] def = defList.toArray(new String[0]);
        String[] text = textList.toArray(new String[0]);

        var builder = new Table.Builder("Card", cards, cardFormatter);

        builder.addColumn("ATK", atk, atkFormatter);
        builder.addColumn("DEF", def, defFormatter);
        builder.addColumn("Text", text, textFormatter);

        return "" + builder.build();
    }

    static {
        archive = new ArrayList<>();
        curses = new ArrayList<>();
        archived = new HashMap<>();
        curseArchive = new HashMap<>();

        archive.add(new GenericCard("A-Bot", 6, 14));
        archive.add(new GenericCard("Aplshield", 0, 21));
        archive.add(new GenericCard("Bassfist", 6, 6));
        archive.add(new GenericCard("Bayleet", 4, 4));
        archive.add(new GenericCard("Briff", 8, 0));
        archive.add(new GenericCard("Cendivious", 16, 16));
        archive.add(new GenericCard("Crezz", 9, 14));
        archive.add(new GenericCard("Dragu", 11, 3));
        archive.add(new GenericCard("Fatbox", 4, 15));
        archive.add(new GenericCard("Fwilt", 1, 6));
        archive.add(new GenericCard("Gamblok", 5, 1));
        archive.add(new GenericCard("Huglena", 9, 19));
        archive.add(new GenericCard("Illumnio", 10, 0));
        archive.add(new GenericCard("Kiwew", 2, 1));
        archive.add(new GenericCard("Knocksplat", 2, 2));
        archive.add(new GenericCard("Lessenbrast", 7, 9));
        archive.add(new GenericCard("Magikal", 4, 3));
        archive.add(new GenericCard("Mantike", 0, 9));
        archive.add(new GenericCard("Millwheel", 2, 2));
        archive.add(new GenericCard("Nutso", 14, 0));
        archive.add(new GenericCard("Ochozor", 8, 8));
        archive.add(new GenericCard("Owltun", 3, 8));
        archive.add(new GenericCard("Polyflill", 5, 7));
        archive.add(new GenericCard("Psyphold", 6, 13));
        archive.add(new GenericCard("Q-Bot", 3, 14));
        archive.add(new GenericCard("Quoxio", 8, 11));
        archive.add(new GenericCard("Ratpack", 3, 4));
        archive.add(new GenericCard("Reptoid", 9, 1));
        archive.add(new GenericCard("Rexloown", 12, 12));
        archive.add(new GenericCard("Roararer", 7, 5));
        archive.add(new GenericCard("Sandguard", 6, 11));
        archive.add(new GenericCard("Shopun", 8, 8));
        archive.add(new GenericCard("Slicor", 7, 7));
        archive.add(new GenericCard("Trickle", 5, 5));
        archive.add(new GenericCard("Ull", 6, 4));
        archive.add(new GenericCard("Vegitowa", 7, 5));
        archive.add(new GenericCard("Wall-O", 1, 9));
        archive.add(new GenericCard("Warfighter", 1, 1));
        archive.add(new GenericCard("Wedvar", 14, 2));
        archive.add(new GenericCard("Weikylvik", 5, 18));
        archive.add(new GenericCard("Wikke", 1, 2));
        archive.add(new GenericCard("X-Bot", 16, 4));
        archive.add(new GenericCard("Y-Bot", 14, 13));
        archive.add(new GenericCard("Zenos", 0, 17));
        archive.add(new GenericCard("Zerk", 9, 2));

        archive.add(new Bufftoss());
        archive.add(new Builto());
        archive.add(new Chilzone());
        archive.add(new Ejo());
        archive.add(new Halfrot());
        archive.add(new Healbot());
        archive.add(new Hewslet());
        archive.add(new Jeqlew());
        archive.add(new Keflew());
        archive.add(new Koyjhet());
        archive.add(new Nyoxide());
        archive.add(new Packhouse());
        archive.add(new Palasor());
        archive.add(new Powrel());
        archive.add(new Quilx());
        archive.add(new Renvar());
        archive.add(new Rhearst());
        archive.add(new Seplew());
        archive.add(new Spimoky());
        archive.add(new Sprokked());
        archive.add(new Tokan());
        archive.add(new Ventora());
        archive.add(new Whennest());
        archive.add(new Xiloowt());
        archive.add(new Yasdarv());

        curses.add(new Nightmare());
        curses.add(new Malice());

        for (var c : archive) {
            archived.put(c.getName(), c);
        }

        for (var c : curses) {
            curseArchive.put(c.getName(), c);
        }
    }
}
