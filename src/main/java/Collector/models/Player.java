package Collector.models;

import Collector.abstracts.*;
import Collector.cards.*;
import Collector.interfaces.*;
import Collector.utilities.*;

import java.util.*;

public class Player {

    private Integer maxHP;
    private Integer currentHP;
    private Integer attackBonus;
    private Integer defendBonus;
    private Integer permAttackBonus;
    private Integer permDefendBonus;
    private String name;
    private Deck deck;
    private final List<AbstractCard> discard;
    private final List<AbstractCard> exhaust;
    private AbstractCard currentCard;

    public Player(String name, int maxHP, Integer numCards) {
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attackBonus = 0;
        this.defendBonus = 0;
        this.permAttackBonus = 0;
        this.permDefendBonus = 0;
        this.discard = new ArrayList<>();
        this.exhaust = new ArrayList<>();
        if (numCards != null) {
            this.deck = new Deck(name + "'s Deck", this.setupDeck(numCards), -1);
        }
    }

    public Player(String name, int maxHP, Collection<AbstractCard> cards) {
        this(name, maxHP, (Integer) null);
        var actual = new ArrayList<AbstractCard>();
        for (AbstractCard c : cards) {
            var copy = c.copy();
            copy.setOwner(this);
            copy.onAddedToDeck();
            actual.add(copy);
        }
        this.deck = new Deck(name + "'s Deck", actual, -1);
    }


    /**
     * Create a random deck for a player based on just a given number of cards.
     * Filters out Unique cards such that only 1 copy at max appears in any deck.
     *
     * @param numberOfCards the number of cards the deck should have after creation
     * @return the deck list
     */
    private ArrayList<AbstractCard> setupDeck(int numberOfCards) {
        var actual = new ArrayList<AbstractCard>();
        var added = new HashMap<String, AbstractCard>();
        while (actual.size() < numberOfCards) {
            var copy = CardArchive.randomCard();
            var allowed = copy != null && (!((copy instanceof Unique) && added.containsKey(copy.getName())));
            if (allowed) {
                copy.setOwner(this);
                copy.onAddedToDeck();
                actual.add(copy);
                added.put(copy.getName(), copy);
            } else if (copy == null) {
                return actual;
            }
        }
        return actual;
    }

    /**
     * Draw card(s) from the deck and return the list.
     *
     * @param amt the number of cards to draw
     * @return the list of cards drawn from the deck
     */
    public List<AbstractCard> draw(int amt, Player enemy) {
        this.attackBonus = 0;
        this.defendBonus = 0;
        if (this.deck.size() < 2) {
            for (var c : this.discard) {
                c.onShuffleIntoDeck();
            }
            this.deck.shuffle(this.discard);
            this.discard.clear();
        }
        var cards = this.deck.draw(amt);
        for (var card : cards) {
            card.onDrawn(enemy);
            if (card.isExhaust()) {
                this.exhaust.add(card);
            } else {
                this.discard.add(card);
            }
        }
        return cards;
    }

    /**
     * Return a fresh instance of this player object with a different hash code but all the same values.
     *
     * @return The new copy of this player.
     */
    public Player copy(){
        var out = new Player(this.name, this.maxHP, this.deck.getList());
        out.setCurrentHP(this.currentHP);
        out.setCurrentCard(this.currentCard);
        return out;
    }

    /**
     * Calculate the full damage of the player's card, using temporary and permanent player bonuses.
     *
     * @return The full ATK value of the player's card + all bonuses.
     */
    public Integer getFullDamage() {
        var dmg = this.getCurrentCard().getAttack() + this.getAttackBonus() + this.getPermAttackBonus();

        if (this.getCurrentCard() instanceof Vicious) {
            dmg += Constants.ViciousDamageBonus;
        }

        return dmg;
    }

    /**
     * Calculate the full defense of the player's card, using temporary and permanent player bonuses.
     *
     * @return The full DEF value of the player's card + all bonuses.
     */
    public Integer getFullDefense() {
        return this.getCurrentCard().getDefend() + this.getDefendBonus() + this.getPermDefendBonus();
    }

    /**
     * Get the ATK value to display in the draw summary.
     *
     * @return The current ATK value of the player's card, if available to show
     */
    public String displayAttack() {
        var base = this.getCurrentCard().getAttack();
        if (base < 0) {
            return Constants.UnknownValue;
        }
        var total = base + this.getAttackBonus() + this.getPermAttackBonus();
        return total != base ? "" + total + " (" + base + ")" : "" + base;
    }

    /**
     * Get the DEF value to display in the draw summary.
     *
     * @return The current DEF value of the player's card, if available to show
     */
    public String displayDefense() {
        var checkSpecial = this.getCurrentCard().getTrueDefend().isPresent();
        if (!checkSpecial) return Constants.UnknownValue;

        var base = this.getCurrentCard().getDefend();
        var total = base + this.getDefendBonus() + this.getPermDefendBonus();
        return total != base ? "" + total + " (" + base + ")" : "" + base;
    }

    /**
     * Quick method to return the name of the player's card.
     *
     * @return The card name
     */
    public String displayCard() {
        return this.getCurrentCard().getName();
    }

    /**
     * Deals damage to this player.
     *
     * @param dmg The amount of damage to deal.
     */
    public void damage(int dmg) {
        this.setCurrentHP(this.currentHP - dmg);
    }

    /**
     * Heals this player.
     *
     * @param heal The amount of HP to heal.
     */
    public void heal(int heal) {
        this.setCurrentHP(this.currentHP + heal);
    }

    /**
     * Increase this player's maximum HP.
     *
     * @param amt The amount of HP to add.
     */
    public void increaseMaxHP(int amt) {
        this.maxHP += amt;
        System.out.println("\n" + this.getName() + " gained " + amt + " extra HP!");
    }

    /**
     * Give this player a temporary attack bonus. Lasts 1 round.
     *
     * @param amt The amount of attack bonus to add to the player's existing attack bonus.
     */
    public void increaseAttackBonus(int amt) {
        this.attackBonus += amt;
    }

    /**
     * Give this player a temporary defense bonus. Lasts 1 round.
     *
     * @param amt The amount of defense bonus to add to the player's existing defense bonus.
     */
    public void increaseDefenseBonus(int amt) {
        this.defendBonus += amt;
    }

    /**
     * Give this player a permanent attack bonus.
     *
     * @param amt The amount of attack bonus to add to the player's existing attack bonus.
     */
    public void increasePermAttackBonus(int amt) {
        this.permAttackBonus += amt;
    }

    /**
     * Give this player a permanent defense bonus.
     *
     * @param amt The amount of defense bonus to add to the player's existing defense bonus.
     */
    public void increasePermDefenseBonus(int amt) {
        this.permDefendBonus += amt;
    }

    /**
     * Modifies the player's current HP value. Cannot set below HP below 1.
     *
     * @param currentHP The amount of HP to set the player's current HP to.
     */
    public void setCurrentHP(Integer currentHP) {
        this.currentHP = currentHP;
        if (this.currentHP > this.maxHP) {
            this.currentHP = this.maxHP;
        }
    }

    // Plain getters & setters
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
    public Integer getPermAttackBonus() {
        return permAttackBonus;
    }
    public Integer getPermDefendBonus() {
        return permDefendBonus;
    }
    public void setCurrentCard(AbstractCard currentCard) { this.currentCard = currentCard; }
    public void setMaxHP(Integer maxHP) { this.maxHP = maxHP; }
    public void setName(String name) { this.name = name; }
    public void setDeck(Deck deck) { this.deck = deck; }
}
