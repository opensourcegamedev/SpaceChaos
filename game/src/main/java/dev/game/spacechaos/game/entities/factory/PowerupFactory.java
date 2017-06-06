package dev.game.spacechaos.game.entities.factory;

import com.badlogic.gdx.graphics.Texture;

import dev.game.spacechaos.engine.collision.shape.CCircle;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;
import dev.game.spacechaos.game.entities.component.powerup.HealthpackComponent;
import dev.game.spacechaos.game.entities.component.powerup.TorpedoAmmoCrateComponent;

/**
 * Creates different types of power ups.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.2-PreAlpha
 */
public class PowerupFactory {

    /**
     * Creates a new healthpack entity.
     *
     * @param ecs
     *            entity component system
     * @param x
     *            x start position
     * @param y
     *            y start position
     * @param texture
     *            texture
     * @return power up entity
     */
    public static Entity createHealthpack(EntityManager ecs, float x, float y, Texture texture) {
        // create new entity
        Entity powerupEntity = createPowerup(ecs, x, y, texture);
        powerupEntity.addComponent(new HealthpackComponent(1000));

        return powerupEntity;
    }

    /**
     * Creates a new ammo crate entity for torpedos.
     *
     * @param ecs
     *            entity component system
     * @param x
     *            x start position
     * @param y
     *            y start position
     * @param texture
     *            texture
     * @return power up entity
     */
    public static Entity createTorpedoAmmoCrate(EntityManager ecs, float x, float y, Texture texture) {
        // create new entity
        Entity powerupEntity = createPowerup(ecs, x, y, texture);
        powerupEntity.addComponent(new TorpedoAmmoCrateComponent(5));

        return powerupEntity;
    }

    /**
     * Creates a new power up entity.
     *
     * @param ecs
     *            entity component system
     * @param x
     *            x start position
     * @param y
     *            y start position
     * @param texture
     *            texture
     * @return power up entity
     */
    public static Entity createPowerup(EntityManager ecs, float x, float y, Texture texture) {
        // create new entity
        Entity powerupEntity = new Entity(ecs);

        // every entity requires a position component
        powerupEntity.addComponent(new PositionComponent(x, y), PositionComponent.class);

        // add texture component to draw texture
        powerupEntity.addComponent(new DrawTextureComponent(texture, texture.getWidth() / 2, texture.getHeight() / 2),
                DrawTextureComponent.class);

        // add collision component, so entity can collide with other space
        // shuttles or meteorites
        powerupEntity.addComponent(new CollisionComponent(), CollisionComponent.class);
        powerupEntity.getComponent(CollisionComponent.class)
                .addInnerShape(new CCircle(texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth() / 2));

        return powerupEntity;
    }

}
