package dev.game.spacechaos.engine.entity.component.collision;

import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.exception.RequiredComponentNotFoundException;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 12.04.2017.
 */
public class CollisionComponent extends BaseComponent implements IUpdateComponent {

    protected PositionComponent positionComponent = null;
    protected MoveComponent moveComponent = null;

    //list with all collision listeners
    protected List<CollisionListener> listenerList = new ArrayList<>();

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        this.positionComponent = entity.getComponent(PositionComponent.class);
        this.moveComponent = entity.getComponent(MoveComponent.class);

        if (this.positionComponent == null) {
            throw new RequiredComponentNotFoundException("entity doesnt have an PositionComponent.");
        }
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        //TODO: check for collision with other entities
    }

    @Override
    public ECSPriority getUpdateOrder() {
        return ECSPriority.COLLISION_DETECTION;
    }
}
