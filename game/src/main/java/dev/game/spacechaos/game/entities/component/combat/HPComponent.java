package dev.game.spacechaos.game.entities.component.combat;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.listener.HPHitListener;
import dev.game.spacechaos.engine.entity.listener.UpdateHPListener;
import dev.game.spacechaos.engine.game.BaseGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Adds a health-component to an entity so it could be destroyed.
 *
 * @author SpaceChaos-Team (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class HPComponent extends BaseComponent {

    private float currentHP = 100;
    private float maxHP = 100;
    private float percent = 0;

    //HP update listener list
    private List<UpdateHPListener> updateHPListenerList = new ArrayList<>();

    //HP hit listener list
    private List<HPHitListener> hpHitListenerList = new ArrayList<>();

    public HPComponent (float currentHP, float maxHP) {
        setMaxHP(maxHP);
        setCurrentHP(currentHP);

        //update percent
        this.updatePercent();
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {

    }

    public float getPercent () {
        return this.percent;
    }

    private void updatePercent() {
        this.percent = this.currentHP / this.maxHP;
    }

    public float getMaxHP () {
        return this.maxHP;
    }

    private void setMaxHP(float maxHP) {
        this.maxHP = maxHP;

        this.updatePercent();
    }

    public float getCurrentHP () {
        return this.currentHP;
    }

    private void setCurrentHP(float currentHP) {
        float oldValue = this.currentHP;

        if (currentHP < 0) {
            this.currentHP = 0;
        } else if (currentHP > maxHP) {
            this.currentHP = maxHP;
        } else {
            this.currentHP = currentHP;
        }

        //update percent
        updatePercent();

        //notify update listeners
        this.notifyUpdateListener(oldValue, this.currentHP, this.maxHP);

        if (this.currentHP <= 0) {
            //character was hit
            this.notifyHitListener(oldValue, this.currentHP, this.maxHP);
        }
    }

    void subHP(float hp) {
        setCurrentHP(getCurrentHP() - hp);
    }

    private void notifyUpdateListener(float oldValue, float newValue, float maxValue) {
        //call all HP update listeners
        for (UpdateHPListener listener : this.updateHPListenerList) {
            //call listener
            listener.onHPUpdate(oldValue, newValue, maxValue);
        }
    }

    public void addUpdateListener (UpdateHPListener listener) {
        this.updateHPListenerList.add(listener);
    }

    private void notifyHitListener(float oldValue, float newValue, float maxValue) {
        //call listeners
        for (HPHitListener listener : this.hpHitListenerList) {
            //call listener
            listener.onHit(oldValue, newValue, maxValue);
        }
    }

    public void addHitListener (HPHitListener listener) {
        this.hpHitListenerList.add(listener);
    }
}
