package dev.game.spacechaos.engine.entity.component.sound;

import com.badlogic.gdx.audio.Sound;
import dev.game.spacechaos.engine.collision.listener.CollisionListener;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.component.collision.CollisionComponent;
import dev.game.spacechaos.engine.exception.RequiredComponentNotFoundException;
import dev.game.spacechaos.engine.game.BaseGame;

import java.io.File;

/**
 * Created by Justin on 12.04.2017.
 */
public class OnCollisionPlaySoundComponent extends BaseComponent implements CollisionListener {

    protected CollisionComponent collisionComponent = null;
    protected String soundPath = "";

    protected Sound sound = null;
    protected float soundVolume = 1f;

    public OnCollisionPlaySoundComponent (String soundPath) {
        this.soundPath = soundPath;

        if (!new File(soundPath).exists()) {
            throw new IllegalStateException("Could not found sound file: " + soundPath);
        }
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        this.collisionComponent = entity.getComponent(CollisionComponent.class);

        if (this.collisionComponent == null) {
            throw new RequiredComponentNotFoundException("entity doesnt have an CollisionComponent.");
        }

        this.collisionComponent.addCollisionListener(this);

        //load sound
        game.getAssetManager().load(soundPath, Sound.class);
        game.getAssetManager().finishLoadingAsset(soundPath);

        //get sound
        this.sound = game.getAssetManager().get(soundPath);
    }

    @Override
    public void onEnter(Entity entity, Entity otherEntity) {
        this.sound.stop();
        this.sound.play(soundVolume);
    }

    @Override
    public void onStay(Entity entity, Entity otherEntity) {

    }

    @Override
    public void onExit(Entity entity, Entity otherEntity) {

    }

    public void setSoundVolume (float volume) {
        if (volume < 0 || volume > 1) {
            throw new IllegalArgumentException("volume cannot be lesser than 0 or greater than 1.");
        }

        this.soundVolume = volume;
    }

    @Override
    public void dispose() {
        game.getAssetManager().unload(soundPath);
    }

}
