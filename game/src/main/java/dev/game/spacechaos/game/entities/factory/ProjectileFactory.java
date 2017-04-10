package dev.game.spacechaos.game.entities.factory;

import com.badlogic.gdx.graphics.Texture;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;

/**
 * Created by Justin on 10.04.2017.
 */
public class ProjectileFactory {

    /**
    * create an new projectile entity
     *
     * @param ecs entity component system
     * @param x x start position of projectile
     * @param y y start position of projectile
     * @param texture texture of projectile
     * @param moveX x move direction (for example to move to left: x = -1, y = 0)
     * @param moveY y move direction (for example to move to left: x = -1, y = 0)
     * @param speed movement speed
     * @param ttl time to live of projectile (after this time in milliseconds the entity will removed automatically)
     *
     * @return projectile entity
    */
    public static Entity createProjectile (EntityManager ecs, float x, float y, Texture texture, float moveX, float moveY, float speed, float ttl) {
        throw new UnsupportedOperationException("method isnt implemented yet.");
    }

}
