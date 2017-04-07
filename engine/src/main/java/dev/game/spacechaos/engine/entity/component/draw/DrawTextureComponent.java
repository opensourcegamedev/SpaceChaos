package dev.game.spacechaos.engine.entity.component.draw;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IDrawComponent;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.listener.TextureChangedListener;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 10.02.2017.
 */
public class DrawTextureComponent extends BaseComponent implements IDrawComponent {

    protected PositionComponent positionComponent = null;
    protected List<TextureChangedListener> textureChangedListenerList = new ArrayList<>();

    protected Texture texture = null;

    protected float originX = 0;
    protected float originY = 0;

    protected float width = 0;
    protected float height = 0;
    float scaleX = 1f;
    float scaleY = 1f;
    float angle = 0;

    protected boolean visible = true;

    public DrawTextureComponent(Texture texture) {
        if (texture == null) {
            throw new NullPointerException("texture cannot be null.");
        }

        if (!texture.isManaged()) {
            throw new IllegalStateException("texture isnt loaded.");
        }

        this.texture = texture;

        if (this.texture != null) {
            //update dimension
            this.width = this.texture.getWidth();
            this.height = this.texture.getHeight();
        }
    }

    @Override
    public void onInit (BaseGame game, Entity entity) {
        this.positionComponent = entity.getComponent(PositionComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }

        //set new width and height of entity
        positionComponent.setDimension(texture.getWidth(), texture.getHeight());
    }

    @Override public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //only draw texture, if entity is visible
        if (this.visible) {
            //draw texture
            batch.draw(this.texture,
                    this.positionComponent.getX() - originX,
                    this.positionComponent.getY() - originY,
                    originX,
                    originY,
                    getWidth(),
                    getHeight(),
                    scaleX,
                    scaleY,
                    angle,
                    0,//srcX
                    0,//srcY
                    this.texture.getWidth(),
                    this.texture.getHeight(),
                    false,
                    false
            );
        }
    }

    @Override public ECSPriority getDrawOrder() {
        return ECSPriority.LOW;
    }

    public Texture getTexture () {
        return this.texture;
    }

    public void setTexture (Texture texture, boolean setNewDimension) {
        Texture oldTexture = this.texture;
        this.texture = texture;

        if (setNewDimension) {
            //update width and height
            this.positionComponent.setDimension(texture.getWidth(), texture.getHeight());

            if (this.texture != null) {
                //update dimension
                this.width = this.texture.getWidth();
                this.height = this.texture.getHeight();
            }
        }

        this.textureChangedListenerList.stream().forEach(listener -> {
            listener.onTextureChanged(oldTexture, this.texture);
        });
    }

    public float getWidth () {
        return this.width;
    }

    public float getHeight () {
        return this.height;
    }

    public boolean isVisible () {
        return this.visible;
    }

    public void setVisible (boolean visible) {
        this.visible = visible;
    }

    public void addTextureChangedListener (TextureChangedListener listener) {
        this.textureChangedListenerList.add(listener);
    }

    public void removeTextureChangedListener (TextureChangedListener listener) {
        this.textureChangedListenerList.remove(listener);
    }

}
