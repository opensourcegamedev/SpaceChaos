package dev.game.spacechaos.game.skybox;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.DrawableEntity;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 30.03.2017.
 */
public class SkyBox implements DrawableEntity {

    protected Texture[] skyboxTextures = null;

    protected float width = 0;
    protected float height = 0;

    protected BoundingBox rectangle = new BoundingBox();
    protected Vector3 minVector = new Vector3();
    protected Vector3 maxVector = new Vector3();

    public SkyBox (Texture[] skyboxTextures, float skyBoxWidth, float skyBoxHeight) {
        if (skyboxTextures.length <= 0) {
            throw new IllegalArgumentException("You have to specify one or more skybox textures.");
        }

        this.skyboxTextures = skyboxTextures;

        this.width = skyBoxWidth;
        this.height = skyBoxHeight;
    }

    public SkyBox (Texture[] skyboxTextures) {
        if (skyboxTextures.length <= 0) {
            throw new IllegalArgumentException("You have to specify one or more skybox textures.");
        }

        this.skyboxTextures = skyboxTextures;

        this.width = skyboxTextures[0].getWidth();
        this.height = skyboxTextures[0].getHeight();
    }

    @Override public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //get current camera position
        float x = camera.getX();
        float y = camera.getY();

        int x1 = (int) x / (int) width;
        int y1 = (int) y / (int) height;

        drawView(x1, y1, camera, batch);
        drawView(x1 + 1, y1, camera, batch);
        drawView(x1 - 1, y1, camera, batch);
        drawView(x1, y1 - 1, camera, batch);
        drawView(x1, y1 + 1, camera, batch);
        drawView(x1 - 1, y1 - 1, camera, batch);
        drawView(x1 - 1, y1 + 1, camera, batch);
        drawView(x1 + 1, y1 - 1, camera, batch);
        drawView(x1 + 1, y1 + 1, camera, batch);
    }

    protected int getIndex (int x, int y) {
        return (y * skyboxTextures.length + x) % skyboxTextures.length;
    }

    protected void drawView (int x, int y, CameraWrapper camera, SpriteBatch batch) {
        //set temporary values to vectors
        minVector.set(x, y, 0);
        maxVector.set(x + width, y + height, 0);

        //update bounding box
        this.rectangle.set(minVector, maxVector);

        //check if skybox part is in viewport
        if (!camera.getOriginalCamera().frustum.boundsInFrustum(this.rectangle)) {
            //we don't have to draw this skybox part
            //return;
        }

        int index = getIndex(x, y);

        if (index < 0) {
            int abs = Math.abs(index);
            abs += skyboxTextures.length;
            index = abs % skyboxTextures.length;
        }

        batch.draw(skyboxTextures[index], x * width, y * height, width, height);
    }

}
