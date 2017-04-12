package dev.game.spacechaos.engine.entity.component.collision;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.collision.CShape;
import dev.game.spacechaos.engine.collision.CollisionManager;
import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.collision.shape.CCircle;
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
import java.util.Collection;
import java.util.List;

/**
 * Created by Justin on 12.04.2017.
 */
public class CollisionComponent extends BaseComponent implements IUpdateComponent {

    protected PositionComponent positionComponent = null;
    protected MoveComponent moveComponent = null;

    //list with all collision listeners
    protected List<CollisionListener> listenerList = new ArrayList<>();

    //list with all collision shapes
    protected List<CShape> collisionShapes = new ArrayList<>();

    protected CCircle hullShape = null;

    protected CollisionManager collisionManager = null;
    protected List<Entity> alreadyInCollisionEntities = new ArrayList<>();
    protected boolean alreadyInCollision = false;
    protected List<Entity> tmpList = new ArrayList<>();

    public CollisionComponent (CollisionManager collisionManager) {
        this.collisionManager = collisionManager;
    }

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
        //clear temporary list
        this.tmpList.clear();

        //check for collision with other entities
        Collection<Entity> collidedEntities = this.collisionManager.checkForCollision(entity, this, this.positionComponent);

        boolean collided = collidedEntities.size() > 0;

        if (!collided) {
            exitAllCollisions();

            return;
        }

        //entity is in collision

        for (Entity collisionEntity : collidedEntities) {
            //check, if entity was already in collision
            if (this.alreadyInCollisionEntities.contains(collisionEntity)) {
                //entity was already in collision
                callCollisionStayListeners(collisionEntity);
            } else {
                //new collision
                callCollisionEnterListeners(collisionEntity);
            }
        }

        //check, if entities have exit collision
        for (Entity collisionEntity : this.alreadyInCollisionEntities) {
            if (!collidedEntities.contains(collisionEntity)) {
                //entity has exit collision
                callCollisionExitListeners(collisionEntity);

                //add to list, so we can remove entity from list after iteration
                tmpList.add(collisionEntity);
            }
        }

        this.alreadyInCollisionEntities.removeAll(tmpList);

        //TODO: call external triggers (stop movement and so on)

        //update flag
        this.alreadyInCollision = collided;
    }

    protected void exitAllCollisions () {
        for (Entity collisionEntity : this.alreadyInCollisionEntities) {
            //call exit listeners
            for (CollisionListener listener : this.listenerList) {
                listener.onExit(entity, collisionEntity);
            }
        }

        //clear list
        this.alreadyInCollisionEntities.clear();
    }

    protected void callCollisionEnterListeners (Entity collisionEntity) {
        //call collision listeners
        for (CollisionListener listener : this.listenerList) {
            listener.onEnter(entity, collisionEntity);
        }
    }

    protected void callCollisionStayListeners (Entity collisionEntity) {
        //call collision listeners
        for (CollisionListener listener : this.listenerList) {
            listener.onStay(entity, collisionEntity);
        }
    }

    protected void callCollisionExitListeners (Entity collisionEntity) {
        //call collision listeners
        for (CollisionListener listener : this.listenerList) {
            listener.onExit(entity, collisionEntity);
        }
    }

    @Override
    public ECSPriority getUpdateOrder() {
        return ECSPriority.COLLISION_DETECTION;
    }

    public void drawCollisionBoxes (GameTime time, CameraWrapper camera, ShapeRenderer shapeRenderer, Color color) {
        //first, draw hull
        if (this.hullShape != null) {
            this.hullShape.drawShape(time, camera, shapeRenderer, color);
        }
    }

}
