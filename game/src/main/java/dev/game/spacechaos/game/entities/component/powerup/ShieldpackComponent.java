package dev.game.spacechaos.game.entities.component.powerup;

import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.annotation.InjectComponent;
import dev.game.spacechaos.game.entities.component.combat.ShieldComponent;

/**
 * Adds an shield pack effect to the player.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.3
 */

@InjectComponent
public class ShieldpackComponent extends BasePowerupComponent {

    private float shieldHP;

    public ShieldpackComponent(float shieldHP) {
        this.shieldHP = shieldHP;
    }

    @Override
    protected boolean onEffect(Entity affectedEntity) {
        ShieldComponent shieldComponent = affectedEntity.getComponent(ShieldComponent.class);

        if (shieldComponent != null) {
            shieldComponent.addShieldHP(shieldHP);
            return true;
        }
        return false;
    }
}
