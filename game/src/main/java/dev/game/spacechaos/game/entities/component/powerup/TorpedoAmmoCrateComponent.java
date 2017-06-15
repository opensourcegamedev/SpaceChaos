package dev.game.spacechaos.game.entities.component.powerup;

import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.annotation.InjectComponent;
import dev.game.spacechaos.game.entities.component.combat.WeaponInventoryComponent;

/**
 * Adds an ammo crate for torpedos effect to entities.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.2-PreAlpha
 */
@InjectComponent
public class TorpedoAmmoCrateComponent extends BasePowerupComponent {

    private int ammo;

    public TorpedoAmmoCrateComponent(int ammo) {
        this.ammo = ammo;
    }

    @Override
    protected boolean onEffect(Entity affectedEntity) {
        WeaponInventoryComponent weaponInvComp = affectedEntity.getComponent(WeaponInventoryComponent.class);

        if (weaponInvComp != null) {
            weaponInvComp.addAmmoToRightWeapon(ammo);
            return true;
        }

        return false;
    }

}
