package dev.game.spacechaos.engine.entity.component.collision;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Created by Justin on 13.04.2017.
 */
public class AvoidRemoveOnCollisionComponent extends BaseComponent {

    protected Entity ownerEntity = null;

    public AvoidRemoveOnCollisionComponent(Entity ownerEntity) {
        this.ownerEntity = ownerEntity;
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        //
    }

    public Entity getOwnerEntity() {
        return this.ownerEntity;
    }

}
