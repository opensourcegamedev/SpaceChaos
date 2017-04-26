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

/**
 * Created by Justin on 08.04.2017.
 */
public class SimpleFollowAIMovementComponent extends BaseComponent implements IUpdateComponent {

    //required components
    protected PositionComponent positionComponent = null;
    protected MoveComponent moveComponent = null;

    //target entity to follow
    protected Entity targetEntity = null;
    protected PositionComponent targetPosition = null;

    protected float minLength = 1;

    //move direction
    private Vector2 moveDir = new Vector2(0, 0);

    public SimpleFollowAIMovementComponent(Entity targetEntity) {
        if (targetEntity == null) {
            throw new NullPointerException("target entity cannot be null.");
        }

        this.targetEntity = targetEntity;
        this.targetPosition = targetEntity.getComponent(PositionComponent.class);

        //check if required component exists
        if (this.targetPosition == null) {
            throw new RequiredComponentNotFoundException("PositionComponent is required on target entity, but cannot be found.");
        }
    }

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
        //calculate target direction and move in this direction
        moveDir.set(targetPosition.getMiddleX() - positionComponent.getMiddleX(), targetPosition.getMiddleY() - positionComponent.getMiddleY());

        //get length
        float length = moveDir.len();

        //avoid jerky
        if (length < this.minLength) {
            //dont move entity
            moveComponent.setMoveDirection(0, 0);
            moveComponent.setMoving(false);

            return;
        }

        moveDir.nor();

        //set move direction
        moveComponent.setMoveDirection(moveDir.x, moveDir.y);
    }

    @Override
    public ECSPriority getUpdateOrder() {
        return ECSPriority.HIGH;
    }
}
