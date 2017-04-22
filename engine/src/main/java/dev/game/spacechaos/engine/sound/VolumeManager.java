package dev.game.spacechaos.engine.sound;

/**
 * Created by Justin on 22.04.2017.
 */
public class VolumeManager {

    protected static VolumeManager instance = null;

    protected float masterVolume = 0.6f;
    protected float envVolume = 1f;
    protected float backgroundMusicVolume = 1f;

    public VolumeManager () {
        //
    }

    public float getMasterVolume () {
        return this.masterVolume;
    }

    public void setMasterVolume (float volume) {
        if (volume < 0) {
            throw new IllegalArgumentException("master volume has to be >= 0.");
        }

        if (volume > 1) {
            throw new IllegalArgumentException("master volume has to be <= 1 (100 percent).");
        }

        this.masterVolume = volume;
    }

    public float getBackgroundMusicVolume () {
        return this.getMasterVolume() * this.backgroundMusicVolume;
    }

    public void setBackgroundMusicVolume (float volume) {
        if (volume < 0) {
            throw new IllegalArgumentException("music volume has to be >= 0.");
        }

        if (volume > 1) {
            throw new IllegalArgumentException("music volume has to be <= 1 (100 percent).");
        }

        this.backgroundMusicVolume = volume;
    }

    public float getEnvVolume () {
        return this.getMasterVolume() * this.envVolume;
    }

    public void setEnvVolume (float volume) {
        if (volume < 0) {
            throw new IllegalArgumentException("environment sounds volume has to be >= 0.");
        }

        if (volume > 1) {
            throw new IllegalArgumentException("environment sounds volume has to be <= 1 (100 percent).");
        }

        this.envVolume = volume;
    }

    public static VolumeManager getInstance () {
        if (instance == null) {
            instance = new VolumeManager();
        }

        return instance;
    }

}
