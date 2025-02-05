package Collector.models;

import Collector.abstracts.*;
import java.util.*;
import java.util.concurrent.*;

public class DeckContents {

    private Integer deckSize;
    private final List<AbstractCard> orderedCards;
    private final Map<String, Integer> cardAmounts;

    public DeckContents(List<AbstractCard> cards, boolean shuffle) {
        this.orderedCards = shuffle ? new ArrayList<>() : new ArrayList<>(cards);
        this.cardAmounts = new HashMap<>();
        this.deckSize = 0;
        for (AbstractCard card : cards) {
            if (this.cardAmounts.containsKey(card.getName())) {
                this.cardAmounts.put(card.getName(), this.cardAmounts.get(card.getName()) + 1);
            } else {
                this.cardAmounts.put(card.getName(), 1);
            }
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

    public Boolean isInDeck(String cardName) {
        return this.cardAmounts != null && this.cardAmounts.containsKey(cardName);
    }

    public Boolean isInDeck(AbstractCard card) {
        return isInDeck(card.getName());
    }

    public AbstractCard fetch(AbstractCard target) {
        return remove(target);
    }

    public void add(List<AbstractCard> cards, boolean shuffle, boolean shuffleAll) {
        this.deckSize += cards.size();
        if (shuffle) {
            while (cards.size() > 0) {
                if (cards.size() == 1) {
                    this.orderedCards.add(cards.remove(0));
                    continue;
                }
                this.orderedCards.add(cards.remove(ThreadLocalRandom.current().nextInt(cards.size() - 1)));
            }
        } else {
            this.orderedCards.addAll(cards);
        }

        for (AbstractCard card : cards) {
            if (this.cardAmounts.containsKey(card.getName())) {
                this.cardAmounts.put(card.getName(), this.cardAmounts.get(card.getName()) + 1);
            } else {
                this.cardAmounts.put(card.getName(), 1);
            }
        }

        if (shuffleAll) {
            List<AbstractCard> newOrder = new ArrayList<>();
            while (this.orderedCards.size() > 0) {
                if (this.orderedCards.size() == 1) {
                    newOrder.add(orderedCards.remove(0));
                    continue;
                }
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
        if (this.deckSize == 1 || this.orderedCards.size() == 1) return 0;
        return ThreadLocalRandom.current().nextInt(this.orderedCards.size() - 1);
    }

    public void shuffle(List<AbstractCard> shuffleInto) {
        var out = new ArrayList<AbstractCard>();
        var shuffle = new ArrayList<>(this.orderedCards);
        this.deckSize = 0;
        shuffle.addAll(shuffleInto);
        while (shuffle.size() > 0) {
            if (shuffle.size() == 1) {
                out.add(shuffle.remove(0));
                deckSize++;
                continue;
            }
            out.add(shuffle.remove(ThreadLocalRandom.current().nextInt(shuffle.size() - 1)));
            deckSize++;
        }
        this.orderedCards.clear();
        this.cardAmounts.clear();
        this.orderedCards.addAll(out);
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
        if (this.cardAmounts.containsKey(toRemove.getName())) {
            this.cardAmounts.put(toRemove.getName(), this.cardAmounts.get(toRemove.getName()) - 1);
        }
        return toRemove;
    }
}
