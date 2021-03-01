package Collector.cards;

import Collector.abstracts.*;
import Collector.interfaces.*;

public class GenericCard extends AbstractCard {

    private BlankHook onDraw;
    private UnaryCardHook onPly;
    private UnaryCardHook onAtk;
    private UnaryCardHook onDef;
    private UnaryCardHook onStale;
    private UnaryCardHook onComb;
    private UnaryCardHook onVicious;
    private EnemyHealHook onEnemyHeal;

    public GenericCard(String name, int atk, int def) {
        super(name, atk, def);
    }

    public GenericCard copy() {
        GenericCard out = new GenericCard(this.getName(), this.getAttack(), this.getDefend());
        out.setOnDraw(this.onDraw);
        out.setOnPlay(this.onPly);
        out.setOnAtk(this.onAtk);
        out.setOnDef(this.onDef);
        out.setOnStalemate(this.onStale);
        out.setOnCombat(this.onComb);
        out.setOnViciousCombat(this.onVicious);
        out.setOnEnemyHeal(this.onEnemyHeal);
        return out;
    }

    @Override
    public void onDrawn() {
        if (this.onDraw != null) {
            this.onDraw.hook();
        }
    }

    @Override
    public void onPlay(AbstractCard enemyCard) {
        if (this.onPly != null) {
            this.onPly.hook(enemyCard);
        }
    }

    @Override
    public void onAttack(AbstractCard enemyCard) {
        if (this.onAtk != null) {
            this.onAtk.hook(enemyCard);
        }
    }

    @Override
    public void onDefend(AbstractCard enemyCard) {
        if (this.onDef != null) {
            this.onDef.hook(enemyCard);
        }
    }

    @Override
    public void onStalemate(AbstractCard enemyCard) {
        if (this.onStale != null) {
            this.onStale.hook(enemyCard);
        }
    }

    @Override
    public void onCombat(AbstractCard enemyCard) {
        if (this.onComb != null) {
            this.onComb.hook(enemyCard);
        }
    }

    @Override
    public void onViciousCombat(AbstractCard enemyCard) {
        if (this.onVicious != null) {
            this.onVicious.hook(enemyCard);
        }
    }

    @Override
    public void onEnemyHeal(AbstractCard enemyCard, int healedFor, int hpAfterHealing) {
        if (this.onEnemyHeal != null) {
            this.onEnemyHeal.enemyHeal(enemyCard, healedFor, hpAfterHealing);
        }
    }

    public void setOnDraw(BlankHook onDraw) {
        this.onDraw = onDraw;
    }

    public void setOnPlay(UnaryCardHook onPly) {
        this.onPly = onPly;
    }

    public void setOnAtk(UnaryCardHook onAtk) {
        this.onAtk = onAtk;
    }

    public void setOnDef(UnaryCardHook onDef) {
        this.onDef = onDef;
    }

    public void setOnStalemate(UnaryCardHook onStale) {
        this.onStale = onStale;
    }

    public void setOnCombat(UnaryCardHook onComb) {
        this.onComb = onComb;
    }

    public void setOnViciousCombat(UnaryCardHook onVicious) {
        this.onVicious = onVicious;
    }

    public void setOnEnemyHeal(EnemyHealHook onEnemyHeal) {
        this.onEnemyHeal = onEnemyHeal;
    }
}
