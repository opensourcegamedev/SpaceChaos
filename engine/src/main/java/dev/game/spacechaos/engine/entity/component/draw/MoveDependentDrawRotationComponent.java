package dev.game.spacechaos.engine.entity.component.draw;

import com.badlogic.gdx.math.Vector2;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.annotation.InjectComponent;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.exception.RequiredComponentNotFoundException;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 08.04.2017.
 */
public class MoveDependentDrawRotationComponent extends BaseComponent implements IUpdateComponent {

    @InjectComponent(nullable = false)
    protected MoveComponent moveComponent = null;
    @InjectComponent
    protected DrawTextureComponent textureComponent = null;
    @InjectComponent
    protected DrawTextureRegionComponent textureRegionComponent = null;
    @InjectComponent
    protected DrawComponent drawComponent = null;

    protected float lastAngle = 0;

    protected Vector2 frontVector = new Vector2(0, 0);

    public MoveDependentDrawRotationComponent() {
        frontVector.setLength(1);
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        if (this.textureComponent == null && this.textureRegionComponent == null) {
            throw new RequiredComponentNotFoundException(
                    "Entity doesn't have a DrawTextureComponent nor a DrawTextureRegionComponent");
        } else if (this.textureRegionComponent != null) {
            this.drawComponent = this.textureRegionComponent;
        } else if (this.textureComponent != null) {
            this.drawComponent = this.textureComponent;
        }
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        // get move direction
        Vector2 moveDir = moveComponent.getMoveDirection();

        float angle = 0;

        if (moveComponent.isMoving()) {
            // get angle
            angle = moveDir.angle() - 90;
            this.lastAngle = angle;
        } else {
            angle = this.lastAngle;
        }

        frontVector.set(0, 1);
        frontVector.setLength(1);
        frontVector.setAngle(angle + 90);

        drawComponent.setRotationAngle(angle);
    }

    @Override
    public ECSPriority getUpdateOrder() {
        return ECSPriority.VERY_LOW;
    }

    public Vector2 getFrontVec() {
        return frontVector;
    }

}
