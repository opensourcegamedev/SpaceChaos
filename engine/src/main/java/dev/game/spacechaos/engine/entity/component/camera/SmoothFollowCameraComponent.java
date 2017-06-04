package dev.game.spacechaos.engine.entity.component.camera;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 09.03.2017.
 */
public class SmoothFollowCameraComponent extends BaseComponent implements IUpdateComponent {

    protected float lerp = 0.1f;
    protected PositionComponent entityPosition = null;

    @Override
    public void onInit(BaseGame game, Entity entity) {
        this.entityPosition = entity.getComponent(PositionComponent.class);

        if (this.entityPosition == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        float dt = time.getDeltaTime();

        // get screen resolution
        float screenWidth = game.getViewportWidth();
        float screenHeight = game.getViewportHeight();

        // calculate camera middle
        float currentCameraMiddleX = game.getCamera().getX() + (screenWidth / 2);
        float currentCameraMiddleY = game.getCamera().getY() + (screenHeight / 2);

        float targetX = entityPosition.getX();// entityPosition.getMiddleX();
        float targetY = entityPosition.getY();// entityPosition.getMiddleY();

        // System.out.println("currentCameraMiddleX: " + currentCameraMiddleX +
        // ", targetX: " + targetX);
        // System.out.println("currentCameraMiddleY: " + currentCameraMiddleY +
        // ", targetY: " + targetY);

        // move camera
        float deltaX = targetX - currentCameraMiddleX;
        float deltaY = targetY - currentCameraMiddleY;

        // float newCameraX = targetX;
        // float newCameraY = targetY;

        float newCameraX = currentCameraMiddleX + (deltaX * lerp);
        float newCameraY = currentCameraMiddleY + (deltaY * lerp);

        // game.getCamera().setPosition(newCameraX, newCameraY);
        game.getCamera().setPosition(newCameraX - (game.getViewportWidth() / 2),
                newCameraY - (game.getViewportHeight() / 2));
    }

    @Override
    public ECSPriority getUpdateOrder() {
        // camera should be updated after all entities
        return ECSPriority.VERY_LOW;
    }

    public float getLerp() {
        return this.lerp;
    }

    public void setLerp(float lerp) {
        if (lerp < 0) {
            throw new IllegalStateException("lerp cannot be lesser than 0.");
        }

        if (lerp > 1) {
            throw new IllegalStateException("lerp cannot be greater than 1.");
        }

        this.lerp = lerp;
    }

}
