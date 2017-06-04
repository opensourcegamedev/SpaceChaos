package dev.game.spacechaos.engine.entity.component;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.annotation.ThreadSafeComponent;
import dev.game.spacechaos.engine.entity.listener.PositionChangedListener;
import dev.game.spacechaos.engine.exception.InvalidJSONException;
import dev.game.spacechaos.engine.exception.ReadOnlyException;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.json.JSONLoadable;
import dev.game.spacechaos.engine.json.JSONSerializable;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * An Thread Safe Data Holder for entities to store their position
 *
 * Created by Justin on 10.02.2017.
 */
@ThreadSafeComponent
public class PositionComponent extends BaseComponent implements JSONSerializable, JSONLoadable, Cloneable {

    protected volatile float x = 0;
    protected volatile float y = 0;

    protected volatile float width = 0;
    protected volatile float height = 0;

    protected volatile float scale = 1;

    //list with listeners
    protected List<PositionChangedListener> changedListenerList = new ArrayList<>();
    protected ReentrantLock listenerLock = new ReentrantLock();
    protected final int LOCK_TIMEOUT = 200;

    protected static final String COMPONENT_NAME = "PositionComponent";

    protected AtomicBoolean readOnly = new AtomicBoolean(false);

    //protected Vector3 min = new Vector3(0, 0, 0);
    //protected Vector3 max = new Vector3(0, 0, 0);

    //protected BoundingBox boundingBox = new BoundingBox(min, max);

    public PositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PositionComponent(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
    }

    public PositionComponent() {
        //
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        //
    }

    public float getX () {
        return this.x;
    }

    public void setX (float x) {
        if (readOnly.get()) {
            throw new ReadOnlyException("Cannot set X position, because PositionComponent is readonly.");
        }

        //save old value
        float oldX = this.x;

        //set new value
        this.x = x;

        this.notifyPositionChangedListener(oldX, x, this.y, this.y);
    }

    public float getY () {
        return this.y;
    }

    public void setY (float y) {
        if (readOnly.get()) {
            throw new ReadOnlyException("Cannot set Y position, because PositionComponent is readonly.");
        }

        //save old value
        float oldY = this.y;

        //set new value
        this.y = y;

        this.notifyPositionChangedListener(this.x, oldY, this.x, this.y);
    }

    public void setPosition (float x, float y) {
        if (readOnly.get()) {
            throw new ReadOnlyException("Cannot set position, because PositionComponent is readonly.");
        }

        //save old values
        float oldX = this.x;
        float oldY = this.y;

        //set new values
        this.x = x;
        this.y = y;

        this.notifyPositionChangedListener(oldX, oldY, x, y);
    }

    public void setMiddlePosition (float middleX, float middleY) {
        float x = middleX - (width / 2);
        float y = middleY - (height / 2);

        this.setPosition(x, y);
    }

    public float getMiddleX () {
        return this.x + (this.getWidth() / 2);
    }

    public float getMiddleY () {
        return this.y + (this.getHeight() / 2);
    }

    /**
     * move entity
     *
     * @param x speed x
     * @param y speed y
     */
    public void move (float x, float y) {
        this.setPosition(this.x + x, this.y + y);
    }

    public float getWidth () {
        return this.width * this.scale;
    }

    public void setWidth (float width) {
        float oldWidth = this.width;
        this.width = width;

        this.notifyDimensionChangedListener(oldWidth, this.height, this.width, this.height);
    }

    public float getHeight () {
        return this.height * this.scale;
    }

    public void setHeight (float heigth) {
        float oldHeight = this.height;
        this.height = heigth;

        this.notifyDimensionChangedListener(this.width, oldHeight, this.width, this.height);
    }

    public void setDimension (float width, float height) {
        //save old values
        float oldWidth = width;
        float oldHeight = height;

        this.width = width;
        this.height = height;

        if (oldWidth != this.width || oldHeight != this.height) {
            notifyDimensionChangedListener(oldWidth, oldHeight, width, height);
        }
    }

    public float getScale () {
        return this.scale;
    }

    public void setScale (float scale) {
        this.scale = scale;
    }

    @Override
    public PositionComponent clone () {
        return new PositionComponent(this.x, this.y);
    }

    protected void notifyPositionChangedListener (float oldX, float oldY, float newX, float newY) {
        //update bounding box
        //this.updateBoundingBox();

        /*try {
            //lock list to avoid ConcurrentModificationException
            this.listenerLock.tryLock(LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldnt lock changedListenerList on PositionComponent: " + e.getLocalizedMessage());
        }*/

        //call listeners
        for (PositionChangedListener listener : this.changedListenerList) {
            listener.onPositionChanged(oldX, oldY, newX, newY);
        }

        //this.listenerLock.unlock();
    }

    protected void notifyDimensionChangedListener (float oldWidth, float oldHeight, float newWidth, float newHeight) {
        //update bounding box
        this.updateBoundingBox();

        //TODO: call listeners
    }

    public void addPositionChangedListener (PositionChangedListener listener) {
        try {
            //lock list to avoid ConcurrentModificationException
            this.listenerLock.tryLock(LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldnt lock changedListenerList on PositionComponent: " + e.getLocalizedMessage());
        }

        this.changedListenerList.add(listener);

        this.listenerLock.unlock();
    }

    public void removePositionChangedListener (PositionChangedListener listener) {
        try {
            //lock list to avoid ConcurrentModificationException
            this.listenerLock.tryLock(LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldnt lock changedListenerList on PositionComponent: " + e.getLocalizedMessage());
        }

        this.changedListenerList.remove(listener);

        this.listenerLock.unlock();
    }

    public boolean isReadOnly () {
        return this.readOnly.get();
    }

    public void setReadOnly (boolean readOnly) {
        this.readOnly.set(readOnly);
    }

    @Override public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        //add values
        json.put("component_name", COMPONENT_NAME);
        json.put("x", this.x);
        json.put("y", this.y);

        json.put("width", this.width);
        json.put("height", this.height);

        json.put("scale", this.scale);

        return json;
    }

    public void updateBoundingBox () {
        //this.min.set(getX(), getY(), 0);
        //this.max.set(getX() + getWidth(), getY() + getHeight(), 0);
        //this.boundingBox.set(this.min, this.max);
    }

    /*public BoundingBox getBoundingBox () {
        return this.boundingBox;
    }*/

    @Override public void loadFromJSON(JSONObject json) {
        if (json == null) {
            throw new NullPointerException("json cannot be null.");
        }

        if (!json.has("x") || !json.has("y")) {
            throw new InvalidJSONException("JSON for PositionComponent is invalide! x and y position has to be set.");
        }

        this.x = Float.parseFloat(json.getString("x"));
        this.y = Float.parseFloat(json.getString("y"));
        this.width = Float.parseFloat(json.getString("width"));
        this.height = Float.parseFloat(json.getString("height"));
        this.scale = Float.parseFloat(json.getString("scale"));
    }

    @Override
    public String toString () {
        return toJSON().toString();
    }

    @Override
    public boolean equals (Object obj) {
        if (!(obj instanceof PositionComponent)) {
            //another object
            return false;

            //throw new IllegalArgumentException("object has to be an instance of PositionComponent to check, if equals, class of object: " + obj.getClass().getName());
        }

        PositionComponent comp = (PositionComponent) obj;

        return this.x == comp.getX() && this.y == comp.getY();
    }

    @Override
    public int hashCode () {
        return new HashCodeBuilder(17, 37).
            append(this.x).
            append(this.y).
            toHashCode();
    }
}
