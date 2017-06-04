package dev.game.spacechaos.engine.entity.component.movement;

import com.badlogic.gdx.math.Vector2;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.annotation.RequiredComponents;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Enables an entity to move.
 * <p>
 * The movement is done via a {@linkplain #moveDirection direction vector}.
 * Requires a {@linkplain PositionComponent position component}.
 */
public class MoveComponent extends BaseComponent implements IUpdateComponent {

	protected PositionComponent positionComponent = null;

	protected Vector2 moveDirection = new Vector2(0, 0);
	protected float speed = 1;

	/**
	 * Indicates if the entity is currently moving.
	 */
	protected boolean isMoving = false;

	// temporary vector for calculations
	protected Vector2 tmpVector = new Vector2();

	/**
	 * Creates a movement component.
	 * 
	 * @param moveDirectionX
	 *            The X value of the movement direction vector.
	 * @param moveDirectionY
	 *            The Y value of the movement direction vector.
	 * @param speed
	 *            The movement speed (is used to scale the vector). Has to be
	 *            greater than 0.
	 */
	public MoveComponent(float moveDirectionX, float moveDirectionY, float speed) {
		if (speed < 0) {
			throw new IllegalArgumentException("speed has to be >= 0.");
		}

		moveDirection.set(moveDirectionX, moveDirectionY);
		moveDirection.nor();
		this.speed = speed;
	}

	/**
	 * Creates a movement component with the direction vector (0, 0).
	 * 
	 * @param speed
	 *            The movement speed (is used to scale the vector). Has to be
	 *            greater than 0.
	 */
	public MoveComponent(float speed) {
		this(0, 0, speed);
	}

	@Override
	protected void onInit(BaseGame game, Entity entity) {
		// get required components
		this.positionComponent = entity.getComponent(PositionComponent.class);

		if (this.positionComponent == null) {
			throw new IllegalStateException("entity doesnt have a PositionComponent.");
		}
	}

	@Override
	public void update(BaseGame game, GameTime time) {
		/*
		 * if (moveDirection.x == 0 && moveDirection.y == 0) { //set flag
		 * isMoving = false; } else { //set flag isMoving = true; }
		 */

		if (!isMoving) {
			// we dont have to move entity
			return;
		}

		float dt = time.getDeltaTime();

		// set vector values to temporary vector
		tmpVector.set(moveDirection.x, moveDirection.y);

		// normalize and scale move vector
		tmpVector.scl(this.speed * dt * 100f);

		// move entity
		positionComponent.move(tmpVector.x, tmpVector.y);
	}

	@Override
	public ECSPriority getUpdateOrder() {
		return ECSPriority.LOW;
	}

	/**
	 * @return Returns if the entity is currently moving.
	 */
	public boolean isMoving() {
		return this.isMoving;
	}

	/**
	 * Sets the {@linkplain #isMoving moving flag}.
	 * 
	 * @param moving
	 *            The value. If set to 0, the direction is set to (0, 0) and
	 *            thus the movement stopped.
	 */
	public void setMoving(boolean moving) {
		this.isMoving = moving;

		if (!moving) {
			moveDirection.set(0, 0);
		}
	}

	public Vector2 getMoveDirection() {
		return this.moveDirection;
	}

	public void setMoveDirection(float x, float y) {
		this.moveDirection.set(x, y);

		if (x == 0 && y == 0) {
			this.isMoving = false;
		} else {
			this.moveDirection.nor();
			this.isMoving = true;
		}
	}

	public float getSpeed() {
		return this.speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
