package Collector.models;

import Collector.abstracts.*;
import Collector.cards.*;

import java.util.*;
import java.util.concurrent.*;

public class CardArchive {

    public static List<AbstractCard> archive;
    public static Map<String, AbstractCard> archived;

    public static AbstractCard randomCard() {
        return archive == null ? null : archive.get(ThreadLocalRandom.current().nextInt(archive.size() - 1));
    }

    static {
        archive = new ArrayList<>();
        archived = new HashMap<>();

        GenericCard bassfist = new GenericCard("Bassfist", 6, 6);
        archive.add(bassfist);

        GenericCard zerk = new GenericCard("Zerk", 9, 2);
        archive.add(zerk);

        GenericCard shopun = new GenericCard("Shopun", 8, 8);
        archive.add(shopun);

        GenericCard fatbox = new GenericCard("Fatbox", 4, 15);
        archive.add(fatbox);

        GenericCard slicor = new GenericCard("Slicor", 7, 7);
        archive.add(slicor);

        GenericCard knocksplat = new GenericCard("Knocksplat", 2, 2);
        archive.add(knocksplat);

        GenericCard ratpack = new GenericCard("Ratpack", 3, 4);
        archive.add(ratpack);

        GenericCard illumnio = new GenericCard("Illumnio", 10, 0);
        archive.add(illumnio);

        GenericCard wallo = new GenericCard("Wall-O", 1, 9);
        archive.add(wallo);

        GenericCard sandguard = new GenericCard("Sandguard", 6, 11);
        archive.add(sandguard);

        GenericCard trickle = new GenericCard("Trickle", 5, 5);
        archive.add(trickle);

        GenericCard dragu = new GenericCard("Dragu", 11, 3);
        archive.add(dragu);

        GenericCard nutso = new GenericCard("Nutso", 14, 0);
        archive.add(nutso);

        GenericCard owltun = new GenericCard("Owltun", 3, 8);
        archive.add(owltun);

        GenericCard roararer = new GenericCard("Roararer", 7, 5);
        archive.add(roararer);

        GenericCard aplshield = new GenericCard("Aplshield", 0, 21);
        archive.add(aplshield);

        GenericCard magikal = new GenericCard("Magikal", 4, 3);
        archive.add(magikal);

        GenericCard warfighter = new GenericCard("Warfighter", 1, 1);
        archive.add(warfighter);

        GenericCard gamblok = new GenericCard("Gamblok", 5, 1);
        archive.add(gamblok);

        GenericCard mantike = new GenericCard("Mantike", 0, 9);
        archive.add(mantike);

        GenericCard reptoid = new GenericCard("Reptoid", 9, 1);
        archive.add(reptoid);

        GenericCard psyphold = new GenericCard("Psyphold", 6, 13);
        archive.add(psyphold);

        GenericCard rexloown = new GenericCard("Rexloown", 12, 12);
        archive.add(rexloown);

        GenericCard polyflill = new GenericCard("Polyflill", 5, 7);
        archive.add(polyflill);

        GenericCard quoxio = new GenericCard("Quoxio", 8, 11);
        archive.add(quoxio);

        GenericCard bayleet = new GenericCard("Bayleet", 4, 4);
        archive.add(bayleet);

        GenericCard fwilt = new GenericCard("Fwilt", 1, 6);
        archive.add(fwilt);

        GenericCard briff = new GenericCard("Briff", 8, 0);
        archive.add(briff);

        GenericCard millwheel = new GenericCard("Millwheel", 2, 2);
        archive.add(millwheel);

        GenericCard lessenbrast = new GenericCard("Lessenbrast", 7, 9);
        archive.add(lessenbrast);

        GenericCard ull = new GenericCard("Ull", 6, 4);
        archive.add(ull);

        GenericCard healbot = new GenericCard("Healbot", 0, 0);
        healbot.setText("When drawn, heal 10 HP.");
        healbot.setOnDraw(() -> {
            if (healbot.getOwner() != null) {
                healbot.getOwner().setCurrentHP(healbot.getOwner().getCurrentHP() + 10);
            }
        });
        archive.add(healbot);

        for (AbstractCard c : archive) {
            archived.put(c.getName(), c);
        }
    }
}
