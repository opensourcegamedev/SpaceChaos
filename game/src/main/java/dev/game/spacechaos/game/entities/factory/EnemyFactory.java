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
import dev.game.spacechaos.game.entities.component.combat.HPComponent;
import dev.game.spacechaos.game.entities.component.combat.ReduceHPOnCollisionComponent;
import dev.game.spacechaos.game.entities.component.combat.RemoveOnHitComponent;
import dev.game.spacechaos.game.entities.component.draw.DrawHPBarComponent;
import dev.game.spacechaos.game.entities.listener.EntityRemoveListener;

/**
 * Created by Justin on 07.04.2017.
 */
public class EnemyFactory {

    public static Entity createEnemyShuttle (EntityManager ecs, float x, float y, Texture texture, Entity targetEntity, Texture projectileTexture, EntityRemoveListener listener) {
        //create new entity
        Entity enemyEntity = new Entity(ecs);

        //every entity requires an position component
        enemyEntity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add texture component to draw texture
        enemyEntity.addComponent(new DrawTextureComponent(texture, texture.getWidth() / 2, texture.getHeight() / 2), DrawTextureComponent.class);

        //add component to move entity
        enemyEntity.addComponent(new MoveComponent(1.5f), MoveComponent.class);

        //add component to rotate shuttle dependent on move direction
        enemyEntity.addComponent(new MoveDependentDrawRotationComponent(), MoveDependentDrawRotationComponent.class);

        //add component to follow player
        //enemyEntity.addComponent(new SimpleFollowAIMovementComponent(targetEntity), SimpleFollowAIMovementComponent.class);

        //add AI
        enemyEntity.addComponent(new EnemyShuttleAIComponent(targetEntity, projectileTexture), EnemyShuttleAIComponent.class);

        //add collision component, so player can collide with other space shuttles or meteorits
        enemyEntity.addComponent(new CollisionComponent(), CollisionComponent.class);
        //enemyEntity.getComponent(CollisionComponent.class).setHullShape(new CCircle(texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth() / 2));
        enemyEntity.getComponent(CollisionComponent.class).addInnerShape(new CCircle(texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth() / 2));

        //add component for HP
        enemyEntity.addComponent(new HPComponent(1000, 1000));

        //add component to draw HP
        enemyEntity.addComponent(new DrawHPBarComponent(texture.getWidth() / 3, 10, texture.getWidth() / 3, 5f), DrawHPBarComponent.class);

        //add component to remove entity on hit
        enemyEntity.addComponent(new RemoveOnHitComponent(listener), RemoveOnHitComponent.class);

        //add component to reduce HP on collision, reduce 100 points on every collision
        enemyEntity.addComponent(new ReduceHPOnCollisionComponent(100), ReduceHPOnCollisionComponent.class);

        return enemyEntity;
    }

}
