package dev.game.spacechaos.game.entities.factory;

import com.badlogic.gdx.graphics.Texture;
import dev.game.spacechaos.engine.collision.shape.CCircle;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.ai.RandomWalkComponent;
import dev.game.spacechaos.engine.entity.component.ai.SimpleFollowAIMovementComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
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
        entity.getComponent(DrawTextureComponent.class).setScale(2);

        //add component to move entity
        entity.addComponent(new MoveComponent(1f), MoveComponent.class);

        //add component so meteorits move randomly
        entity.addComponent(new RandomWalkComponent(), RandomWalkComponent.class);

        //add collision component, so player can collide with meteorits
        entity.addComponent(new CollisionComponent(), CollisionComponent.class);
        entity.getComponent(CollisionComponent.class).addInnerShape(new CCircle(texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth() / 2));

        //add component to rotate entity
        entity.addComponent(new SimpleRotationComponent(1f), SimpleRotationComponent.class);

        return entity;
    }

}
