package Collector.models;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.core.util.*;

import java.io.*;
import java.util.*;
import Collector.utilities.*;
import com.fasterxml.jackson.databind.*;

public class PlayerArchive {

    private static final List<Player> archive;
    private static final Map<String, Player> archived;
    private static final String path;

    public static Boolean save() {
        var mapper = new ObjectMapper();
        var writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            var serializeList = new ArrayList<SerializablePlayer>();
            for (var p : archive) {
                serializeList.add(new SerializablePlayer(p));
            }
            writer.writeValue(new File(path), serializeList);
        } catch (Exception ex) {
            System.out.println("Error saving progress! Error: " + ex);
            return false;
        }
        return true;
    }

    public static void load() {
        var mapper = new ObjectMapper();
        try {
            var loaded = mapper.readValue(new File(path), new TypeReference<ArrayList<SerializablePlayer>>(){});
            for (var p : loaded) {
                archive.add(p.rebuild());
            }
        } catch (Exception ex) {
            try {
                File yourFile = new File(path);
                yourFile.createNewFile();
            } catch (Exception ex2) {
                System.out.println("Error loading progress! Error: " + ex2);
            }
        }
        for (var c : archive) {
            archived.put(c.getName(), c);
        }
    }

    public static Player get(String playerName) {
        return archived.containsKey(playerName) ? archived.get(playerName).copy() : null;
    }

    public static Boolean modifyPlayer(Player newPlayerData, String originalName) {
        if (!archived.containsKey(newPlayerData.getName())) return false;
        var player = remove(originalName);
        if (player == null) return false;
        return add(newPlayerData);
    }

    public static Player remove(String playerName) {
        var player = archived.getOrDefault(playerName, null);
        if (player == null) return null;
        var out = archived.remove(playerName);
        archive.removeIf((pl) -> pl.getName().equals(playerName));
        return out;
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
        path = FileSystem.isWindows()
                ? FileSystem.getResourcesPath() + "progress.json"
                : "/Users/CardCollector/progress/progress.json";
        load();
    }
}
