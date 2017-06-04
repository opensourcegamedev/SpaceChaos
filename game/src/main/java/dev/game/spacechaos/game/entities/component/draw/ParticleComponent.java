package dev.game.spacechaos.game.entities.component.draw;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.*;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.movement.MouseDependentMovementComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Draws and updates the given particle effect.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class ParticleComponent extends BaseComponent implements IDrawComponent, IUpdateComponent {

    private PositionComponent positionComponent;
    private MouseDependentMovementComponent mouseDependentMovementComponent;
    private ParticleEffectPool effectPool;
    private ParticleEffect particleEffect;
    private ParticleEffect particleEffectTwo;
    private float angle;
    private Vector2 offset;
    private Vector2 frontVec;
    private Vector2 thruster;

    public ParticleComponent(ParticleEffect particleEffect, float xOffset, float yOffset) {
        this.effectPool = new ParticleEffectPool(particleEffect, 0, 20);
        this.offset = new Vector2(xOffset, yOffset);
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        this.positionComponent = entity.getComponent(PositionComponent.class);
        this.mouseDependentMovementComponent = entity.getComponent(MouseDependentMovementComponent.class);
        this.frontVec = mouseDependentMovementComponent.getFrontVec();

        particleEffect = effectPool.obtain();
        particleEffectTwo = effectPool.obtain();

        angle = frontVec.angle();
        offset.setAngle(angle - 15);
        thruster = new Vector2(positionComponent.getMiddleX() - offset.x,
                positionComponent.getMiddleY() - offset.y);
        particleEffect.setPosition(thruster.x, thruster.y);
        particleEffect.scaleEffect(0.7f);
        particleEffect.start();

        offset.setAngle(-angle - 180 - 15);
        thruster.x = positionComponent.getMiddleX() + offset.x;
        thruster.y = positionComponent.getMiddleY() - offset.y;
        particleEffectTwo.setPosition(thruster.x, thruster.y);
        particleEffectTwo.scaleEffect(0.7f);
        particleEffectTwo.start();
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        angle = frontVec.angle();

        //update thruster left
        offset.setAngle(angle - 15);
        thruster.x = positionComponent.getMiddleX() - offset.x;
        thruster.y = positionComponent.getMiddleY() - offset.y;

        particleEffect.setPosition(thruster.x, thruster.y);
        for (int i = 0; i < particleEffect.getEmitters().size; i++) { //get the list of emitters - things that emit particles
            particleEffect.getEmitters().get(i).getAngle().setLow(angle - 180 - 25); //low is the minimum rotation
            particleEffect.getEmitters().get(i).getAngle().setHigh(angle - 180 - 25); //high is the max rotation
        }
        particleEffect.update(time.getDeltaTime());

        //update thruster right
        offset.setAngle(-angle - 180 - 15);
        thruster.x = positionComponent.getMiddleX() + offset.x;
        thruster.y = positionComponent.getMiddleY() - offset.y;

        particleEffectTwo.setPosition(thruster.x, thruster.y);
        for (int i = 0; i < particleEffectTwo.getEmitters().size; i++) { //get the list of emitters - things that emit particles
            particleEffectTwo.getEmitters().get(i).getAngle().setLow(angle - 180 + 25); //low is the minimum rotation
            particleEffectTwo.getEmitters().get(i).getAngle().setHigh(angle - 180 + 25); //high is the max rotation
        }
        particleEffectTwo.update(time.getDeltaTime());
    }

    @Override
    public ECSPriority getUpdateOrder() {
        return ECSPriority.DRAW_PARTICLES;
    }

    @Override
    public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        particleEffect.draw(batch, time.getDeltaTime());
        particleEffectTwo.draw(batch, time.getDeltaTime());

        //free particles
        effectPool.free((ParticleEffectPool.PooledEffect) particleEffect);
        effectPool.free((ParticleEffectPool.PooledEffect) particleEffectTwo);
    }

    @Override
    public ECSPriority getDrawOrder() {
        return ECSPriority.DRAW_PARTICLES;
    }
}
