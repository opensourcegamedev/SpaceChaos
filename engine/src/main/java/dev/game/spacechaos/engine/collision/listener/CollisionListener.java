package dev.game.spacechaos.engine.collision.listener;

import dev.game.spacechaos.engine.entity.Entity;

/**
 * The listener interface for receiving collision events.
 * 
 * @since 1.0.0-PreAlpha
 */
public interface CollisionListener {

	/**
	 * Invoked when an entity enters the collision zone.
	 *
	 * @param entity
	 *            The colliding entity.
	 * @param otherEntity
	 *            The other entity.
	 */
	public void onEnter(Entity entity, Entity otherEntity);

	/**
	 * Invoked when an entity enters the collision zone.
	 *
	 * @param entity
	 *            The colliding entity.
	 * @param otherEntity
	 *            The other entity.
	 */
	public void onStay(Entity entity, Entity otherEntity);

	/**
	 * Invoked when an entity enters the collision zone.
	 *
	 * @param entity
	 *            The colliding entity.
	 * @param otherEntity
	 *            The other entity.
	 */
	public void onExit(Entity entity, Entity otherEntity);

}
