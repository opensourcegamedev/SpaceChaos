package dev.game.spacechaos.game.entities.component.combat;

import java.util.ArrayList;
import java.util.List;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.listener.HPDeathListener;
import dev.game.spacechaos.engine.entity.listener.HPHitListener;
import dev.game.spacechaos.engine.entity.listener.UpdateHPListener;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Adds a health-component to an entity so it can be destroyed.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class HPComponent extends BaseComponent {

    private float currentHP = 100;
    private float maxHP = 100;
    private float percent = 0;

    // Listener
    private List<UpdateHPListener> updateHPListenerList = new ArrayList<>();
    private List<HPDeathListener> hpDeathListenerList = new ArrayList<>();
    private List<HPHitListener> hpHitListenerList = new ArrayList<>();

    public HPComponent(float currentHP, float maxHP) {
        setMaxHP(maxHP);
        setCurrentHP(currentHP);

        // update percent
        this.updatePercent();
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {

    }

    public float getPercent() {
        return this.percent;
    }

    private void updatePercent() {
        this.percent = this.currentHP / this.maxHP;
    }

    public float getMaxHP() {
        return this.maxHP;
    }

    private void setMaxHP(float maxHP) {
        this.maxHP = maxHP;

        this.updatePercent();
    }

    public float getCurrentHP() {
        return this.currentHP;
    }

    private void setCurrentHP(float currentHP) {
        setCurrentHP(currentHP, null);
    }

    private void setCurrentHP(float currentHP, Entity causingEntity) {
        float oldValue = this.currentHP;

        if (currentHP < 0) {
            this.currentHP = 0;
        } else if (currentHP > maxHP) {
            this.currentHP = maxHP;
        } else {
            this.currentHP = currentHP;
        }

        updatePercent();

        // notify update listeners
        this.notifyUpdateListener(oldValue, this.currentHP, this.maxHP);

        // notify hit listeners
        if (currentHP < oldValue) {
            this.notifyHitListener(oldValue, this.currentHP, this.maxHP, causingEntity);
        }

        // notify death listeners
        if (this.currentHP <= 0) {
            this.notifyDeathListener(causingEntity);
        }
    }

    void subHP(float hp, Entity causingEntity) {
        setCurrentHP(getCurrentHP() - hp, causingEntity);
    }

    void subHP(float hp) {
        subHP(hp, null);
    }

    private void notifyUpdateListener(float oldValue, float newValue, float maxValue) {
        for (UpdateHPListener listener : this.updateHPListenerList) {
            listener.onHPUpdate(oldValue, newValue, maxValue);
        }
    }

    public void addUpdateListener(UpdateHPListener listener) {
        this.updateHPListenerList.add(listener);
    }

    private void notifyHitListener(float oldValue, float newValue, float maxValue, Entity causingEntity) {
        for (HPHitListener listener : this.hpHitListenerList) {
            listener.onHit(oldValue, newValue, maxValue, causingEntity);
        }
    }

    public void addHitListener(HPHitListener listener) {
        this.hpHitListenerList.add(listener);
    }

    private void notifyDeathListener(Entity causingEntity) {
        for (HPDeathListener listener : this.hpDeathListenerList) {
            listener.onDeath(causingEntity);
        }
    }

    public void addDeathListener(HPDeathListener listener) {
        this.hpDeathListenerList.add(listener);
    }

}
