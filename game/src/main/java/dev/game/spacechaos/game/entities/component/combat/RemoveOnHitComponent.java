package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.listener.HPHitListener;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.game.entities.listener.EntityRemoveListener;

/**
 * Created by Justin on 13.04.2017.
 */
public class RemoveOnHitComponent extends BaseComponent implements HPHitListener {

    protected HPComponent hpComponent = null;

    protected EntityRemoveListener listener = null;

    public RemoveOnHitComponent (EntityRemoveListener listener) {
        this.listener = listener;
    }

    public RemoveOnHitComponent () {
        //
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        this.hpComponent = entity.getComponent(HPComponent.class);

        if (this.hpComponent == null) {
            throw new IllegalStateException("entity doesnt have an HPComponent.");
        }

        this.hpComponent.addHitListener(this);
    }

    @Override
    public void onHit(float oldValue, float newValue, float maxHP) {
        if (this.listener != null) {
            this.listener.beforeRemove(this.entity);
        }

        //remove entity
        game.runOnUIThread(() -> {
            //remove entity from ecs
            this.entity.getEntityComponentSystem().removeEntity(this.entity);
        });
    }
}
