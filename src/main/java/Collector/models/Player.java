package Collector.models;

import Collector.abstracts.*;

import java.util.*;

public class Player {

    private Integer maxHP;
    private Integer currentHP;
    private final String name;
    private final Deck deck;
    private AbstractCard currentCard;

    public Player(String name, String deckName, int maxHP, Collection<AbstractCard> cards) {
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.deck = new Deck(deckName, cards, -1);
    }

    public String getName() {
        return name;
    }

    public Integer getMaxHP() {
        return maxHP;
    }

    public Integer getCurrentHP() {
        return currentHP;
    }

    public Deck getDeck() {
        return deck;
    }

    public AbstractCard getCurrentCard() {
        return currentCard;
    }

    public void setMaxHP(Integer maxHP) {
        this.maxHP = maxHP;
    }

    public void setCurrentHP(Integer currentHP) {
        this.currentHP = currentHP;
        if (this.currentHP > this.maxHP) {
            this.currentHP = this.maxHP;
        }
    }

    public void setCurrentCard(AbstractCard currentCard) {
        this.currentCard = currentCard;
    }
}
