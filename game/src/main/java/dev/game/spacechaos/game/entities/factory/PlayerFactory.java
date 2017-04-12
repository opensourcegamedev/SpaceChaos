package dev.game.spacechaos.game.entities.factory;

import com.badlogic.gdx.graphics.Texture;
import dev.game.spacechaos.engine.collision.shape.CCircle;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.camera.SmoothFollowCameraComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.entity.component.collision.OnCollisionCameraShakeComponent;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;
import dev.game.spacechaos.engine.entity.component.draw.MouseDependentDrawRotationAngle;
import dev.game.spacechaos.engine.entity.component.movement.MouseDependentMovementComponent;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;

/**
 * Created by Justin on 07.04.2017.
 */
public class PlayerFactory {

    public static Entity createPlayer (EntityManager ecs, float x, float y, Texture texture) {
        //create new entity
        Entity player = new Entity(ecs);

        //every entity requires an position component
        player.addComponent(new PositionComponent(x, y), PositionComponent.class);

        //add texture component to draw texture
        player.addComponent(new DrawTextureComponent(texture, texture.getWidth() / 2, texture.getHeight() / 2), DrawTextureComponent.class);

        //add component to rotate shuttle relative to mouse position
        player.addComponent(new MouseDependentDrawRotationAngle(), MouseDependentDrawRotationAngle.class);

        //add component to move entity
        player.addComponent(new MoveComponent(1, 0, 1.5f), MoveComponent.class);

        //add component to move entity dependent on mouse position
        player.addComponent(new MouseDependentMovementComponent(texture.getWidth() / 2, texture.getHeight() / 2), MouseDependentMovementComponent.class);

        //add collision component, so player can collide with other space shuttles or meteorits
        player.addComponent(new CollisionComponent(), CollisionComponent.class);
        //player.getComponent(CollisionComponent.class).setHullShape(new CCircle(texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth() / 2));
        player.getComponent(CollisionComponent.class).addInnerShape(new CCircle(texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth() / 2));

        //add component to shake camera, if player collides with other objects
        player.addComponent(new OnCollisionCameraShakeComponent(), OnCollisionCameraShakeComponent.class);

        //add follow camera component, so camera is following player
        player.addComponent(new SmoothFollowCameraComponent(), SmoothFollowCameraComponent.class);

        return player;
    }

}
