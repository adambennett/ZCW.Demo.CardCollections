package Collector.cards;

import Collector.abstracts.*;
import Collector.enums.*;
import Collector.models.*;

import java.util.concurrent.*;

public class Whennest extends CollectorCard {

    private static final String cardText = "50% chance to miss while attacking.";
    private Integer resetAttack;

    public Whennest() {
        super("Whennest", 30, 9);
        this.text = cardText;
    }

    @Override
    public void onPlay(AbstractCard enemyCard, CombatMove playerMove) {
        if (playerMove == CombatMove.ATTACK) {
            var roll = ThreadLocalRandom.current().nextBoolean();
            if (!roll) {
                this.resetAttack = this.getAttack();
                this.setAttack(0);
            }
        }
    }

    @Override
    public void afterCombat(AbstractCard enemyCard) {
        if (this.resetAttack != null) {
            this.setAttack(this.resetAttack);
            this.resetAttack = null;
        }
    }
}
