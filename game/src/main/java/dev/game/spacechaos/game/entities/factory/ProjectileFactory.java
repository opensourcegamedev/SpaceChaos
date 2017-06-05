package dev.game.spacechaos.game.entities.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

import dev.game.spacechaos.engine.collision.shape.CCircle;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.collision.AutoRemoveOnCollisionComponent;
import dev.game.spacechaos.engine.entity.component.collision.AvoidRemoveOnCollisionComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;
import dev.game.spacechaos.engine.entity.component.draw.MoveDependentDrawRotationComponent;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;
import dev.game.spacechaos.engine.entity.component.sound.AvoidCollisionSoundComponent;
import dev.game.spacechaos.engine.entity.component.utils.TimedAutoRemoveComponent;
import dev.game.spacechaos.game.entities.component.collision.AvoidCollisionCameraShakeComponent;
import dev.game.spacechaos.game.entities.component.combat.ProjectileComponent;
import dev.game.spacechaos.game.entities.component.draw.ParticleComponent;
import dev.game.spacechaos.game.fx.BaseParticleEffect;
import dev.game.spacechaos.game.fx.MovementDirectionBasedParticleEffect;

/**
 * Creates different types of projectiles shot by real players or enemy-AIs.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class ProjectileFactory {

    /**
     * Creates a new entity moving straight forward and causing damage.
     *
     * @param ecs
     *            entity component system
     * @param x
     *            x start position of projectile
     * @param y
     *            y start position of projectile
     * @param texture
     *            texture of projectile
     * @param moveX
     *            x move direction (for example to move to left: x = -1, y = 0)
     * @param moveY
     *            y move direction (for example to move to top: x = 0, y = -1)
     * @return projectile entity
     */
    public static Entity createProjectile(EntityManager ecs, float x, float y, Texture texture, float moveX,
            float moveY, Entity ownEntity) {
        // create new entity
        Entity projectileEntity = new Entity(ecs);

        // every entity requires a position component
        projectileEntity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        // add texture component to draw texture
        projectileEntity.addComponent(
                new DrawTextureComponent(texture, texture.getWidth() / 2, texture.getHeight() / 2),
                DrawTextureComponent.class);

        // add component to move entity
        projectileEntity.addComponent(new MoveComponent(moveX, moveY, 4f), MoveComponent.class);

        // add component to rotate shuttle dependent on move direction
        projectileEntity.addComponent(new MoveDependentDrawRotationComponent(),
                MoveDependentDrawRotationComponent.class);

        // add collision component, so entity can collide with other space
        // shuttles or meteorites
        projectileEntity.addComponent(new CollisionComponent(), CollisionComponent.class);
        projectileEntity.getComponent(CollisionComponent.class)
                .addInnerShape(new CCircle(texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth() / 2));

        // add attack component
        projectileEntity.addComponent(new ProjectileComponent(ownEntity, 100));

        // add component to avoid camera shake, if player fires projectile (when
        // projectile starts, projectile is in player collision hull)
        projectileEntity.addComponent(new AvoidCollisionCameraShakeComponent(),
                AvoidCollisionCameraShakeComponent.class);

        // add component to avoid collision sound
        projectileEntity.addComponent(new AvoidCollisionSoundComponent(), AvoidCollisionSoundComponent.class);

        // add component to remove entity, if entity collides with an other
        // entity
        projectileEntity.addComponent(new AutoRemoveOnCollisionComponent(), AutoRemoveOnCollisionComponent.class);

        // don't remove, if projectile collides with player
        projectileEntity.addComponent(new AvoidRemoveOnCollisionComponent(ownEntity),
                AvoidRemoveOnCollisionComponent.class);

        // add component to auto remove projectile after a given time
        projectileEntity.addComponent(new TimedAutoRemoveComponent(4000L));

        return projectileEntity;
    }

    /**
     * Creating a new entity moving forward and causing much damage.
     *
     * @param ecs
     *            entity component system
     * @param x
     *            x start position of projectile
     * @param y
     *            y start position of projectile
     * @param texture
     *            texture of projectile
     * @return projectile entity
     */
    public static Entity createTorpedoProjectile(EntityManager ecs, float x, float y, Texture texture, float moveX,
            float moveY, Entity playerEntity, Entity enemyEntity) {

        // create new entity
        Entity projectileEntity = new Entity(ecs);

        // every entity requires a position component
        projectileEntity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        // add texture component to draw texture
        projectileEntity.addComponent(
                new DrawTextureComponent(texture, texture.getWidth() / 2, texture.getHeight() / 2),
                DrawTextureComponent.class);

        // add component to move entity
        projectileEntity.addComponent(new MoveComponent(moveX, moveY, 4f), MoveComponent.class);

        // add component to rotate projectile dependent on move direction
        enemyEntity.addComponent(new MoveDependentDrawRotationComponent(), MoveDependentDrawRotationComponent.class);

        // add collision component, so entity can collide with other space
        // shuttles or meteorites
        projectileEntity.addComponent(new CollisionComponent(), CollisionComponent.class);
        projectileEntity.getComponent(CollisionComponent.class)
                .addInnerShape(new CCircle(texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth() / 2));

        // add attack component
        projectileEntity.addComponent(new ProjectileComponent(playerEntity, 500));

        // add component to avoid camera shake, if player fires projectile (if
        // projectile starts, projectile is in player collision hull)
        projectileEntity.addComponent(new AvoidCollisionCameraShakeComponent(),
                AvoidCollisionCameraShakeComponent.class);

        // add component to avoid collision sound
        projectileEntity.addComponent(new AvoidCollisionSoundComponent(), AvoidCollisionSoundComponent.class);

        // add component to remove entity, if entity collides with an other
        // entity
        projectileEntity.addComponent(new AutoRemoveOnCollisionComponent(), AutoRemoveOnCollisionComponent.class);

        // don't remove, if projectile collides with player
        projectileEntity.addComponent(new AvoidRemoveOnCollisionComponent(playerEntity),
                AvoidRemoveOnCollisionComponent.class);

        // AI
        // projectileEntity.addComponent(new
        // SimpleFollowAIMovementComponent(enemyEntity));

        // add component to auto remove projectile after a given time
        projectileEntity.addComponent(new TimedAutoRemoveComponent(3000L));

        // add component for smoke particles
        // create smoke effect
        ParticleEffect smokeEffect = new ParticleEffect();
        smokeEffect.load(Gdx.files.internal("./data/particles/smokeGrey.p"), Gdx.files.internal(""));
        BaseParticleEffect particleEffect = new MovementDirectionBasedParticleEffect(smokeEffect,
                texture.getWidth() / 2 - 12, texture.getHeight() / 2 - 12);
        projectileEntity.addComponent(new ParticleComponent(particleEffect));

        return projectileEntity;
    }

}
