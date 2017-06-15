package dev.game.spacechaos.game.entities.component.collision;

import dev.game.spacechaos.engine.camera.impl.Shake1CameraModification;
import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.annotation.InjectComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Shakes the player's camera on colliding with specific entities.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class OnCollisionCameraShakeComponent extends BaseComponent implements CollisionListener {

    @InjectComponent(nullable = false)
    private CollisionComponent collisionComponent;

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        collisionComponent.addCollisionListener(this);
    }

    @Override
    public void onEnter(Entity entity, Entity otherEntity) {
        /*
         * if (entity.getComponent(AttackComponent.class) != null ||
         * otherEntity.getComponent(AttackComponent.class) != null) { //don't
         * shake camera, if projectile collides return; }
         */

        if (otherEntity.getComponent(AvoidCollisionCameraShakeComponent.class) != null) {
            // don't shake camera
            return;
        }

        // shake camera
        game.getCamera().activateMod(Shake1CameraModification.class);
        game.getCamera().getMod(Shake1CameraModification.class).shake(10, 500);
    }

    @Override
    public void onStay(Entity entity, Entity otherEntity) {
        //
    }

    @Override
    public void onExit(Entity entity, Entity otherEntity) {
        //
    }
}
