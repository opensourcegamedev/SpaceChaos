package dev.game.spacechaos.engine.entity.component.collision;

import dev.game.spacechaos.engine.camera.impl.Shake1CameraModification;
import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.annotation.RequiredComponents;
import dev.game.spacechaos.engine.exception.RequiredComponentNotFoundException;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Created by Justin on 12.04.2017.
 */
@RequiredComponents(components = CollisionComponent.class)
public class OnCollisionCameraShakeComponent extends BaseComponent implements CollisionListener {

    protected CollisionComponent collisionComponent = null;

    protected int i = 0;

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        this.collisionComponent = entity.getComponent(CollisionComponent.class);

        if (this.collisionComponent == null) {
            throw new RequiredComponentNotFoundException("entity doesnt have an CollisionComponent.");
        }

        this.collisionComponent.addCollisionListener(this);
    }

    @Override
    public void onEnter(Entity entity, Entity otherEntity) {
        //shake camera
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
