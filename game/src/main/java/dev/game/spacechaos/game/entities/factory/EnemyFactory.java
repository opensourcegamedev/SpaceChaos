package dev.game.spacechaos.game.entities.factory;

import com.badlogic.gdx.graphics.Texture;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.camera.SmoothFollowCameraComponent;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;

/**
 * Created by Justin on 07.04.2017.
 */
public class EnemyFactory {

    public static Entity createEnemyShuttle (EntityManager ecs, float x, float y, Texture texture) {
        //create new entity
        Entity enemyEntity = new Entity(ecs);

        //every entity requires an position component
        enemyEntity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add texture component to draw texture
        enemyEntity.addComponent(new DrawTextureComponent(texture, texture.getWidth() / 2, texture.getHeight() / 2), DrawTextureComponent.class);

        return enemyEntity;
    }

}
