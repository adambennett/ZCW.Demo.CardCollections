package Collector.abstracts;

import java.util.*;

public abstract class AbstractCard {

    private final UUID id;
    protected final String name;
    protected Integer attack;
    protected Integer defend;

    public AbstractCard(String name, int atk, int def) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.attack = atk;
        this.defend = def;
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

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public void setDefend(Integer defend) {
        this.defend = defend;
    }

    // Hooks into game logic
    public void onDrawn() {}
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
        if (!(o instanceof AbstractCard)) return false;
        AbstractCard that = (AbstractCard) o;
        return getId().equals(that.getId()) && getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
