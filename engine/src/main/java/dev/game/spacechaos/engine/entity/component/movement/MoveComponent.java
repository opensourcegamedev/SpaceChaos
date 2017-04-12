package dev.game.spacechaos.engine.entity.component.movement;

import com.badlogic.gdx.math.Vector2;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 08.04.2017.
 */
public class MoveComponent extends BaseComponent implements IUpdateComponent {

    protected PositionComponent positionComponent = null;

    protected Vector2 moveDirection = new Vector2(0, 0);
    protected float speed = 1;

    //flag, if entity is moving
    protected boolean isMoving = false;

    //temporary vector for calculations
    protected Vector2 tmpVector = new Vector2();

    public MoveComponent (float moveDirectionX, float moveDirectionY, float speed) {
        moveDirection.set(moveDirectionX, moveDirectionY);
        this.speed = speed;
    }

    public MoveComponent (float speed) {
        moveDirection.set(0, 0);
        this.speed = speed;
    }

    @Override
    protected void onInit (BaseGame game, Entity entity) {
        //get required components
        this.positionComponent = entity.getComponent(PositionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have a PositionComponent.");
        }
    }

    @Override
    public void update (BaseGame game, GameTime time) {
        /*if (moveDirection.x == 0 && moveDirection.y == 0) {
            //set flag
            isMoving = false;
        } else {
            //set flag
            isMoving = true;
        }*/

        if (!isMoving) {
            //we dont have to move entity
            return;
        }

        //get delta time
        float dt = time.getDeltaTime();

        //set vector values to temporary vector
        tmpVector.set(moveDirection.x, moveDirection.y);

        //normalize and scale move vector
        tmpVector.nor();
        tmpVector.scl(this.speed * dt * 100f);

        //move entity
        positionComponent.move(tmpVector.x, tmpVector.y);
    }

    @Override
    public ECSPriority getUpdateOrder () {
        return ECSPriority.LOW;
    }

    public boolean isMoving () {
        return this.isMoving;
    }

    public void setMoving (boolean moving) {
        this.isMoving = moving;

        if (!moving) {
            moveDirection.set(0, 0);
        }
    }

    public Vector2 getMoveDirection () {
        return this.moveDirection;
    }

    public void setMoveDirection (float x, float y) {
        this.moveDirection.set(x, y);

        if (x == 0 && y == 0) {
            this.isMoving = false;
        } else {
            this.moveDirection.nor();
            this.isMoving = true;
        }
    }

    public float getSpeed () {
        return this.speed;
    }

    public void setSpeed (float speed) {
        this.speed = speed;
    }

}
