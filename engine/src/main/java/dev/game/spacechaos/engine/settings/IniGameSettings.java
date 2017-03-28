package dev.game.spacechaos.engine.settings;

import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 06.02.2017.
 */
public class IniGameSettings implements GameSettings {

    protected Ini ini = null;
    protected Profile.Section section = null;
    protected File cfgFile = null;
    protected Map<String,Profile.Section> sectionMap = new ConcurrentHashMap<>();

    public IniGameSettings () {
        //
    }

    @Override public void loadFromFile(File file) throws IOException {
        this.cfgFile = file;
        this.ini = new Ini(file);

        //get all sections
        for (String sectionName : this.ini.keySet()) {
            this.sectionMap.put(sectionName, this.ini.get(sectionName));
        }
    }

    @Override public String get(String section, String key) {
        Profile.Section section1 = this.sectionMap.get(section);

        if (section1 == null) {
            System.err.println("Couldnt found config section (config file: " + cfgFile.getAbsolutePath() + "): " + section);
        }

        return section1.get(key);
    }

    @Override public String getOrDefault(String section, String key, String defaultValue) {
        Profile.Section section1 = this.sectionMap.get(section);

        if (section1 == null) {
            System.err.println("Couldnt found config section (config file: " + cfgFile.getAbsolutePath() + "): " + section);
        }

        return section1.getOrDefault(key, defaultValue);
    }

    @Override public int getInt(String section, String key) {
        return Integer.parseInt(get(section, key));
    }

    @Override public float getFloat(String section, String key) {
        return Float.parseFloat(get(section, key));
    }

    @Override public void save() throws IOException {
        this.ini.store();
    }

}
