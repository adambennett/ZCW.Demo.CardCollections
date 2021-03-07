package Collector.models;

import Collector.abstracts.*;

import java.util.*;

public class SerializablePlayer {

    public String name;
    public Integer hp;
    public Map<String, Integer> cards;

    public SerializablePlayer() {}

    public SerializablePlayer(Player serialize) {
        this.name = serialize.getName();
        this.hp = serialize.getMaxHP();
        this.cards = new HashMap<>();
        for (var c : serialize.getDeck().getList()) {
            if (this.cards.containsKey(c.getName())) {
                this.cards.put(c.getName(), this.cards.get(c.getName()) + 1);
            } else {
                this.cards.put(c.getName(), 1);
            }
        }
    }

    public Player rebuild() {
        var player = new Player(this.name, this.hp, (Integer) null);
        var cards = new ArrayList<AbstractCard>();
        for (var entry : this.cards.entrySet()) {
            var card = CardArchive.get(entry.getKey());
            if (card == null) continue;

            for (var i = 0; i < entry.getValue(); i++) {
               cards.add(card.copy());
            }
        }
        player.setDeck(new Deck(this.name + "'s Deck", cards, -1));
        return player;
    }
}
