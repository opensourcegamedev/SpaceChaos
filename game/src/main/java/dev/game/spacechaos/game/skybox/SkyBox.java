package dev.game.spacechaos.game.skybox;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.DrawableEntity;
import dev.game.spacechaos.engine.time.GameTime;

/**
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class SkyBox implements DrawableEntity {

    private Texture[] skyboxTextures = null;

    private float width = 0;
    private float height = 0;

    private BoundingBox rectangle = new BoundingBox();
    private Vector3 minVector = new Vector3();
    private Vector3 maxVector = new Vector3();

    public SkyBox(Texture[] skyboxTextures, float skyBoxWidth, float skyBoxHeight) {
        this(skyboxTextures);

        this.width = skyBoxWidth;
        this.height = skyBoxHeight;
    }

    private SkyBox(Texture[] skyboxTextures) {
        if (skyboxTextures.length <= 0) {
            throw new IllegalArgumentException("You have to specify one or more skybox textures.");
        }

        this.skyboxTextures = skyboxTextures;

        this.width = skyboxTextures[0].getWidth();
        this.height = skyboxTextures[0].getHeight();
    }

    @Override
    public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        // get current camera position
        float x = camera.getX();
        float y = camera.getY();

        int x1 = (int) x / (int) width;
        int y1 = (int) y / (int) height;

        drawView(x1, y1, batch);
        drawView(x1 + 1, y1, batch);
        drawView(x1 - 1, y1, batch);
        drawView(x1, y1 - 1, batch);
        drawView(x1, y1 + 1, batch);
        drawView(x1 - 1, y1 - 1, batch);
        drawView(x1 - 1, y1 + 1, batch);
        drawView(x1 + 1, y1 - 1, batch);
        drawView(x1 + 1, y1 + 1, batch);
    }

    private int getIndex(int x, int y) {
        return (y * skyboxTextures.length + x) % skyboxTextures.length;
    }

    private void drawView(int x, int y, SpriteBatch batch) {
        // set temporary values to vectors
        minVector.set(x, y, 0);
        maxVector.set(x + width, y + height, 0);

        // update bounding box
        this.rectangle.set(minVector, maxVector);

        int index = getIndex(x, y);

        if (index < 0) {
            int abs = Math.abs(index);
            abs += skyboxTextures.length;
            index = abs % skyboxTextures.length;
        }

        batch.draw(skyboxTextures[index], x * width, y * height, width, height);
    }

}
