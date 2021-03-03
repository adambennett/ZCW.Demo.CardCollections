package Collector.models;

import Collector.abstracts.*;

import java.util.*;

public class Deck implements Iterable<AbstractCard> {

    private final DeckContents contents;
    private final String name;
    private final Integer limitSize;
    private final Boolean isSizeLimited;

    public Deck(String name, Collection<AbstractCard> cards, int limitSize) {
        this.name = name;
        this.contents = new DeckContents(new ArrayList<>(cards), true);
        this.limitSize = limitSize;
        if (this.limitSize < 1) {
            this.isSizeLimited = false;
            return;
        }
        this.isSizeLimited = true;
        int size = this.contents.size() - this.limitSize;
        if (size > 0) {
            this.contents.removeCards(size);
        }
    }

    public List<AbstractCard> getList() { return this.contents.getList(); }

    public List<AbstractCard> draw(int amount) {
        return this.contents.draw(amount);
    }

    public void shuffle(List<AbstractCard> shuffleInto) {
        this.contents.shuffle(shuffleInto);
    }

    public AbstractCard fetch(AbstractCard target) {
        return this.contents.fetch(target);
    }

    public String getName() { return this.name; }

    public Integer size() { return this.contents.size(); }

    public Boolean add(AbstractCard card, boolean shuffle, boolean shuffleAll) {
        if (this.isSizeLimited && this.contents.size() + 1 > this.limitSize) return false;
        List<AbstractCard> cards = new ArrayList<>();
        cards.add(card);
        this.contents.add(cards, shuffle, shuffleAll);
        return true;
    }

    public Boolean addAll(Collection<AbstractCard> cards, boolean shuffle, boolean shuffleAll) {
        if (this.isSizeLimited && this.contents.size() + cards.size() > this.limitSize) return false;

        if (!this.isSizeLimited) {
            this.contents.add(new ArrayList<>(cards), shuffle, shuffleAll);
        } else {
            List<AbstractCard> cards2 = new ArrayList<>(cards);
            while (cards2.size() > 0 && this.contents.size() < this.limitSize) {
                List<AbstractCard> oneCardList = new ArrayList<>();
                oneCardList.add(cards2.remove(0));
                this.contents.add(oneCardList, shuffle, shuffleAll);
            }
        }
        return true;
    }

    public Boolean remove(AbstractCard card) {
        return this.contents.remove(card) != null;
    }

    @Override
    public Iterator<AbstractCard> iterator() {
        return this.contents.getList().iterator();
    }
}
