package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Created by Jo on 03.06.2017.
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

    public void addToScore(int score) {
        this.score += score;
    }
}
