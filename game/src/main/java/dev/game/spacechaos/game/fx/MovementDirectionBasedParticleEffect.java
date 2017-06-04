package dev.game.spacechaos.game.fx;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;

import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;

public class MovementDirectionBasedParticleEffect extends BaseParticleEffect {

    private MoveComponent moveComp;

    public MovementDirectionBasedParticleEffect(ParticleEffect effect, float offsetX, float offsetY) {
        super(effect, offsetX, offsetY);
    }

    @Override
    protected void initAngle(Entity entity) {
        moveComp = entity.getComponent(MoveComponent.class);
        if (moveComp == null) {
            throw new IllegalStateException("Entity doesn't have a MoveComponent.");
        }

    }

    @Override
    protected float getAngle() {
        return moveComp.getMoveDirection().angle();
    }

}
