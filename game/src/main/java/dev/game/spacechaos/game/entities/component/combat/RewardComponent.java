package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.annotation.InjectComponent;
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
    @InjectComponent(nullable = false)
    private HPComponent hpComponent = null;

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
        hpComponent.addDeathListener(this);
    }

    @Override
    public void onDeath(Entity causingEntity) {
        if (causingEntity == null)
            return;
        
        ScoreComponent scoreComp = causingEntity.getComponent(ScoreComponent.class);
        if (scoreComp != null) {
            scoreComp.addScore(score);
        }

    }

}
