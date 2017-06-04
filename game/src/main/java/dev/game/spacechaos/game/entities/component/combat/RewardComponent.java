package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.game.entity.listener.HPDeathListener;

/**
 * Gives points when the entity gets killed.
 * <p>
 * Requires a {@linkplain HPComponent health component}.
 * 
 * @since 1.0.1-PreAlpha
 *
 */
public class RewardComponent extends BaseComponent implements HPDeathListener {

    private int score = 0;

    public RewardComponent(int score) {
        setScore(score);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        HPComponent hpComponent = entity.getComponent(HPComponent.class);

        if (hpComponent == null) {
            throw new IllegalStateException("entity doesn't have an HPComponent.");
        }

        hpComponent.addDeathListener(this);
    }

    @Override
    public void onDeath(Entity causingEntity) {
        ScoreComponent scoreComp = causingEntity.getComponent(ScoreComponent.class);
        if (scoreComp != null) {
            scoreComp.addScore(score);
        }

    }

}
