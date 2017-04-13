package dev.game.spacechaos.game.entities.factory;

import com.badlogic.gdx.graphics.Texture;
import dev.game.spacechaos.engine.collision.shape.CCircle;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.ai.SimpleFollowAIMovementComponent;
import dev.game.spacechaos.engine.entity.component.collision.AutoRemoveOnCollisionComponent;
import dev.game.spacechaos.engine.entity.component.collision.AvoidRemoveOnCollisionComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;
import dev.game.spacechaos.engine.entity.component.draw.MoveDependentDrawRotationComponent;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;
import dev.game.spacechaos.engine.entity.component.sound.AvoidCollisionSoundComponent;
import dev.game.spacechaos.engine.entity.component.utils.TimedAutoRemoveComponent;
import dev.game.spacechaos.game.entities.component.collision.AvoidCollisionCameraShakeComponent;
import dev.game.spacechaos.game.entities.component.combat.AttackComponent;

/**
 * Created by Justin on 10.04.2017.
 */

public class ProjectileFactory {

    /**
     * create an new projectile entity
     *
     * @param ecs     entity component system
     * @param x       x start position of projectile
     * @param y       y start position of projectile
     * @param texture texture of projectile
     * @param moveX   x move direction (for example to move to left: x = -1, y = 0)
     * @param moveY   y move direction (for example to move to left: x = -1, y = 0)
     * @param speed   movement speed
     * @param ttl     time to live of projectile (after this time in milliseconds the entity will removed automatically)
     * @return projectile entity
     */

    public static Entity createProjectile(EntityManager ecs, float x, float y, Texture texture, float moveX, float moveY, float speed, Entity playerEntity, long ttl) {
        //create new entity
        Entity projectileEntity = new Entity(ecs);

        //every entity requires a position component
        projectileEntity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add texture component to draw texture
        projectileEntity.addComponent(new DrawTextureComponent(texture, texture.getWidth() / 2, texture.getHeight() / 2), DrawTextureComponent.class);

        //add component to move entity
        projectileEntity.addComponent(new MoveComponent(moveX, moveY, speed), MoveComponent.class);

        //add collision component, so player can collide with other space shuttles or meteorits
        projectileEntity.addComponent(new CollisionComponent(), CollisionComponent.class);
        projectileEntity.getComponent(CollisionComponent.class).addInnerShape(new CCircle(texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth() / 2));

        //add attack component
        projectileEntity.addComponent(new AttackComponent(playerEntity, 100));

        //add component to avoid camera shake, if player fires projectile (if projectile starts, projectile is in player collision hull)
        projectileEntity.addComponent(new AvoidCollisionCameraShakeComponent(), AvoidCollisionCameraShakeComponent.class);

        //add component to avoid collision sound
        projectileEntity.addComponent(new AvoidCollisionSoundComponent(), AvoidCollisionSoundComponent.class);

        //add component to remove entity, if entity collides with an other entity
        projectileEntity.addComponent(new AutoRemoveOnCollisionComponent(), AutoRemoveOnCollisionComponent.class);

        //dont remove, if projectile collides with player
        projectileEntity.addComponent(new AvoidRemoveOnCollisionComponent(playerEntity), AvoidRemoveOnCollisionComponent.class);

        //add component to auto remove projectile after a given time
        projectileEntity.addComponent(new TimedAutoRemoveComponent(ttl));

        return projectileEntity;
    }

    /**
     * create an new projectile entity
     *
     * @param ecs     entity component system
     * @param x       x start position of projectile
     * @param y       y start position of projectile
     * @param texture texture of projectile
     * @param speed   movement speed
     * @param ttl     time to live of projectile (after this time in milliseconds the entity will removed automatically)
     * @return projectile entity
     */

    public static Entity createTorpedoProjectile(EntityManager ecs, float x, float y, Texture texture, float speed, Entity playerEntity, Entity enemyEntity, long ttl) {
        //create new entity
        Entity projectileEntity = new Entity(ecs);

        //every entity requires a position component
        projectileEntity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add texture component to draw texture
        projectileEntity.addComponent(new DrawTextureComponent(texture, texture.getWidth() / 2, texture.getHeight() / 2), DrawTextureComponent.class);

        //add component to move entity
        projectileEntity.addComponent(new MoveComponent(speed), MoveComponent.class);

        //add component to rotate projectile dependent on move direction
        enemyEntity.addComponent(new MoveDependentDrawRotationComponent(), MoveDependentDrawRotationComponent.class);

        //add collision component, so player can collide with other space shuttles or meteorits
        projectileEntity.addComponent(new CollisionComponent(), CollisionComponent.class);
        projectileEntity.getComponent(CollisionComponent.class).addInnerShape(new CCircle(texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth() / 2));

        //add attack component
        projectileEntity.addComponent(new AttackComponent(playerEntity, 500));

        //add component to avoid camera shake, if player fires projectile (if projectile starts, projectile is in player collision hull)
        projectileEntity.addComponent(new AvoidCollisionCameraShakeComponent(), AvoidCollisionCameraShakeComponent.class);

        //add component to avoid collision sound
        projectileEntity.addComponent(new AvoidCollisionSoundComponent(), AvoidCollisionSoundComponent.class);

        //add component to remove entity, if entity collides with an other entity
        projectileEntity.addComponent(new AutoRemoveOnCollisionComponent(), AutoRemoveOnCollisionComponent.class);

        //dont remove, if projectile collides with player
        projectileEntity.addComponent(new AvoidRemoveOnCollisionComponent(playerEntity), AvoidRemoveOnCollisionComponent.class);

        //AI
        projectileEntity.addComponent(new SimpleFollowAIMovementComponent(enemyEntity));

        //add component to auto remove projectile after a given time
        projectileEntity.addComponent(new TimedAutoRemoveComponent(ttl));

        return projectileEntity;
    }

}
