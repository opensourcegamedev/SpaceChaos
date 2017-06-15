package dev.game.spacechaos.engine.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.annotation.InjectComponent;
import dev.game.spacechaos.engine.entity.annotation.SharableComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.exception.RequiredComponentNotFoundException;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 10.02.2017.
 */
public class Entity {

    /**
     * map with all components
     */
    protected Map<Class, IComponent> componentMap = new HashMap<>();

    /**
     * list with all components which implements update() method
     */
    protected List<IUpdateComponent> updateComponentList = new ArrayList<>();

    /**
     * list with all components which implements draw() method
     */
    protected List<IDrawComponent> drawComponentList = new ArrayList<>();

    /**
     * list with all components which implements drawUILayer() method
     */
    protected List<IDrawUILayerComponent> drawUILayerComponentList = new ArrayList<>();

    protected BaseGame game = null;
    protected EntityManager ecs = null;

    protected static AtomicLong lastID = new AtomicLong(0);
    protected long entityID = lastID.incrementAndGet();

    protected ECSPriority updateOrder = ECSPriority.NORMAL;
    protected ECSPriority drawOrder = ECSPriority.NORMAL;
    protected ECSPriority drawUILayerOrder = ECSPriority.NORMAL;
    private boolean initialized = false;

    public Entity(EntityManager ecs) {
        this.game = ecs.getGame();
        this.ecs = ecs;
    }

    /**
     * Initializes the entity and its components.
     * 
     * @param game
     *            The game object.
     * @param ecs
     *            The ECS.
     */
    public void init(BaseGame game, EntityManager ecs) {
        this.game = game;
        this.ecs = ecs;

        // Injects the components
        for (IComponent component : componentMap.values()) {
            injectComponents(component, component.getClass());
        }

        // Initializes all components
        for (IComponent component : componentMap.values()) {
            component.init(this.game, this);
        }
        
        this.initialized = true;

    }

    private void injectComponents(Object target, Class clazz) {
        InjectComponent classAnnotation = (InjectComponent) clazz.getAnnotation(InjectComponent.class);
        if (classAnnotation != null) {
            injectValidFieldsInClass(target, clazz, classAnnotation.nullable(), classAnnotation.injectInherited());
        } else {
            injectAnnotatedFieldsInClass(target, clazz);
        }
    }

    /**
     * Injects all properly annotated fields in the component Object.
     * 
     * @param target
     *            The object whose fields should be injected.
     * @param clazz
     *            The class of the component.
     * @see #injectField(IComponent, Field, boolean)
     */
    private void injectAnnotatedFieldsInClass(Object target, Class clazz) {
        for (Field field : target.getClass().getDeclaredFields()) {
            InjectComponent annotation = field.getAnnotation(InjectComponent.class);

            if (annotation != null && IComponent.class.isAssignableFrom(field.getType())) {
                injectField(target, field, annotation.nullable());
            }
        }
    }

