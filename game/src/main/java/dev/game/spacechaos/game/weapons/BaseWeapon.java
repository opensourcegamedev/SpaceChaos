package dev.game.spacechaos.game.weapons;

/**
 * Handles a weapon that can be shot.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.2-PreAlpha
 */
public class BaseWeapon {

    private long lastShot = 0;
    private long lastShotRightFirstSlot = 0;
    private long lastShotRightSecondSlot = 0;

    private int currentAmmo;
    private int maxAmmo;

    //cooldown of right and left weapon
    private int cooldownLeft;
    private int cooldownRight;

    public BaseWeapon(int cooldownLeft, int cooldownRight, int ammo, int maxAmmo) {
        this.cooldownLeft = cooldownLeft;
        this.currentAmmo = ammo;
        this.maxAmmo = maxAmmo;
        this.cooldownRight = cooldownRight;
    }

    public BaseWeapon(int cooldown, int ammo, int maxAmmo) {
        this(cooldown, 0, ammo, maxAmmo);
    }

    public BaseWeapon(int cooldown, int ammo) {
        this(cooldown, ammo, ammo);
    }

    public void setCurrentAmmo(int ammo) {
        if (ammo < 0) {
            this.currentAmmo = 0;
        } else if (ammo > maxAmmo) {
            this.currentAmmo = maxAmmo;
        } else {
            this.currentAmmo = ammo;
        }
    }

    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public void subAmmo() {
        setCurrentAmmo(getCurrentAmmo() - 1);
    }

    public void addAmmo(int add) {
        setCurrentAmmo(getCurrentAmmo() + add);
    }

    public void setLastShot() {
        lastShot = System.currentTimeMillis();
    }

    public void setLastShotRightFirstSlot() {
        lastShotRightFirstSlot = System.currentTimeMillis();
    }

    public void setLastShotRightSecondSlot() {
        lastShotRightSecondSlot = System.currentTimeMillis();
    }

    public boolean canShootLeftNow() {

        if (System.currentTimeMillis() - lastShot > cooldownLeft && currentAmmo > 0) {
            // lastShot = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    public boolean canShootRightNow() {
        if (System.currentTimeMillis() - lastShotRightFirstSlot > cooldownRight && currentAmmo > 0) {
            return true;
        } else if (System.currentTimeMillis() - lastShotRightSecondSlot > cooldownRight && currentAmmo > 0) {
            return true;
        }
        return false;
    }

}
