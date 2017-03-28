package dev.game.spacechaos.engine.data;

/**
 * Created by Justin on 08.02.2017.
 */
public interface SharedData {

    public void put(final String name, Object obj);

    public boolean contains(final String name);

    public Object get(final String name);

    public <T> T get(final String name, Class<T> cls);

}
