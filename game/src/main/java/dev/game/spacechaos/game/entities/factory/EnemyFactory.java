package dev.game.spacechaos.game.entities.factory;

import com.badlogic.gdx.graphics.Texture;

import dev.game.spacechaos.engine.collision.shape.CCircle;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.ai.SimpleFollowAIMovementComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;
import dev.game.spacechaos.engine.entity.component.draw.MoveDependentDrawRotationComponent;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;
import dev.game.spacechaos.game.entities.component.ai.EnemyShuttleAIComponent;
import dev.game.spacechaos.game.entities.component.ai.EnemyShuttleTransitionConfiguration;
import dev.game.spacechaos.game.entities.component.combat.HPComponent;
import dev.game.spacechaos.game.entities.component.combat.GetDamagedOnCollisionComponent;
import dev.game.spacechaos.game.entities.component.combat.RemoveOnDeathComponent;
import dev.game.spacechaos.game.entities.component.combat.RewardComponent;
import dev.game.spacechaos.game.entities.component.draw.DrawHPBarComponent;
import dev.game.spacechaos.game.entities.component.powerup.CanUsePowerUpsComponent;
import dev.game.spacechaos.game.entity.listener.HPDeathListener;

import java.util.List;

/**
 * Creates the new enemy entities.
 * <p>
 * Enemies follow the player and shoot him.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class EnemyFactory {

    public static Entity createNewEnemyShuttle(EntityManager ecs, float x, float y, Texture texture, Entity targetEntity,
                                               Texture projectileTexture, HPDeathListener listener, List<Texture> enemies) {


        Entity enemyEntity = createEnemyShuttle(ecs, x, y, texture);

        for (int i = 0; i <= enemies.size(); i++) {
            if (texture == enemies.get(i)) {
                // TODO: An enum with all transitions should improve the design
                EnemyShuttleTransitionConfiguration e = new EnemyShuttleTransitionConfiguration();
                e.initializeEnemyTransitions();
                int currentHealth = e.getEnemyTransition().get(i).currentHealth;
                int maxHealth = e.getEnemyTransition().get(i).maxHealth;
                float speed = e.getEnemyTransition().get(i).speed;
                int scoreByDeath = e.getEnemyTransition().get(i).scoreByDeath;
                boolean aiRequired = e.getEnemyTransition().get(i).aiRequired;
                int shootIntervall = e.getEnemyTransition().get(i).shootIntervall;

                if (aiRequired) {
                    enemyEntity.addComponent(new EnemyShuttleAIComponent(targetEntity, projectileTexture,
                     shootIntervall), EnemyShuttleAIComponent.class);
                }else {
                    enemyEntity.addComponent(new SimpleFollowAIMovementComponent(targetEntity),
                            SimpleFollowAIMovementComponent.class);
                }

                enemyEntity.addComponent(new HPComponent(currentHealth, maxHealth));
                enemyEntity.getComponent(HPComponent.class).addDeathListener(listener);
                enemyEntity.addComponent(new MoveComponent(speed), MoveComponent.class);
                enemyEntity.addComponent(new RewardComponent(scoreByDeath), RewardComponent.class);
                System.out.println("Spawn: " + i);
                return enemyEntity;
            }
        }
        return null;
    }

    private static Entity createEnemyShuttle(EntityManager ecs, float x, float y, Texture texture) {
        // create new entity
        Entity enemyEntity = new Entity(ecs);

        // every entity requires an position component
        enemyEntity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        // add texture component to draw texture
        enemyEntity.addComponent(new DrawTextureComponent(texture, texture.getWidth() / 2, texture.getHeight() / 2),
                DrawTextureComponent.class);

        // add component to rotate shuttle dependent on move direction
        enemyEntity.addComponent(new MoveDependentDrawRotationComponent(), MoveDependentDrawRotationComponent.class);

        // add collision component, so player can collide with other space
        // shuttles or meteorites
        enemyEntity.addComponent(new CollisionComponent(), CollisionComponent.class);
        // enemyEntity.getComponent(CollisionComponent.class).setHullShape(new
        // CCircle(texture.getWidth() / 2, texture.getHeight() / 2,
        // texture.getWidth() / 2));
        enemyEntity.getComponent(CollisionComponent.class)
                .addInnerShape(new CCircle(texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth() / 2));

        // add component to draw HP
        enemyEntity.addComponent(new DrawHPBarComponent(texture.getWidth() / 3, 10, texture.getWidth() / 3, 5f),
                DrawHPBarComponent.class);

        // add component to remove entity on death
        enemyEntity.addComponent(new RemoveOnDeathComponent(), RemoveOnDeathComponent.class);

        // add component to reduce HP on collision, reduce 100 points on every
        // collision
        enemyEntity.addComponent(new GetDamagedOnCollisionComponent(100), GetDamagedOnCollisionComponent.class);

        //add component, so enemy shuttle can also use power ups
        enemyEntity.addComponent(new CanUsePowerUpsComponent(), CanUsePowerUpsComponent.class);

        return enemyEntity;
    }

}
