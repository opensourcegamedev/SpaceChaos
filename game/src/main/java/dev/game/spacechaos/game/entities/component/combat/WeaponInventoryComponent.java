package dev.game.spacechaos.game.entities.component.combat;

import java.util.ArrayList;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.game.weapons.BaseWeapon;

/**
 * Is added to entities that can use weapons (shoot).
 * <p>
 * Handles one weapon on the left side and multiple weapons on the right side.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.2-PreAlpha
 */
public class WeaponInventoryComponent extends BaseComponent {

    private BaseWeapon leftWeapon;
    private ArrayList<BaseWeapon> rightWeapons;
    private int rightWeaponIndex = 0;
    private boolean canShotRightFirstSlot = false;

    // The right weapon can later be replaced by a list, so you can switch your
    // equipped weapon (e.g. via scrolling)
    public WeaponInventoryComponent(BaseWeapon leftWeapon, BaseWeapon rightWeapon) {
        this.leftWeapon = leftWeapon;
        this.rightWeapons = new ArrayList<>();
        rightWeapons.add(rightWeapon);
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
    }

    public int getCurrentAmmoForLeftWeapon() {
        return leftWeapon.getCurrentAmmo();
    }

    public int getCurrentAmmoForRightWeapon() {
        return rightWeapons.get(rightWeaponIndex).getCurrentAmmo();
    }

    public void subAmmoForLeftWeapon() {
        leftWeapon.subAmmo();
    }

    public void subAmmoForRightWeapon() {
        rightWeapons.get(rightWeaponIndex).subAmmo();
    }

    public void addAmmoToLeftWeapon(int add) {
        leftWeapon.addAmmo(add);
    }

    /**
     * Adds ammo to the currently selected weapon on the right side.
     * 
     * @param add
     *            The amount to add.
     */
    public void addAmmoToRightWeapon(int add) {
        rightWeapons.get(rightWeaponIndex).addAmmo(add);
    }

    public boolean canShootNowWithLeftWeapon() {
        return leftWeapon.canShootLeftNow();
    }

    public boolean canShootNowWithRightWeapon() {
        return rightWeapons.get(rightWeaponIndex).canShootRightNow();
    }

    public void setLastShotWithLeftWeapon() {
        leftWeapon.setLastShot();
    }

    public void setLastShotWithRightWeapon() {

        if (canShotRightFirstSlot) {
            rightWeapons.get(rightWeaponIndex).setLastShotRightFirstSlot();
            this.canShotRightFirstSlot = false;
        } else {
            rightWeapons.get(rightWeaponIndex).setLastShotRightSecondSlot();
            this.canShotRightFirstSlot = true;
        }
    }

}
