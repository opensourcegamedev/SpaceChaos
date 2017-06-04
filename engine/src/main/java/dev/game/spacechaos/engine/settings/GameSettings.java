package dev.game.spacechaos.engine.settings;

import java.io.File;
import java.io.IOException;

/**
 * Created by Justin on 06.02.2017.
 */
public interface GameSettings {

    /**
     * load settings from file
     */
    public void loadFromFile(File file) throws IOException;

    /**
     * get value
     */
    public String get(String section, String key);

    /**
     * get value
     */
    public String getOrDefault(String section, String key, String defaultValue);

    /**
     * get integer value
     */
    public int getInt(String section, String key);

    /**
     * get integer value
     */
    public float getFloat(String section, String key);

    public void save() throws IOException;

}
