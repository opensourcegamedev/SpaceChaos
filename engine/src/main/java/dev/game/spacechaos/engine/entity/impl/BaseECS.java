package dev.game.spacechaos.engine.entity.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.priority.EntityDrawOrderChangedListener;
import dev.game.spacechaos.engine.entity.priority.EntityUpdateOrderChangedListener;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 10.02.2017.
 */
public abstract class BaseECS implements EntityManager, EntityUpdateOrderChangedListener,
        EntityDrawOrderChangedListener {

    /**
    * list with entities
    */
    protected List<Entity> entityUpdateList = new ArrayList<>();

    /**
     * list with entities
     */
    protected List<Entity> entityDrawList = new ArrayList<>();

    /**
     * list with entities
     */
    protected List<Entity> entityUILayerDrawList = new ArrayList<>();

    /**
    * map with entities
    */
    protected Map<Long,Entity> entityMap = new ConcurrentHashMap<>();

    /**
    * entities with unique name
    */
    protected Map<String,Entity> namedEntitiesMap = new ConcurrentHashMap<>();

    protected BaseGame game = null;

    public BaseECS (BaseGame game) {
        this.game = game;
    }

    @Override
    public void update (BaseGame game, GameTime time) {
        for (Entity entity : this.entityUpdateList) {
            //update entity
            entity.update(game, time);
        }
    }

    @Override
    public void draw (GameTime time, CameraWrapper camera, SpriteBatch batch) {
        for (Entity entity : this.entityDrawList) {
            //draw entity
            entity.draw(time, camera, batch);
        }
        /*this.entityDrawList.stream().forEach(entity -> {
            //draw entity
            entity.draw(time, camera, batch);
        });*/
    }

    @Override
    public void drawUILayer (GameTime time, CameraWrapper camera, SpriteBatch batch) {
        for (Entity entity : this.entityUILayerDrawList) {
            //draw entity
            entity.drawUILayer(time, camera, batch);
        }
        /*this.entityUILayerDrawList.stream().forEach(entity -> {
            //draw entity
            entity.drawUILayer(time, camera, batch);
        });*/
    }

    @Override
    public void onEntityUpdateOrderChanged() {
        //sort list
        Collections.sort(this.entityUpdateList, new Comparator<Entity>() {
            @Override public int compare(Entity o1, Entity o2) {
                return ((Integer) o2.getUpdateOrder().getValue()).compareTo(o1.getUpdateOrder().getValue());
            }
        });
    }

    @Override
    public void onEntityDrawOrderChanged() {
        //sort list
        Collections.sort(this.entityDrawList, new Comparator<Entity>() {
            @Override public int compare(Entity o1, Entity o2) {
                return ((Integer) o2.getDrawOrder().getValue()).compareTo(o1.getDrawOrder().getValue());
            }
        });
    }

    @Override
    public void onEntityUILayerDrawOrderChanged() {
        //sort list
        Collections.sort(this.entityUILayerDrawList, new Comparator<Entity>() {
            @Override public int compare(Entity o1, Entity o2) {
                return ((Integer) o2.getUILayerDrawOrder().getValue()).compareTo(o1.getUILayerDrawOrder().getValue());
            }
        });
    }

    public void addEntity (final String uniqueName, final Entity entity) {
        if (entity == null) {
            throw new NullPointerException("entity cannot be null.");
        }

        //check, if unique name is already in use
        if (this.namedEntitiesMap.get(uniqueName) != null) {
            throw new IllegalStateException("entity name '" + uniqueName + "' is already in use.");
        }

        //initialize entity
        entity.init(this.game, this);

        synchronized (this.entityUpdateList) {
            this.entityUpdateList.add(entity);
        }

        synchronized (this.entityDrawList) {
            this.entityDrawList.add(entity);
        }

        synchronized (this.entityUILayerDrawList) {
            this.entityUILayerDrawList.add(entity);
        }

        //get entityID
        final long entityID = entity.getEntityID();

        //add entity to maps
        this.entityMap.put(entityID, entity);
        this.namedEntitiesMap.put(uniqueName, entity);

        //call listeners to sort lists
        this.onEntityUpdateOrderChanged();
        this.onEntityDrawOrderChanged();

        //call listeners
        this.onEntityAdded(entity);
    }

    public void addEntity (Entity entity) {
        if (entity == null) {
            throw new NullPointerException("entity cannot be null.");
        }

        //initialize entity
        entity.init(this.game, this);

        synchronized (this.entityUpdateList) {
            this.entityUpdateList.add(entity);
        }

        synchronized (this.entityDrawList) {
            this.entityDrawList.add(entity);
        }

        synchronized (this.entityUILayerDrawList) {
            this.entityUILayerDrawList.add(entity);
        }

        //get entityID
        final long entityID = entity.getEntityID();

        //add entity to map
        this.entityMap.put(entityID, entity);

        //call listeners to sort lists
        this.onEntityUpdateOrderChanged();
        this.onEntityDrawOrderChanged();

        //call listeners
        this.onEntityAdded(entity);
    }

    public void removeEntity (Entity entity) {
        if (entity == null) {
            throw new NullPointerException("entity cannot be null.");
        }

        //get entityID
        final long entityID = entity.getEntityID();

        synchronized (this.entityUpdateList) {
            //remove entity
            this.entityUpdateList.remove(entity);
        }

        synchronized (this.entityDrawList) {
            //remove entity
            this.entityDrawList.remove(entity);
        }

        synchronized (this.entityUILayerDrawList) {
            //remove entity
            this.entityUILayerDrawList.remove(entity);
        }

        this.entityMap.remove(entityID);

        //call listeners
        onEntityRemoved(entity);

        //dispose entity
        entity.dispose();
    }

    @Override public void removeEntity(long entityID) {
        //get entity by entityID
        Entity entity = this.entityMap.get(entityID);

        if (entity != null) {
            this.removeEntity(entity);
        }
    }

    @Override public void removeEntity(final String uniqueName) {
        //get entity by unique name
        Entity entity = this.namedEntitiesMap.get(uniqueName);

        if (entity != null) {
            this.removeEntity(entity);
        }
    }

    @Override
    public void removeAllEntities () {
        //ierate through all entities
        for (Map.Entry<Long,Entity> entry : this.entityMap.entrySet()) {
            if (entry.getValue() != null) {
                //remove entity
                removeEntity(entry.getValue());
            }
        }
    }

    protected abstract void onEntityAdded (Entity entity);

    protected abstract void onEntityRemoved (Entity entity);

    @Override
    public BaseGame getGame () {
        return this.game;
    }

    @Override
    public void dispose () {
        //
    }

}
