package Collector.cards;

import Collector.abstracts.*;
import Collector.models.*;

public class Sprokked extends CollectorCard {

    private static final String cardText = "After combat, lose !M! HP.";
    private static final Integer baseMagic = 10;

    public Sprokked() {
        super("Sprokked", 19, 5);
        this.text = cardText;
        this.magic = baseMagic;
    }

    public Sprokked(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
        this.magic = baseMagic;
    }

    @Override
    public void afterCombat(AbstractCard enemyCard) {
        this.getOwner().damage(this.getMagic());
    }

    @Override
    public Sprokked copy() {
        return new Sprokked(this.getName(), this.getAttack(), this.getDefend());
    }
}
