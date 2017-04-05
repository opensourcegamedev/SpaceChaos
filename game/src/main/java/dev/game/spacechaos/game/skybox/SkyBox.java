package dev.game.spacechaos.game.skybox;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.DrawableEntity;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.game.entities.Player;
import dev.game.spacechaos.game.entities.PlayerSpaceShuttle;

/**
 * Created by Justin on 30.03.2017.
 */
public class SkyBox implements DrawableEntity {

    protected Texture[] skyboxTextures = null;

    protected float width = 0;
    protected float height = 0;

    public SkyBox (Texture[] skyboxTextures, float skyBoxWidth, float skyBoxHeight) {
        this.skyboxTextures = skyboxTextures;

        this.width = skyBoxWidth;
        this.height = skyBoxHeight;
    }

    @Override public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //get current camera position
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

        float posX0 = (x1 - 1) * width;
        float posY0 = (y1 - 1) * width;
        float posX1 = x1 * width;
        float posY1 = y1 * width;
        float posX2 = (x1 + 1) * width;
        float posY2 = (y1 + 1) * height;
    }

    protected int getIndex (int x, int y) {
        return (x * y) % skyboxTextures.length;
    }

    protected void drawView (int x, int y, SpriteBatch batch) {
        int index = getIndex(x, y);

        if (index < 0) {
            int abs = Math.abs(index);
            abs += skyboxTextures.length;
            index = abs % skyboxTextures.length;
        }

        batch.draw(skyboxTextures[index], x * width, y * height, width, height);
    }

}
