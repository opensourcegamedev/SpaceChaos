package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Holds statistics about the current player performance.
 * <p>
 * This component is the basis for the score calculation.
 * 
 * @since 1.0.1-PreAlpha
 *
 */
public class StatComponent extends BaseComponent {

    private int enemyKills = 0;

    @Override
    protected void onInit(BaseGame game, Entity entity) {

    }

    public int getEnemyKills() {
        return enemyKills;
    }

    public void addEnemyKill() {
        this.enemyKills += 1;
    }
}
