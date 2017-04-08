package dev.game.spacechaos.game.entities.factory;

import com.badlogic.gdx.graphics.Texture;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.camera.SmoothFollowCameraComponent;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;
import dev.game.spacechaos.engine.entity.component.draw.MouseDependentDrawRotationAngle;

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

        //add follow camera component, so camera is following player
        player.addComponent(new SmoothFollowCameraComponent(), SmoothFollowCameraComponent.class);

        return player;
    }

}
