package dev.game.spacechaos.game.entities.factory;

import com.badlogic.gdx.graphics.Texture;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.ai.SimpleFollowAIMovementComponent;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;
import dev.game.spacechaos.engine.entity.component.draw.MoveDependentDrawRotationComponent;
import dev.game.spacechaos.engine.entity.component.draw.SimpleRotationComponent;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;

/**
 * Created by Justin on 07.04.2017.
 */
public class MeteoritFactory {

    public static Entity createMeteorit (EntityManager ecs, float x, float y, Texture texture) {
        //create new entity
        Entity entity = new Entity(ecs);

        //every entity requires an position component
        entity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add texture component to draw texture
        entity.addComponent(new DrawTextureComponent(texture, texture.getWidth() / 2, texture.getHeight() / 2), DrawTextureComponent.class);
        entity.getComponent(DrawTextureComponent.class).setScale(5);

        //add component to move entity
        entity.addComponent(new MoveComponent(1f), MoveComponent.class);

        //add component to rotate entity
        entity.addComponent(new SimpleRotationComponent(1f), SimpleRotationComponent.class);

        return entity;
    }

}
