package dev.game.spacechaos.engine.entity.component.collision;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.collision.CShape;
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

        //TODO: call listeners

        //TODO: call external triggers (stop movement and so on)
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
