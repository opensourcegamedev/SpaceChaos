package dev.game.spacechaos.engine.entity.component.draw;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.listener.TextureChangedListener;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 10.02.2017.
 */
public class DrawTextureComponent extends DrawComponent {

    protected List<TextureChangedListener> textureChangedListenerList = new ArrayList<>();

    protected Texture texture = null;

    public DrawTextureComponent(Texture texture, float originX, float originY) {
        if (texture == null) {
            throw new NullPointerException("texture cannot be null.");
        }

        if (!texture.isManaged()) {
            throw new IllegalStateException("texture isn't loaded.");
        }

        this.texture = texture;

        if (this.texture != null) {
            //update dimension
            this.setDimension(texture.getWidth(), texture.getHeight());
        }

        this.originX = originX;
        this.originY = originY;
    }

    public DrawTextureComponent(Texture texture) {
        this(texture, 0, 0);
    }

    @Override
    public void afterInit(BaseGame game, Entity entity) {
        //set new width and height of entity
        positionComponent.setDimension(texture.getWidth(), texture.getHeight());

        //set dimension of texture
        setDimension(texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //if no texture is set, we don't have to draw anything
        if (this.texture == null) {
            return;
        }

        //only draw texture, if entity is visible
        if (this.visible) {
            //draw texture
            batch.draw(this.texture,
                    this.positionComponent.getX()/* - originX*/,
                    this.positionComponent.getY()/* - originY*/,
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

    @Override
    public ECSPriority getDrawOrder() {
        return ECSPriority.LOW;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public void setTexture(Texture texture, boolean setNewDimension) {
        Texture oldTexture = this.texture;
        this.texture = texture;

        //set dimension of texture
        setDimension(texture.getWidth(), texture.getHeight());

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

    public void addTextureChangedListener(TextureChangedListener listener) {
        this.textureChangedListenerList.add(listener);
    }

    public void removeTextureChangedListener(TextureChangedListener listener) {
        this.textureChangedListenerList.remove(listener);
    }

}
