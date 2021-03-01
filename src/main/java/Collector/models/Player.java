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
        for (AbstractCard c : cards) {
            c.setOwner(this);
        }
        this.deck = new Deck(deckName, cards, -1);
    }

    public Player copy(){
        var out = new Player(this.name, this.deck.getName(), this.maxHP, this.deck.getList());
        out.setCurrentHP(this.currentHP);
        out.setCurrentCard(this.currentCard);
        return out;
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

    public void damage(int dmg) {
        this.setCurrentHP(this.currentHP - dmg);
    }

    public void heal(int heal) {
        this.setCurrentHP(this.currentHP + heal);
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
