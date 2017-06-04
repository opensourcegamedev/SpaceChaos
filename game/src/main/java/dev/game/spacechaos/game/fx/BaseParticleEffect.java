package dev.game.spacechaos.game.fx;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.time.GameTime;

public abstract class BaseParticleEffect {

    private ParticleEffectPool effectPool;
    private PooledEffect particleEffect;
    protected PositionComponent positionComponent;
    private Vector2 offset;
    private float scale;
    private float rotationDifference;

    public BaseParticleEffect(ParticleEffect effect, float offsetX, float offsetY) {
        this(effect, offsetX, offsetY, 1);
    }

    public BaseParticleEffect(ParticleEffect effect, float offsetX, float offsetY, float scale) {
        this(effect, offsetX, offsetY, scale, 0);
    }

    public BaseParticleEffect(ParticleEffect effect, float offsetX, float offsetY, float scale,
            float rotationDifference) {
        this.offset = new Vector2(offsetX, offsetY);
        this.effectPool = new ParticleEffectPool(effect, 0, 200);
        this.scale = scale;
        this.rotationDifference = rotationDifference;
    }

    public void init(Entity entity) {
        particleEffect = effectPool.obtain();
        this.positionComponent = entity.getComponent(PositionComponent.class);

        initAngle(entity);

        offset.setAngle(getAngle());
        particleEffect.setPosition(positionComponent.getMiddleX() - offset.x,
                positionComponent.getMiddleY() - offset.y);
        particleEffect.scaleEffect(scale);
        particleEffect.start();
    }

    protected abstract void initAngle(Entity entity);

    public void update(float deltaTime) {
        offset.setAngle(getAngle());
        particleEffect.setPosition(positionComponent.getMiddleX() - offset.x,
                positionComponent.getMiddleY() - offset.y);
        // Set the rotation
        for (int i = 0; i < particleEffect.getEmitters().size; i++) {
            particleEffect.getEmitters().get(i).getAngle().setLow(getAngle() - 180 + rotationDifference);
            particleEffect.getEmitters().get(i).getAngle().setHigh(getAngle() - 180 + rotationDifference);
        }
        particleEffect.update(deltaTime);
    }

    public void draw(GameTime time, SpriteBatch batch) {
        particleEffect.draw(batch, time.getDeltaTime());
        particleEffect.free();
    }

    protected abstract float getAngle();

}
