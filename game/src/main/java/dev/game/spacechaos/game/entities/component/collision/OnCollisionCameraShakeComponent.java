package dev.game.spacechaos.game.entities.component.collision;

import dev.game.spacechaos.engine.camera.impl.Shake1CameraModification;
import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.annotation.RequiredComponents;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.exception.RequiredComponentNotFoundException;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.game.entities.component.combat.AttackComponent;

/**
 * Created by Justin on 12.04.2017.
 */
@RequiredComponents(components = CollisionComponent.class)
public class OnCollisionCameraShakeComponent extends BaseComponent implements CollisionListener {

    protected CollisionComponent collisionComponent = null;

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
        /*if (entity.getComponent(AttackComponent.class) != null || otherEntity.getComponent(AttackComponent.class) != null) {
            //dont shake camera, if projectile collides
            return;
        }*/

        if (otherEntity.getComponent(AvoidCollisionCameraShakeComponent.class) != null) {
            //dont shake camera
            return;
        }

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
