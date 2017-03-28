package dev.game.spacechaos.engine.camera.impl;

import dev.game.spacechaos.engine.camera.CameraModification;
import dev.game.spacechaos.engine.camera.ModificationFinishedListener;
import dev.game.spacechaos.engine.camera.TempCameraParams;
import dev.game.spacechaos.engine.time.GameTime;

import java.util.Random;

/**
 * Created by Justin on 11.02.2017.
 */
public class Shake2CameraModification implements CameraModification {

    protected volatile boolean isActive = false;
    protected volatile boolean permanentShake = false;

    protected float elapsed = 0;
    protected float radius = 0;
    protected float duration = 0;
    protected float randomAngle = 0;

    protected Random random = new Random();

    @Override
    public void onUpdate(GameTime time, TempCameraParams camera, ModificationFinishedListener listener) {
        if (!isActive) {
            //mod isnt active, so we dont need to update mod
            return;
        }

        float delta = time.getDeltaTime() * 1000;

        //tutorial: http://www.netprogs.com/libgdx-screen-shaking/

        // Only shake when required.
        if(elapsed < duration || permanentShake) {

            // Calculate the shake based on the remaining radius.
            radius *= 0.9f; // diminish radius each frame
            randomAngle += (150 + random.nextFloat() % 60f);
            float x = (float) (Math.sin(randomAngle) * radius);
            float y = (float) (Math.cos(randomAngle) * radius);
            camera.translate(-x, -y);

            // Increase the elapsed time by the delta provided.
            elapsed += delta;
        } else {
            isActive = false;
        }

    }

    @Override
    public void dispose() {

    }

    public boolean isShaking () {
        return this.isActive;
    }

    /**
     * Start the screen shaking with a given power and duration
     *
     * @param radius The starting radius for the shake. The larger the radius, the large the shaking effect.
     * @param duration Time in milliseconds the screen should shake.
     */
    public void shake (float radius, float duration) {
        this.elapsed = 0;
        this.radius = radius;
        this.duration = duration;
        this.randomAngle = random.nextFloat() % 360f;

        this.isActive = true;
    }

    public void startPermantentShake (float radius) {
        this.elapsed = 0;
        this.radius = radius;
        this.randomAngle = random.nextFloat() % 360f;

        this.permanentShake = true;
        this.isActive = true;
    }

    public void stopPermanentShake () {
        this.permanentShake = false;
    }

}
