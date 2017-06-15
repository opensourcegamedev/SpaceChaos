package dev.game.spacechaos.engine.entity.component.camera;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.annotation.InjectComponent;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public class FollowCameraComponent extends BaseComponent implements IUpdateComponent {

    protected float lerp = 0.5f;
    @InjectComponent(nullable = false)
    protected PositionComponent entityPosition = null;

    @Override
    public void onInit(BaseGame game, Entity entity) {
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

        float targetX = entityPosition.getMiddleX() - (game.getViewportWidth() / 2);
        float targetY = entityPosition.getMiddleY() - (game.getViewportHeight() / 2);

        // move camera
        float deltaX = targetX - currentCameraMiddleX;
        float deltaY = targetY - currentCameraMiddleY;

        if (Math.abs(deltaX) <= 10) {
            deltaX = 0;
        }

        if (Math.abs(deltaY) <= 10) {
            deltaY = 0;
        }

        float newCameraX = game.getCamera().getX() + (deltaX * lerp * dt);
        float newCameraY = game.getCamera().getY() + (deltaY * lerp * dt);

        if (Math.abs(deltaX) <= 1) {
            newCameraX = targetX;
        } else if (Math.abs(deltaX) <= 10) {
            newCameraX = game.getCamera().getX() + deltaX;
        }

        if (Math.abs(deltaY) <= 1) {
            newCameraY = targetY;
        } else if (Math.abs(deltaY) <= 10) {
            newCameraY = game.getCamera().getY() + deltaY;
        }

        // game.getCamera().setPosition(newCameraX, newCameraY);
        game.getCamera().setPosition(entityPosition.getX() - (game.getViewportWidth() / 2),
                entityPosition.getY() - (game.getViewportHeight() / 2));
    }

    @Override
    public ECSPriority getUpdateOrder() {
        // camera should be updated after all entities
        return ECSPriority.VERY_LOW;
    }

}
