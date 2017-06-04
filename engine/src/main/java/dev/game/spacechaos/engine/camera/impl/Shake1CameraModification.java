package dev.game.spacechaos.engine.camera.impl;

import dev.game.spacechaos.engine.camera.CameraModification;
import dev.game.spacechaos.engine.camera.ModificationFinishedListener;
import dev.game.spacechaos.engine.camera.TempCameraParams;
import dev.game.spacechaos.engine.time.GameTime;

import java.util.Random;

/**
 * Created by Justin on 11.02.2017.
 */
public class Shake1CameraModification implements CameraModification {

    protected volatile boolean isActive = false;
    protected volatile boolean permanentShake = false;

    protected float elapsed = 0;
    protected float intensity = 0;
    protected float duration = 0;

    protected Random random = new Random();
    protected long startTime = 0l;

    @Override
    public void onUpdate(GameTime time, TempCameraParams camera, ModificationFinishedListener listener) {
        if (!isActive) {
            // mod isnt active, so we dont need to update mod
            return;
        }

        float delta = time.getDeltaTime() * 1000;

        // http://www.netprogs.com/libgdx-screen-shaking/

        // shake only, if activated
        if (elapsed < duration || permanentShake) {
            // Calculate the amount of shake based on how long it has been
            // shaking already
            float currentPower = intensity * camera.getZoom() * ((duration - elapsed) / duration);
            float x = (random.nextFloat() - 0.5f) * 2 * currentPower;
            float y = (random.nextFloat() - 0.5f) * 2 * currentPower;
            camera.translate(-x, -y);

            // Increase the elapsed time by the delta provided.
            // elapsed = time.getTime() - startTime;
            elapsed += delta;
        } else {
            // shake was finsihed
            this.isActive = false;
            // listener.onModificationFinished(this,
            // Shake1CameraModification.class);
        }
    }

    @Override
    public void dispose() {

    }

    public boolean isShaking() {
        return this.isActive;
    }

    /**
     * Start the screen shaking with a given power and duration
     *
     * @param intensity
     *            How much intensity should the shaking use.
     * @param duration
     *            Time in milliseconds the screen should shake.
     */
    public void shake(float intensity, float duration) {
        this.elapsed = 0;
        this.intensity = intensity;
        this.duration = duration;

        this.startTime = System.currentTimeMillis();
        this.isActive = true;
    }

    public void startPermantentShake(float intensity) {
        this.elapsed = 0;
        this.intensity = intensity;

        this.permanentShake = true;
        this.isActive = true;
    }

    public void stopPermanentShake() {
        this.permanentShake = false;
    }

}
