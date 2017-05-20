package dev.game.spacechaos.game.entities.component.ai;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;
import dev.game.spacechaos.engine.entity.component.draw.MoveDependentDrawRotationComponent;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.exception.RequiredComponentNotFoundException;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.RandomUtils;
import dev.game.spacechaos.game.entities.factory.ProjectileFactory;

/**
 * Adds an AI-component to an entity, so it follows the player in a specific, non-suicidal way.
 *
 * @author SpaceChaos-Team (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @version 1.0.0-PreAlpha
 */
public class EnemyShuttleAIComponent extends BaseComponent implements IUpdateComponent {

    //required components
    private PositionComponent positionComponent = null;
    private MoveComponent moveComponent = null;
    private MoveDependentDrawRotationComponent moveDependentDrawRotationComponent = null;

    //target entity to follow
    private Entity targetEntity = null;
    private PositionComponent targetPosition = null;

    //move direction
    private Vector2 moveDir = new Vector2(0, 0);

    private long elapsed = 0;
    private long shootInterval = 1000;

    private Texture projectileTexture = null;

    private Vector2 tmpVector = new Vector2(0, 0);

    public EnemyShuttleAIComponent(Entity targetEntity, Texture projectileTexture) {
        if (targetEntity == null) {
            throw new NullPointerException("target entity cannot be null.");
        }

        this.projectileTexture = projectileTexture;

        this.targetEntity = targetEntity;
        this.targetPosition = targetEntity.getComponent(PositionComponent.class);

        //check if required component exists
        if (this.targetPosition == null) {
            throw new RequiredComponentNotFoundException("PositionComponent is required on target entity, but cannot be found.");
        }

        //generate random shoot interval
        int minShootInterval = 2000;
        int maxShootInterval = 5000;
        this.shootInterval = RandomUtils.getRandomNumber(minShootInterval, maxShootInterval);
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        //get required components
        this.positionComponent = entity.getComponent(PositionComponent.class);
        this.moveComponent = entity.getComponent(MoveComponent.class);
        this.moveDependentDrawRotationComponent = entity.getComponent(MoveDependentDrawRotationComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("Entity doesn't have an PositionComponent.");
        }

        if (this.moveComponent == null) {
            throw new RequiredComponentNotFoundException("Entity doesn't have an MoveComponent.");
        }

        if (this.moveDependentDrawRotationComponent == null) {
            throw new RequiredComponentNotFoundException("Entity doesn't have an MoveDependentDrawRotation.");
        }
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        this.elapsed += time.getDeltaTime() * 1000;

        //calculate target direction and move in this direction
        moveDir.set(targetPosition.getMiddleX() - positionComponent.getMiddleX(), targetPosition.getMiddleY() - positionComponent.getMiddleY());

        if (canShoot()) {
            shootProjectile();
        }

        if (!isMovementRequired()) {
            //don't move entity
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

    private boolean isMovementRequired() {
        //calculate target direction and move in this direction
        moveDir.set(targetPosition.getMiddleX() - positionComponent.getMiddleX(), targetPosition.getMiddleY() - positionComponent.getMiddleY());

        //get length
        float length = moveDir.len();

        float minDistance = 300;
        return length > minDistance;
    }

    private boolean canShoot() {
        return elapsed > shootInterval;
    }

    private void shootProjectile() {
        //get projectile direction from enemy direction
        float dirX = moveDependentDrawRotationComponent.getFrontVec().x;
        float dirY = moveDependentDrawRotationComponent.getFrontVec().y;

        tmpVector.set(dirX, dirY);
        tmpVector.setLength(100);

        Entity projectile = ProjectileFactory.createProjectile(
                entity.getEntityComponentSystem(),
                dirX + positionComponent.getMiddleX() + tmpVector.x - 20,
                dirY + positionComponent.getMiddleY() + tmpVector.y - 20,
                projectileTexture,
                dirX,
                dirY,
                this.entity
        );

        projectile.getComponent(DrawTextureComponent.class).setRotationAngle(targetEntity.getComponent(DrawTextureComponent.class).getRotationAngle());
        projectile.getComponent(MoveComponent.class).setMoving(true);

        game.runOnUIThread(() -> {
            //add entity on next gameloop cycle
            this.entity.getEntityComponentSystem().addEntity(projectile);
        });

        //reset elapsed time
        this.elapsed = 0;
    }

}
