package dev.game.spacechaos.engine.collision.listener;

import dev.game.spacechaos.engine.entity.Entity;

/**
 * Created by Justin on 12.04.2017.
 */
public interface CollisionListener {

    /**
    * on enter collision
     *
     * @param entity entity which collides with other entity
     * @param otherEntity other entity
    */
    public void onEnter (Entity entity, Entity otherEntity);

    /**
     * will be called, if entity is already in collision
     *
     * @param entity entity which collides with other entity
     * @param otherEntity other entity
     */
    public void onCollision (Entity entity, Entity otherEntity);

    /**
     * on exit collision (no colliding anymore)
     *
     * @param entity entity which collides with other entity
     * @param otherEntity other entity
     */
    public void onExit (Entity entity, Entity otherEntity);

}
