package dev.game.spacechaos.engine.entity.component.ai;

import com.badlogic.gdx.math.Vector2;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.exception.RequiredComponentNotFoundException;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

import java.util.Random;

/**
 * Created by Justin on 09.04.2017.
 */
public class RandomWalkComponent extends BaseComponent implements IUpdateComponent {

    //required components
    protected PositionComponent positionComponent = null;
    protected MoveComponent moveComponent = null;

    protected boolean initialized = false;
    protected float startX = 0;
    protected float startY = 0;
    protected Vector2 targetPos = new Vector2();
    protected float minLength = 100;
    protected float maxLength = 1000;
    protected float minDistance = 10;

    protected Vector2 tmpVector = new Vector2();
    protected Random random = new Random();

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        //get required components
        this.positionComponent = entity.getComponent(PositionComponent.class);
        this.moveComponent = entity.getComponent(MoveComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }

        if (this.moveComponent == null) {
            throw new RequiredComponentNotFoundException("entity doesnt have an MoveComponent.");
        }
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        if (!initialized) {
            generateNewRoute();
            initialized = true;
        }

        //check if entity is near target
        tmpVector.set(targetPos.x - positionComponent.getMiddleX(), targetPos.y - positionComponent.getMiddleY());
        float length = tmpVector.len();

        if (length < minDistance) {
            generateNewRoute();
        }

        moveComponent.setMoveDirection(targetPos.x - positionComponent.getMiddleX(), targetPos.y - positionComponent.getMiddleY());
    }

    @Override
    public ECSPriority getUpdateOrder() {
        return ECSPriority.VERY_HIGH;
    }

    protected void generateNewRoute () {
        //get current position
        this.startX = positionComponent.getMiddleX();
        this.startY = positionComponent.getMiddleY();

        //get random angle
        float angle = (float) Math.random() * 360;

        //get random length
        float length = random.nextFloat() * (maxLength - minLength) + minLength;

        tmpVector.set(length, 0);
        tmpVector.setAngle(angle);

        //set target position
        targetPos.set(tmpVector.x + positionComponent.getMiddleX(), tmpVector.y + positionComponent.getMiddleY());
    }

    public Vector2 getTargetPos () {
        return this.targetPos;
    }

}
