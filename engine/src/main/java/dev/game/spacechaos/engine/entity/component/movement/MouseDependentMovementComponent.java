package dev.game.spacechaos.engine.entity.component.movement;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IDrawComponent;
import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.annotation.RequiredComponents;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.MouseUtils;
import dev.game.spacechaos.engine.utils.SpriteBatcherUtils;

/**
 * Created by Justin on 08.04.2017.
 */
@RequiredComponents(components = {PositionComponent.class, MoveComponent.class})
public class MouseDependentMovementComponent extends BaseComponent implements IUpdateComponent, IDrawComponent {

    protected PositionComponent positionComponent = null;
    protected MoveComponent moveComponent = null;

    //used to store front coordinates
    protected float frontX = 0;
    protected float frontY = 0;

    //front vector: origin shuttle center.
    private Vector2 frontVec = new Vector2(0, 0);
    protected Vector2 tmpVector = new Vector2(0, 0);

    //minimum distance of mouse to entity which is needed to move entity
    protected float minMouseDistance = 20;

    public MouseDependentMovementComponent(float originX, float originY) {
        frontVec.set(originX, originY);
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
            throw new IllegalStateException("entity doesnt have an MoveComponent.");
        }
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        //update front
        this.frontX = positionComponent.getMiddleX() + frontVec.x;
        this.frontY = positionComponent.getMiddleY() + frontVec.y;

        //get mouse position relative to shuttle
        Vector2 vector = MouseUtils.getRelativePositionToEntity(game.getCamera(), frontX, frontY);
        float mouseX = vector.x;
        float mouseY = vector.y;

        //get mouse angle relative to shuttle
        float angle = MouseUtils.getRelativeMouseAngle(game.getCamera(), positionComponent.getMiddleX(), positionComponent.getMiddleY());

        //adjust front angle
        frontVec.setAngle(angle);

        //avoid jerky
        /*if (Math.abs(mouseX) < 5) { //changed from 1 to 5
            mouseX = 0;
        }

        if (Math.abs(mouseY) < 5) { //changed from 1 to 5
            mouseY = 0;
        }*/

        tmpVector.set(mouseX, mouseY);

        //calculate length of vector
        float length = tmpVector.len();

        if (length > minMouseDistance) {
            //set movement direction
            moveComponent.setMoveDirection(tmpVector.x, tmpVector.y);

            //set moving flag
            moveComponent.setMoving(true);
        } else {
            //dont move entity
            moveComponent.setMoveDirection(0, 0);
            moveComponent.setMoving(false);
        }
    }

    @Override
    public ECSPriority getUpdateOrder() {
        return ECSPriority.VERY_HIGH;
    }

    @Override
    public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //TODO: outsource this code to extra component

        //draw center, only for debugging purposes
        SpriteBatcherUtils.fillRectangle(batch, positionComponent.getMiddleX() - 10, positionComponent.getMiddleY() - 10, 20, 20, Color.YELLOW);

        //draw mouse position
        Vector3 mousePos = camera.getMousePosition();
        //SpriteBatcherUtils.fillRectangle(batch, mousePos.x - 5, mousePos.y - 5, 10, 10, Color.RED);

        //draw position
        //SpriteBatcherUtils.fillRectangle(batch, positionComponent.getX() - 5, positionComponent.getY() - 5, 10, 10, Color.BLUE);

        //draw front position
        //SpriteBatcherUtils.fillRectangle(batch, frontX - 5, frontY - 5, 10, 10, Color.GREEN);
    }

    @Override
    public ECSPriority getDrawOrder() {
        return ECSPriority.VERY_LOW;
    }

    public Vector2 getFrontVec () {
        return this.frontVec;
    }

    public float getMinMouseDistance () {
        return this.minMouseDistance;
    }

    public void setMinMouseDistance (float minMouseDistance) {
        this.minMouseDistance = minMouseDistance;
    }

}
