package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Adds a shield-component to an entity.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.2-PreAlpha
 */
public class ShieldComponent extends BaseComponent implements IUpdateComponent {

    private float shieldHP;
    private float maxShieldHp;
    private float regenRate;
    private boolean regenerateShield;

    /**
     * Creates a new shield.
     * 
     * @param shieldHP
     *            The shield hit points.
     */
    public ShieldComponent(float shieldHP) {
        this(shieldHP, shieldHP, 0);
    }

    /**
     * Creates a new regenerable shield.
     * 
     * @param shieldHP
     *            The shield hit points.
     * @param maxShieldHp
     *            The max shield hit points.
     * @param regenRate
     *            The rate of regeneration.
     */
    public ShieldComponent(float shieldHP, float maxShieldHp, float regenRate) {
        if (maxShieldHp == shieldHP) {
            regenerateShield = false;
        } else if (maxShieldHp > shieldHP) {
            regenerateShield = true;
        } else if (maxShieldHp < shieldHP) {
            regenerateShield = false;
            shieldHP = maxShieldHp;
        }

        this.regenRate = regenRate;
        this.maxShieldHp = maxShieldHp;
        setCurrentShieldHP(shieldHP);
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {

    }

    public float getMaxShieldHP() {
        return this.maxShieldHp;
    }

    private void reInitializeShield(float shieldHP, float maxShieldHp, float regenRate) {
        if (maxShieldHp == shieldHP) {
            regenerateShield = false;
        } else if (maxShieldHp > shieldHP) {
            regenerateShield = true;
        } else if (maxShieldHp < shieldHP) {
            regenerateShield = false;
            shieldHP = maxShieldHp;
        }

        this.regenRate = regenRate;
        this.maxShieldHp = maxShieldHp;
        setCurrentShieldHP(shieldHP);
    }

    public float getCurrentShieldHP() {
        return this.shieldHP;
    }

    private void setCurrentShieldHP(float shieldHP) {
        if (shieldHP < 0) {
            this.shieldHP = 0;
        } else if (shieldHP > maxShieldHp) {
            this.shieldHP = maxShieldHp;
        } else {
            this.shieldHP = shieldHP;
        }
    }

    public void subShieldHP(float hp) {
        setCurrentShieldHP(getCurrentShieldHP() - hp);
    }

    public void addShieldHP(float hp) {
        setCurrentShieldHP(getCurrentShieldHP() + hp);
    }

    public boolean doesRegenerate() {
        return regenerateShield;
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        if (regenerateShield) {
            addShieldHP(regenRate * time.getDeltaTime() * 10f);
        }
    }

    @Override
    public ECSPriority getUpdateOrder() {
        return ECSPriority.NORMAL;
    }

}
