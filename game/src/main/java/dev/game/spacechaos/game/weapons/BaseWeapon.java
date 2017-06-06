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
    private int currentAmmo;
    private int maxAmmo;
    private int cooldown;

    public BaseWeapon(int cooldown, int ammo, int maxAmmo) {
        this.cooldown = cooldown;
        this.currentAmmo = ammo;
        this.maxAmmo = maxAmmo;
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

    public boolean canShootNow() {
        if (System.currentTimeMillis() - lastShot > cooldown && currentAmmo > 0) {
            // lastShot = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

}
