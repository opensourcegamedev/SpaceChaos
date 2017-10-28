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
import dev.game.spacechaos.game.entities.component.ai.EnemyShuttleAttributeConfiguration;
import dev.game.spacechaos.game.entities.component.collision.DealDamageOnCollisionComponent;
import dev.game.spacechaos.game.entities.component.combat.HPComponent;
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

    public static Entity createEnemyShuttle(EntityManager ecs, float x, float y, Texture texture, Entity targetEntity,
                                            Texture projectileTexture, HPDeathListener hpDeathListener, List<Texture> enemies) {

        Entity enemyEntity = createEnemyShuttle(ecs, x, y, texture);

        for (int i = 0; i < enemies.size(); i++) {
            if (texture == enemies.get(i)) {

                EnemyShuttleAttributeConfiguration e = new EnemyShuttleAttributeConfiguration();
                e.initializeEnemyAttributes();

                // add component for HP
                int currentHealth = e.getEnemyShuttleAttributeList().get(i).getCurrentHealth();
                int maxHealth = e.getEnemyShuttleAttributeList().get(i).getMaxHealth();
                enemyEntity.addComponent(new HPComponent(currentHealth, maxHealth));
                enemyEntity.getComponent(HPComponent.class).addDeathListener(hpDeathListener);

                // add component for score reward
                int scoreByDeath = e.getEnemyShuttleAttributeList().get(i).getScoreByDeath();
                enemyEntity.addComponent(new RewardComponent(scoreByDeath), RewardComponent.class);

                // add component to move entity
                float speed = e.getEnemyShuttleAttributeList().get(i).getSpeed();
                enemyEntity.addComponent(new MoveComponent(speed), MoveComponent.class);

                // add AI component
                boolean aiRequired = e.getEnemyShuttleAttributeList().get(i).getAiRequired();
                if(aiRequired) {
                    // add AI
                    int shootInterval = e.getEnemyShuttleAttributeList().get(i).getShootInterval();
                    enemyEntity.addComponent(new EnemyShuttleAIComponent(targetEntity, projectileTexture, shootInterval),
                            EnemyShuttleAIComponent.class);
                } else {
                    enemyEntity.addComponent(new SimpleFollowAIMovementComponent(targetEntity), SimpleFollowAIMovementComponent.class);
                }
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

        // add component to deal damage upon collision
        enemyEntity.addComponent(new DealDamageOnCollisionComponent(75, false, null, true, 525));

        // add collision component, so player can collide with enemy shuttles
        enemyEntity.addComponent(new CollisionComponent(), CollisionComponent.class);
        enemyEntity.getComponent(CollisionComponent.class)
                .addInnerShape(new CCircle(texture.getWidth() / 2, texture.getHeight() / 2, texture.getHeight() / 2));

        // add component to draw HP
        enemyEntity.addComponent(new DrawHPBarComponent(texture.getWidth() / 3, 10, texture.getWidth() / 3, 5f),
                DrawHPBarComponent.class);

        // add component to remove entity on death
        enemyEntity.addComponent(new RemoveOnDeathComponent(), RemoveOnDeathComponent.class);

        // add component, so enemy shuttle can also use power ups
        enemyEntity.addComponent(new CanUsePowerUpsComponent(), CanUsePowerUpsComponent.class);

        return enemyEntity;
    }

}
