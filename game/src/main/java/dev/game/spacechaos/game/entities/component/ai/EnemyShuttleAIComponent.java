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
 * Created by Justin on 17.04.2017.
 */
public class EnemyShuttleAIComponent extends BaseComponent implements IUpdateComponent {

    //required components
    protected PositionComponent positionComponent = null;
    protected MoveComponent moveComponent = null;
    protected MoveDependentDrawRotationComponent moveDependentDrawRotationComponent = null;

    //target entity to follow
    protected Entity targetEntity = null;
    protected PositionComponent targetPosition = null;

    protected float minLength = 1;

    protected float minDistance = 300;

    //move direction
    private Vector2 moveDir = new Vector2(0, 0);

    protected long elapsed = 0;
    protected long shootInterval = 1000;
    protected int minShootInterval = 2000;
    protected int maxShootInterval = 5000;

    protected Texture projectileTexture = null;

    public EnemyShuttleAIComponent(Entity targetEntity, Texture projectileTexture) {
        if (targetEntity == null) {
            throw new NullPointerException("target entity cannot be null.");
        }

        this.projectileTexture = projectileTexture;

        this.targetEntity = targetEntity;
        this.targetPosition = targetEntity.getComponent(PositionComponent.class);

        //check, if required component exists
        if (this.targetPosition == null) {
            throw new RequiredComponentNotFoundException("PositionComponent is required on target entity, but cannot be found.");
        }

        //generate random shoot interval
        this.shootInterval = RandomUtils.getRandomNumber(minShootInterval, maxShootInterval);
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        //get required components
        this.positionComponent = entity.getComponent(PositionComponent.class);
        this.moveComponent = entity.getComponent(MoveComponent.class);
        this.moveDependentDrawRotationComponent = entity.getComponent(MoveDependentDrawRotationComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }

        if (this.moveComponent == null) {
            throw new RequiredComponentNotFoundException("entity doesnt have an MoveComponent.");
        }

        if (this.moveDependentDrawRotationComponent == null) {
            throw new RequiredComponentNotFoundException("entity doesnt have an MoveDependentDrawRotation.");
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

    protected boolean isMovementRequired () {
        //calculate target direction and move in this direction
        moveDir.set(targetPosition.getMiddleX() - positionComponent.getMiddleX(), targetPosition.getMiddleY() - positionComponent.getMiddleY());

        //get length
        float length = moveDir.len();

        return length > minDistance;
    }

    protected boolean canShoot () {
        return elapsed > shootInterval;
    }

    protected void shootProjectile () {
        //get projectile direction from enemy direction
        float dirX = moveDependentDrawRotationComponent.getFrontVec().x;
        float dirY = moveDependentDrawRotationComponent.getFrontVec().y;

        Entity projectile = ProjectileFactory.createProjectile(
                entity.getEntityComponentSystem(),
                dirX + positionComponent.getMiddleX() - 20,
                dirY + positionComponent.getMiddleY() - 20,
                projectileTexture,
                dirX,
                dirY,
                4f,
                this.entity,
                4000L
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
