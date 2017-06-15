package dev.game.spacechaos.engine.entity.component.draw;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IDrawComponent;
import dev.game.spacechaos.engine.entity.annotation.InjectComponent;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.listener.TextureRegionChangedListener;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 11.02.2017.
 */
@InjectComponent
public class DrawTextureRegionComponent extends DrawComponent {

    protected TextureRegion textureRegion = null;
    protected List<TextureRegionChangedListener> textureRegionChangedListenerList = new ArrayList<>();

    public DrawTextureRegionComponent(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public DrawTextureRegionComponent() {
        //
    }

    @Override
    public void afterInit(BaseGame game, Entity entity) {
        if (this.textureRegion != null) {
            // set new width and height of entity
            positionComponent.setDimension(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());

            setDimension(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        }
    }

    @Override
    public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        if (this.textureRegion == null) {
            // System.err.println("texture region of DrawTextureRegionComponent
            // is null.");

            return;
        }

        // https://github.com/libgdx/libgdx/wiki/2D-Animation

        // draw texture region
        batch.draw(this.textureRegion,
                this.positionComponent.getX()/* - getOriginX() */,
                this.positionComponent.getY()/* - getOriginY() */, getOriginX(), getOriginY(), getWidth(), getHeight(),
                scaleX, scaleY, getRotationAngle());
    }

    @Override
    public ECSPriority getDrawOrder() {
        return ECSPriority.LOW;
    }

    public TextureRegion getTextureRegion() {
        return this.textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion, boolean setNewDimension) {
        TextureRegion oldTextureRegion = this.textureRegion;
        this.textureRegion = textureRegion;

        if (oldTextureRegion == this.textureRegion) {
            // we dont need to notify listeners
            return;
        }

        if (setNewDimension) {
            // set new width and height
            this.positionComponent.setDimension(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        }

        this.textureRegionChangedListenerList.stream().forEach(listener -> {
            // check if dev mode is enabled
            /*
             * if (DevMode.isEnabled()) { //log listener
             * System.out.println("DrawTextureRegionComponent call listener: " +
             * listener.getClass().getName()); }
             */

            listener.onTextureRegionChanged(oldTextureRegion, this.textureRegion);
        });
    }

    public void addTextureRegionChangedListener(TextureRegionChangedListener listener) {
        this.textureRegionChangedListenerList.add(listener);
    }

    public void removeTextureRegionChangedListener(TextureRegionChangedListener listener) {
        this.textureRegionChangedListenerList.remove(listener);
    }

}
