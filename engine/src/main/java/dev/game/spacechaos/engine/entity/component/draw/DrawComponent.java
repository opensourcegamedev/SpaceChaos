package dev.game.spacechaos.engine.entity.component.draw;

import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IDrawComponent;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Created by Justin on 08.04.2017.
 */
public abstract class DrawComponent extends BaseComponent implements IDrawComponent {

    protected PositionComponent positionComponent = null;

    protected float originX = 0;
    protected float originY = 0;

    protected float width = 0;
    protected float height = 0;
    float scaleX = 1f;
    float scaleY = 1f;
    float angle = 0;

    protected boolean visible = true;

    @Override
    public final void onInit (BaseGame game, Entity entity) {
        this.positionComponent = entity.getComponent(PositionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }
    }

    public abstract void afterInit (BaseGame game, Entity entity);

    @Override public ECSPriority getDrawOrder() {
        return ECSPriority.LOW;
    }

    public float getWidth () {
        return this.width;
    }

    public float getHeight () {
        return this.height;
    }

    public void setDimension (float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getOriginX () {
        return this.originX;
    }

    public float getOriginY () {
        return this.originY;
    }

    public void setOrigin (float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
    }

    public boolean isVisible () {
        return this.visible;
    }

    public void setVisible (boolean visible) {
        this.visible = visible;
    }

    public float getRotationAngle () {
        return this.angle % 360;
    }

    public void setRotationAngle (float angle) {
        if (angle < 0) {
            throw new IllegalArgumentException("rotation angle has to be >= 0 and <= 360 degree.");
        }

        this.angle = angle % 360;
    }

}
