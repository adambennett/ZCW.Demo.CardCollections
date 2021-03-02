package Collector.models;

import Collector.abstracts.*;

import java.util.*;

public class Player {

    private Integer maxHP;
    private Integer currentHP;
    private Integer attackBonus;
    private Integer defendBonus;
    private final String name;
    private final Deck deck;
    private AbstractCard currentCard;

    public Player(String name, String deckName, int maxHP, Collection<AbstractCard> cards) {
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attackBonus = 0;
        this.defendBonus = 0;
        for (AbstractCard c : cards) {
            c.setOwner(this);
        }
        this.deck = new Deck(deckName, cards, -1);
    }

    public List<AbstractCard> draw(int amt) {
        this.attackBonus = 0;
        this.defendBonus = 0;
        return this.deck.draw(amt);
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

    public Integer getAttackBonus() {
        return attackBonus;
    }

    public Integer getDefendBonus() {
        return defendBonus;
    }

    public void setAttackBonus(Integer attackBonus) {
        this.attackBonus = attackBonus;
    }

    public void setDefendBonus(Integer defendBonus) {
        this.defendBonus = defendBonus;
    }

    public void damage(int dmg) {
        this.setCurrentHP(this.currentHP - dmg);
    }

    public void heal(int heal) {
        this.setCurrentHP(this.currentHP + heal);
    }

    public void increaseMaxHP(int amt) {
        this.maxHP += amt;
    }

    public void decreaseMaxHP(int amt) {
        this.maxHP -= amt;
        if (this.maxHP < 1) {
            this.maxHP = 1;
        }
        this.setCurrentHP(this.currentHP);
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
