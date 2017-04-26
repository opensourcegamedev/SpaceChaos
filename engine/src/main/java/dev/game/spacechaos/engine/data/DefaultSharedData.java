package dev.game.spacechaos.engine.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 08.02.2017.
 */
public class DefaultSharedData implements SharedData {

    /**
     * map with shared data
     */
    protected Map<String,Object> dataMap = new ConcurrentHashMap<>();

    @Override public void put(String name, Object obj) {
        if (name == null) {
            throw new NullPointerException("name cannot be null.");
        }

        if (name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be empty.");
        }

        if (obj == null) {
            throw new NullPointerException("obj cannot be null.");
        }

        this.dataMap.put(name, obj);
    }

    @Override public boolean contains(String name) {
        return this.dataMap.get(name) != null;
    }

    @Override public Object get(String name) {
        return this.dataMap.get(name);
    }

    @Override public <T> T get(String name, Class<T> cls) {
        //get object
        Object obj = this.get(name);

        //because null object cannot be casted, check if object is null
        if (obj == null) {
            return null;
        }

        //cast and return object
        return cls.cast(obj);
    }
}
