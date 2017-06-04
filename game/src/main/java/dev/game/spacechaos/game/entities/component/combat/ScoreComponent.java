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
public class ScoreComponent extends BaseComponent {

    private int score = 0;

    @Override
    protected void onInit(BaseGame game, Entity entity) {

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public void addScore(int score) {
        this.score += score;
    }

}
