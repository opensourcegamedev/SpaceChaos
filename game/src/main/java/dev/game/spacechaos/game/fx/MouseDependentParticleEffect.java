package dev.game.spacechaos.game.fx;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;

import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.component.movement.MouseDependentMovementComponent;
import dev.game.spacechaos.game.entities.component.draw.ParticleComponent;

/**
 * This is a particle effect that is dependent on the mouse movement and is used
 * in the player.
 * 
 * @since 1.0.1-PreAlpha
 *
 */
public class MouseDependentParticleEffect extends BaseParticleEffect {

    private Vector2 frontVec;
    private float offsetAngleDifference;

    public MouseDependentParticleEffect(ParticleEffect effect, float offsetX, float offsetY, float scale,
            float rotationDifference, float offsetAngleDifference) {
        super(effect, offsetX, offsetY, scale, rotationDifference);

        this.offsetAngleDifference = offsetAngleDifference;
    }

    @Override
    protected void initAngle(Entity entity) {
        MouseDependentMovementComponent mouseDependentMovementComponent = entity
                .getComponent(MouseDependentMovementComponent.class);
        if (mouseDependentMovementComponent == null) {
            throw new IllegalStateException("Entity doesn't have a MouseDependentMovementComponent.");
        }

        this.frontVec = mouseDependentMovementComponent.getFrontVec();
    }

    @Override
    protected float getAngle() {
        return frontVec.angle() + offsetAngleDifference;
    }

}
