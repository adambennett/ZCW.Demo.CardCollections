package Collector.models;

import java.util.*;
import java.util.concurrent.*;

public class PlayerArchive {

    private static final List<Player> archive;
    private static final Map<String, Player> archived;

    public static Player randomPlayer() {
        return archive == null ? null : archive.get(ThreadLocalRandom.current().nextInt(archive.size() - 1)).copy();
    }

    public static Player get(String playerName) {
        return archived.containsKey(playerName) ? archived.get(playerName).copy() : null;
    }

    public static List<Player> list() {
        return archive;
    }

    public static Boolean add(Player player) {
        if (archived.containsKey(player.getName())) {
            return false;
        }
        archive.add(player);
        archived.put(player.getName(), player);
        return true;
    }

    public static Player get(Player player) {
        return archived.getOrDefault(player.getName(), null);
    }

    static {
        archive = new ArrayList<>();
        archived = new HashMap<>();

        // load players from file or database

        for (var c : archive) {
            archived.put(c.getName(), c);
        }
    }
}
