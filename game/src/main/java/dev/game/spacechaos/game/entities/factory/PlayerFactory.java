package dev.game.spacechaos.game.entities.factory;

import com.badlogic.gdx.graphics.Texture;

import dev.game.spacechaos.engine.collision.shape.CCircle;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.camera.SmoothFollowCameraComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;
import dev.game.spacechaos.engine.entity.component.draw.MouseDependentDrawRotationAngle;
import dev.game.spacechaos.engine.entity.component.movement.MouseDependentMovementComponent;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;
import dev.game.spacechaos.engine.entity.component.sound.OnCollisionPlaySoundComponent;
import dev.game.spacechaos.engine.entity.listener.HPDeathListener;
import dev.game.spacechaos.game.entities.component.collision.OnCollisionCameraShakeComponent;
import dev.game.spacechaos.game.entities.component.combat.HPComponent;
import dev.game.spacechaos.game.entities.component.combat.ReduceHPOnCollisionComponent;
import dev.game.spacechaos.game.entities.component.combat.ScoreComponent;
import dev.game.spacechaos.game.entities.component.draw.DrawHPBarComponent;

/**
 * Creates a new player entity.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class PlayerFactory {

    public static Entity createPlayer(EntityManager ecs, float x, float y, Texture texture,
            HPDeathListener hpDeathListener) {
        // create new entity
        Entity player = new Entity(ecs);

        // every entity requires an position component
        player.addComponent(new PositionComponent(x, y), PositionComponent.class);

        // add texture component to draw texture
        player.addComponent(new DrawTextureComponent(texture, texture.getWidth() / 2, texture.getHeight() / 2),
                DrawTextureComponent.class);

        // add component to rotate shuttle relative to mouse position
        player.addComponent(new MouseDependentDrawRotationAngle(), MouseDependentDrawRotationAngle.class);

        // add component to move entity
        player.addComponent(new MoveComponent(1, 0, 1.5f), MoveComponent.class);

        // add component to move entity dependent on mouse position
        player.addComponent(new MouseDependentMovementComponent(texture.getWidth() / 2, texture.getHeight() / 2),
                MouseDependentMovementComponent.class);

        // add collision component, so player can collide with other space
        // shuttles or meteorites
        player.addComponent(new CollisionComponent(), CollisionComponent.class);
        player.getComponent(CollisionComponent.class)
                .addInnerShape(new CCircle(texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth() / 2));

        // add component to shake camera, if player collides with other objects
        player.addComponent(new OnCollisionCameraShakeComponent(), OnCollisionCameraShakeComponent.class);

        // add component to play a sound, if the player collides
        player.addComponent(new OnCollisionPlaySoundComponent("./data/sound/explosions1/explodemini.wav", 0.4f),
                OnCollisionPlaySoundComponent.class);

        // add component for HP
        player.addComponent(new HPComponent(1000, 1000));
        player.getComponent(HPComponent.class).addDeathListener(hpDeathListener);

        // add component to draw HP
        player.addComponent(new DrawHPBarComponent(texture.getWidth() / 3, 10, texture.getWidth() / 3, 5f),
                DrawHPBarComponent.class);

        // add component to reduce HP on collision, reduce 100 points on every
        // collision
        player.addComponent(new ReduceHPOnCollisionComponent(100, 3000l), ReduceHPOnCollisionComponent.class);

        // add follow camera component, so camera is following player
        player.addComponent(new SmoothFollowCameraComponent(), SmoothFollowCameraComponent.class);

        // add component for score
        player.addComponent(new ScoreComponent());

        return player;
    }

}
