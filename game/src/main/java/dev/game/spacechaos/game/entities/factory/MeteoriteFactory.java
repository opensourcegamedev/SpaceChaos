package dev.game.spacechaos.game.entities.factory;

import com.badlogic.gdx.graphics.Texture;

import dev.game.spacechaos.engine.collision.shape.CCircle;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.ai.RandomWalkComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;
import dev.game.spacechaos.engine.entity.component.draw.SimpleRotationComponent;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;
import dev.game.spacechaos.engine.utils.RandomUtils;
import dev.game.spacechaos.game.entities.component.combat.HPComponent;
import dev.game.spacechaos.game.entities.component.combat.RemoveOnHitComponent;

/**
 * Creating a new randomly moving entity representing a meteorite.
 *
 * @author SpaceChaos-Team (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @version 1.0.0-PreAlpha
 */
public class MeteoriteFactory {

	/**
	 * Creates a meteorite which moves around randomly.
	 * 
	 * @param ecs
	 *            The entity component system.
	 * @param x
	 *            The x-coordinate of the meteorite which is created.
	 * @param y
	 *            The y-coordinate of the meteorite which is created.
	 * @param texture
	 *            The texture of the meteorite.
	 * @return A meteorite-entity.
	 */
	public static Entity createMeteorite(EntityManager ecs, float x, float y, Texture texture) {
		//create new entity
		Entity entity = new Entity(ecs);

		//every entity requires an position component
		entity.addComponent(new PositionComponent(x, y), PositionComponent.class);

		//add texture component to draw texture
		entity.addComponent(new DrawTextureComponent(texture, texture.getWidth() / 2, texture.getHeight() / 2),
				DrawTextureComponent.class);
		entity.getComponent(DrawTextureComponent.class).setScale(1);

		//add component to move entity
		entity.addComponent(new MoveComponent(1f), MoveComponent.class);

		//add component so meteorites move randomly
		entity.addComponent(new RandomWalkComponent(), RandomWalkComponent.class);

		//add HP component, so player can destroy meteorites
		entity.addComponent(new HPComponent(1500, 1500));

		//add component to remove entity on hit
		entity.addComponent(new RemoveOnHitComponent(), RemoveOnHitComponent.class);

		//add collision component, so player can collide with meteorites
		entity.addComponent(new CollisionComponent(), CollisionComponent.class);
		entity.getComponent(CollisionComponent.class).addInnerShape(
				new CCircle(texture.getWidth() / 2, texture.getHeight() / 2, texture.getHeight() / 2));

		//add component to rotate entity
		entity.addComponent(new SimpleRotationComponent(RandomUtils.getRandomNumber(0, 8)/10F), SimpleRotationComponent.class);

		return entity;
	}

}
