package Collector.cards;

import Collector.abstracts.*;
import Collector.enums.*;
import Collector.models.*;

public class Ventora extends CollectorCard {

    private static final String cardText = "If you attack with this card, set your HP to half of your maximum HP.";

    public Ventora() {
        super("Ventora", 22, 7);
        this.text = cardText;
    }

    public Ventora(String name, int atk, int def) {
        super(name, atk, def);
        this.text = cardText;
    }

    @Override
    public void onPlay(AbstractCard enemyCard, CombatMove playerMove) {
        if (playerMove == CombatMove.ATTACK) {
            this.getOwner().setCurrentHP(this.getOwner().getMaxHP() / 2);
        }
    }

    @Override
    public Ventora copy() {
        return new Ventora(this.getName(), this.getAttack(), this.getDefend());
    }
}
