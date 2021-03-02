package Collector.abstracts;

import Collector.cards.*;
import Collector.models.*;

import java.util.*;

public abstract class AbstractCard {

    private final UUID id;
    protected final String name;
    protected String text;
    protected Integer attack;
    protected Integer defend;
    protected Integer magic;
    protected Player owner;
    protected Boolean isExhaust;

    public AbstractCard(String name, int atk, int def) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.attack = atk;
        this.defend = def;
        this.magic = 0;
        this.text = "";
        this.isExhaust = false;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUniqueName() {
        return id + ":" + name;
    }

    public Integer getAttack() {
        return attack;
    }

    public Integer getDefend() {
        return defend;
    }

    public String getText() {
        if (text == null) return "";
        return text.replaceAll("!M!", "" + this.getMagic())
                .replaceAll("!A!", "" + this.getAttack())
                .replaceAll("!D!", "" + this.getDefend());
    }

    public Player getOwner() {
        return owner;
    }

    public Integer getMagic() {
        return magic;
    }

    public Boolean isExhaust() {
        return isExhaust;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public void setDefend(Integer defend) {
        this.defend = defend;
    }

    public void setText(String text) { this.text = text; }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setMagic(Integer magic) {
        this.magic = magic;
    }

    public void setExhaust(Boolean exhaust) {
        isExhaust = exhaust;
    }

    // Hooks into game logic
    public void onDrawn() {}
    public void afterDrawn() {}
    public void onPlay(AbstractCard enemyCard) {}
    public void onAttack(AbstractCard enemyCard) {}
    public void onDefend(AbstractCard enemyCard) {}
    public void onStalemate(AbstractCard enemyCard) {}
    public void onCombat(AbstractCard enemyCard) {}
    public void onViciousCombat(AbstractCard enemyCard) {}
    public void onEnemyHeal(AbstractCard enemyCard, int healedFor, int hpAfterHealing) {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCard that = (AbstractCard) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    public abstract AbstractCard copy();
}
