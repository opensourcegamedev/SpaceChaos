package dev.game.spacechaos.engine.entity.component.draw;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.annotation.InjectComponent;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.exception.RequiredComponentNotFoundException;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 09.04.2017.
 */
public class SimpleRotationComponent extends BaseComponent implements IUpdateComponent {

    @InjectComponent
    protected DrawTextureComponent textureComponent = null;
    @InjectComponent
    protected DrawTextureRegionComponent textureRegionComponent = null;
    @InjectComponent
    protected DrawComponent drawComponent = null;

    // rotation angle
    protected float angle = 0;
    protected float rotationSpeed = 1f;

    public SimpleRotationComponent(float rotationSpeed) {
        this.rotationSpeed = -rotationSpeed;
    }

    public SimpleRotationComponent() {
        this.rotationSpeed = -1f;
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        // get components
        this.textureComponent = entity.getComponent(DrawTextureComponent.class);
        this.textureRegionComponent = entity.getComponent(DrawTextureRegionComponent.class);

        if (this.textureComponent == null && this.textureRegionComponent == null) {
            throw new RequiredComponentNotFoundException(
                    "entity doesnt have an DrawTextureComponent or DrawTextureRegionComponent");
        } else if (this.textureRegionComponent != null) {
            this.drawComponent = this.textureRegionComponent;
        } else if (this.textureComponent != null) {
            this.drawComponent = this.textureComponent;
        }
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        // rotate object
        this.angle = (this.angle + time.getDeltaTime() * rotationSpeed * 100) % 360;

        while (this.angle < 0) {
            this.angle += 360;
        }

        // set rotation angle
        drawComponent.setRotationAngle(this.angle);
    }

    @Override
    public ECSPriority getUpdateOrder() {
        return ECSPriority.NORMAL;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getRotationSpeed() {
        return -this.rotationSpeed;
    }

    public void setRotationSpeed(float speed) {
        this.rotationSpeed = -speed;
    }

}
