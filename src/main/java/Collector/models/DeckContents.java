package Collector.models;

import Collector.abstracts.*;
import java.util.*;
import java.util.concurrent.*;

public class DeckContents {

    private Integer deckSize;
    private final List<AbstractCard> orderedCards;
    private final Map<AbstractCard, Integer> cardAmounts;

    public DeckContents(List<AbstractCard> cards, boolean shuffle) {
        this.orderedCards = shuffle ? new ArrayList<>() : new ArrayList<>(cards);
        this.cardAmounts = new HashMap<>();
        this.deckSize = 0;
        for (AbstractCard card : cards) {
            this.cardAmounts.merge(card, 1, (k, v) -> v + 1);
            this.deckSize++;
        }
        if (shuffle) {
            while (cards.size() > 0) {
                if (cards.size() > 1) {
                    this.orderedCards.add(cards.remove(ThreadLocalRandom.current().nextInt(cards.size() - 1)));
                } else {
                    this.orderedCards.add(cards.remove(0));
                }
            }
        }
    }

    public List<AbstractCard> getList() { return this.orderedCards; }

    public Integer size() {
        return deckSize;
    }

    public List<AbstractCard> draw(int amount) {
        List<AbstractCard> out = new ArrayList<>();
        while (amount > 0 && this.orderedCards.size() > 0) {
            AbstractCard drawn = remove(null);
            if (drawn == null) {
                return out;
            }
            out.add(drawn);
            amount--;
        }
        return out;
    }

    public AbstractCard fetch(AbstractCard target) {
        return remove(target);
    }

    public void add(List<AbstractCard> cards, boolean shuffle, boolean shuffleAll) {
        if (shuffle) {
            while (cards.size() > 0) {
                this.orderedCards.add(cards.remove(ThreadLocalRandom.current().nextInt(cards.size() - 1)));
            }
        } else {
            this.orderedCards.addAll(cards);
        }

        this.deckSize += cards.size();
        for (AbstractCard card : cards) {
            this.cardAmounts.merge(card, 1, (k, v) -> v + 1);
            this.deckSize++;
        }

        if (shuffleAll) {
            List<AbstractCard> newOrder = new ArrayList<>();
            while (this.orderedCards.size() > 0) {
                newOrder.add(orderedCards.remove(ThreadLocalRandom.current().nextInt(orderedCards.size() - 1)));
            }
            this.orderedCards.addAll(newOrder);
        }
    }

    public List<AbstractCard> removeCards(int amount) {
        List<AbstractCard> removedCards = new ArrayList<>();
        while (amount > 0) {
            var removed = remove(null);
            if (removed == null) {
                return removedCards;
            }
            removedCards.add(removed);
            amount--;
        }
        return removedCards;
    }

    public Integer randomIndex() {
        if (this.deckSize == 0 || this.orderedCards.size() < 1) return -1;
        return ThreadLocalRandom.current().nextInt(this.orderedCards.size() - 1);
    }

    public AbstractCard remove(AbstractCard toRemove) {
        if (this.deckSize == 0 || this.orderedCards.size() < 1) return null;

        if (toRemove == null) {
            toRemove = this.orderedCards.get(randomIndex());
        }

        boolean removed = this.orderedCards.remove(toRemove);
        if (!removed) {
            return null;
        }

        this.deckSize--;
        if (this.cardAmounts.containsKey(toRemove)) {
            this.cardAmounts.put(toRemove, this.cardAmounts.get(toRemove) - 1);
        }
        return toRemove;
    }
}
