package dev.game.spacechaos.game.entities.component.powerup;

import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.annotation.InjectComponent;
import dev.game.spacechaos.game.entities.component.combat.HPComponent;

/**
 * Adds an health pack effect to entities.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.2-PreAlpha
 */
@InjectComponent
public class HealthpackComponent extends BasePowerupComponent {

    private float hp;

    public HealthpackComponent(float hp) {
        this.hp = hp;
    }

    @Override
    protected boolean onEffect(Entity affectedEntity) {
        HPComponent hpComp = affectedEntity.getComponent(HPComponent.class);

        if (hpComp != null) {
            hpComp.addHP(hp);
            return true;
        }

        return false;
    }

}
