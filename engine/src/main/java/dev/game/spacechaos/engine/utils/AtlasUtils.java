package dev.game.spacechaos.engine.utils;

import dev.game.spacechaos.engine.exception.FilePermissionException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Justin on 09.03.2017.
 */
public class AtlasUtils {

    public static Map<String,Integer> getAvailableAnimations (String atlasFile) throws IOException {
        //create new empty list
        Map<String,Integer> map = new HashMap<>();

        File f = new File(atlasFile);

        if (!f.exists()) {
            throw new IllegalStateException("atlas file doesnt exists: " + atlasFile);
        }

        if (!f.canRead()) {
            throw new FilePermissionException("Cannot read atlas file, please set right permissions for atlas file: " + atlasFile);
        }

        //read all lines from file
        List<String> lines = FileUtils.readLines(f.getAbsolutePath(), StandardCharsets.ISO_8859_1);

        int lineCounter = 0;

        //iterate through all lines
        for (String line : lines) {
            lineCounter++;

            if (lineCounter < 5) {
                //skip line, because it belongs to header
                continue;
            }

            if (!line.startsWith(" ")) {
                //line is an animation name
                if (!map.containsKey(line)) {
                    //add animation name to map with first frame
                    map.put(line, 1);
                } else {
                    //get last frame counter
                    Integer lastCounter = map.get(line);

                    //increment frame counter, because we add an new frame
                    lastCounter++;

                    map.put(line, lastCounter);
                }
            }
        }

        return map;
    }

}
