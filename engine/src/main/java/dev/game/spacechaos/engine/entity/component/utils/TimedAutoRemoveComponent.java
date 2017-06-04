package dev.game.spacechaos.engine.entity.component.utils;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Auto remove entity after an given TTL time.
 *
 * Created by Justin on 09.03.2017.
 */
public class TimedAutoRemoveComponent extends BaseComponent implements IUpdateComponent {

    protected long startTime = 0;
    protected long ttl = 0;

    public TimedAutoRemoveComponent(long TTL) {
        this.ttl = TTL;
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        //
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        if (startTime == 0) {
            startTime = time.getTime();
        }

        // calculate elapsed time
        long elapsed = time.getTime() - startTime;

        if (elapsed > this.ttl) {
            // auto remove entity on next gameloop cycle
            game.runOnUIThread(() -> {
                // remove entity from ecs
                this.entity.getEntityComponentSystem().removeEntity(this.entity);
            });
        }
    }

    @Override
    public ECSPriority getUpdateOrder() {
        return ECSPriority.NORMAL;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getTTL() {
        return this.ttl;
    }

    public void setTTL(long ttl) {
        this.ttl = ttl;
    }

}