    /**
     * Injects all valid fields in the given component Object.
     * 
     * @param component
     *            The object whose field should be injected.
     * @param clazz
     *            The class of the component.
     * @param nullable
     *            Whether the field can be null.
     * @param injectInherited
     *            Whether inherited fields should also be injected.
     * @see #injectField(IComponent, Field, boolean)
     */
    private void injectValidFieldsInClass(Object target, Class clazz, boolean nullable, boolean injectInherited) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0, s = declaredFields.length; s > i; i++) {
            if (IComponent.class.isAssignableFrom(declaredFields[i].getType())) {
                InjectComponent fieldAnnotation = declaredFields[i].getAnnotation(InjectComponent.class);
                injectField((IComponent) target, declaredFields[i],
                        fieldAnnotation != null ? fieldAnnotation.nullable() : nullable);
            }
        }

        while (injectInherited && (clazz = clazz.getSuperclass()) != Object.class) {
            injectValidFieldsInClass(target, clazz, nullable, injectInherited);
        }
    }

    /**
     * Injects the value of the field in the given component.
     * 
     * @param target
     *            The object whose field should be injected.
     * @param field
     *            The field.
     * @param nullable
     *            Whether the field can be null.
     */
    private void injectField(Object target, Field field, boolean nullable) {
        // check if component present
        if (componentMap.containsKey(field.getType())) {
            field.setAccessible(true);
            try {
                field.set(target, componentMap.get(field.getType()));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("Couldn't inject component '" + field.getType() + "' in '"
                        + field.getDeclaringClass().getName() + "'. Exception: " + e.getLocalizedMessage());
            }
        } else if (!nullable) {
            throw new RequiredComponentNotFoundException("Component '" + field.getType()
                    + "' is required by component '" + field.getDeclaringClass().getName() + "' but does not exist.");
        }
    }

    public void update(BaseGame game, GameTime time) {
        // update all components
        for (IUpdateComponent component : this.updateComponentList) {
            component.update(game, time);
        }

        /*
         * this.updateComponentList.stream().forEach(component -> {
         * component.update(game, time); });
         */
    }

    public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        // draw all components
        for (IDrawComponent component : this.drawComponentList) {
            component.draw(time, camera, batch);
        }

        /*
         * Üthis.drawComponentList.stream().forEach(component -> {
         * component.draw(time, camera, batch); });
         */
    }

    public void drawUILayer(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        // draw all components
        for (IDrawUILayerComponent component : this.drawUILayerComponentList) {
            component.drawUILayer(time, camera, batch);
        }

        /*
         * this.drawUILayerComponentList.stream().forEach(component -> {
         * component.drawUILayer(time, camera, batch); });
         */
    }

    public long getEntityID() {
        return this.entityID;
    }

    public <T extends IComponent> T getComponent(Class<T> cls) {
        // for HashMap implementation
        if (!this.componentMap.containsKey(cls)) {
            // component doesnt exists on this entity
            return null;
        }

        IComponent component = this.componentMap.get(cls);

        // for ConcurrentHashMap implementation
        if (component == null) {
            // component doesnt exists on this entity
            return null;
        }

        return cls.cast(component);
    }

    public <T extends IComponent> void addComponent(T component, Class<T> cls) {
        // add component to map
        this.componentMap.put(cls, component);
        
        if(initialized){
            injectComponents(component, cls);
            
            component.init(this.game, this);
        }

        component.onAddedToEntity(this);

        // check if component needs to update
        if (component instanceof IUpdateComponent) {
            this.updateComponentList.add((IUpdateComponent) component);

            // sort list
            Collections.sort(this.updateComponentList, new Comparator<IUpdateComponent>() {
                @Override
                public int compare(IUpdateComponent o1, IUpdateComponent o2) {
                    if (o1 == null) {
                        throw new NullPointerException("o1 is null.");
                    }

                    if (o2 == null) {
                        throw new NullPointerException("o2 is null.");
                    }

                    if (o1.getUpdateOrder() == null) {
                        throw new NullPointerException("Please return an non null value on getUpdateOrder() method.");
                    }

                    if (o2.getUpdateOrder() == null) {
                        throw new NullPointerException("Please return an non null value on getUpdateOrder() method.");
                    }

                    return ((Integer) o2.getUpdateOrder().getValue()).compareTo(o1.getUpdateOrder().getValue());
                }
            });
        }

        /*
         * System.out.println("======== Update List ========="); for
         * (IUpdateComponent component1 : this.updateComponentList) {
         * System.out.println("Order: " + component1.getUpdateOrder().getValue()
         * + ", Classname: " + component1.getClass().getName()); }
         */

        // check if component needs to draw
        if (component instanceof IDrawComponent) {
            this.drawComponentList.add((IDrawComponent) component);

            // sort list
            Collections.sort(this.drawComponentList, new Comparator<IDrawComponent>() {

                @Override
                public int compare(IDrawComponent o1, IDrawComponent o2) {
                    return ((Integer) o2.getDrawOrder().getValue()).compareTo(o1.getDrawOrder().getValue());
                }
            });
        }

        // check if component needs to draw on user interface layer
        if (component instanceof IDrawUILayerComponent) {
            this.drawUILayerComponentList.add((IDrawUILayerComponent) component);

            // sort list
            Collections.sort(this.drawUILayerComponentList, new Comparator<IDrawUILayerComponent>() {
                @Override
                public int compare(IDrawUILayerComponent o1, IDrawUILayerComponent o2) {
                    return ((Integer) o2.getUILayerDrawOrder().getValue())
                            .compareTo(o1.getUILayerDrawOrder().getValue());
                }
            });
        }

        this.onComponentAdded(this, component, cls);

    }

    public <T extends IComponent> void addComponent(T component) {
        Class<T> cls = (Class<T>) component.getClass();

        this.addComponent(component, cls);
    }

    public <T extends IComponent> void removeComponent(Class<T> cls) {
        IComponent component = this.componentMap.get(cls);

        if (component != null && cls.isInstance(component)) {
            this.onComponentRemoved(this, cls.cast(component), cls);
        } else {
            this.onComponentRemoved(this, null, cls);
        }

        // remove component from map
        this.componentMap.remove(cls);

        if (component != null) {
            component.onRemovedFromEntity(this);

            this.updateComponentList.remove(component);
            this.drawComponentList.remove(component);
            this.drawUILayerComponentList.remove(component);

            // dispose component which arent sharable
            if (!cls.isAnnotationPresent(SharableComponent.class)) {
                component.dispose();
            }
        }
    }

    public ECSPriority getUpdateOrder() {
        return this.updateOrder;
    }

    public void setUpdateOrder(ECSPriority updateOrder) {
        this.updateOrder = updateOrder;

        // call listeners
        ecs.onEntityUpdateOrderChanged();
    }

    public ECSPriority getDrawOrder() {
        return this.drawOrder;
    }

    public void setDrawOrder(ECSPriority drawOrder) {
        this.drawOrder = drawOrder;

        // call listeners
        ecs.onEntityDrawOrderChanged();
    }

    public ECSPriority getUILayerDrawOrder() {
        return this.drawUILayerOrder;
    }

    public void setUILayerDrawOrder(ECSPriority drawOrder) {
        this.drawUILayerOrder = drawOrder;

        // call listeners
        ecs.onEntityUILayerDrawOrderChanged();
    }

    public void printUpdateList() {
        System.out.println("======== update list of entity ========");

        int i = 0;
        for (IUpdateComponent component : this.updateComponentList) {
            System.out.println("Index " + i + ": " + component.getClass().getName());

            i++;
        }
    }

    public void printDrawList() {
        System.out.println("======== draw list of entity ========");

        int i = 0;
        for (IDrawComponent component : this.drawComponentList) {
            System.out.println("Index " + i + ": " + component.getClass().getName());

            i++;
        }
    }

    protected <T extends IComponent> void onComponentAdded(Entity entity, T component, Class<T> cls) {
        this.ecs.onComponentAdded(entity, component, cls);
    }

    protected <T extends IComponent> void onComponentRemoved(Entity entity, T component, Class<T> cls) {
        this.ecs.onComponentRemoved(entity, component, cls);
    }

    public EntityManager getEntityComponentSystem() {
        return this.ecs;
    }

    public void dispose() {
        List<Class> componentsToRemoveList = new ArrayList<>();

        // dispose all components
        for (Map.Entry<Class, IComponent> entry : this.componentMap.entrySet()) {
            Class cls = entry.getKey();

            // remove component
            componentsToRemoveList.add(cls);
        }

        componentsToRemoveList.stream().forEach(cls -> {
            this.removeComponent(cls);
        });
    }

}
