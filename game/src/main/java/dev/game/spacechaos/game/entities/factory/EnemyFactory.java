package dev.game.spacechaos.game.entities.factory;

import com.badlogic.gdx.graphics.Texture;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.ai.SimpleFollowAIMovement;
import dev.game.spacechaos.engine.entity.component.camera.SmoothFollowCameraComponent;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;
import dev.game.spacechaos.engine.entity.component.draw.MoveDependentDrawRotationComponent;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;

/**
 * Created by Justin on 07.04.2017.
 */
public class EnemyFactory {

    public static Entity createEnemyShuttle (EntityManager ecs, float x, float y, Texture texture, Entity targetEntity) {
        //create new entity
        Entity enemyEntity = new Entity(ecs);

        //every entity requires an position component
        enemyEntity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add texture component to draw texture
        enemyEntity.addComponent(new DrawTextureComponent(texture, texture.getWidth() / 2, texture.getHeight() / 2), DrawTextureComponent.class);

        //add component to move entity
        enemyEntity.addComponent(new MoveComponent(1f), MoveComponent.class);

        //add component to rotate shuttle dependent on move direction
        enemyEntity.addComponent(new MoveDependentDrawRotationComponent(), MoveDependentDrawRotationComponent.class);

        //add component to follow player
        enemyEntity.addComponent(new SimpleFollowAIMovement(targetEntity), SimpleFollowAIMovement.class);

        return enemyEntity;
    }

}
