package dev.game.spacechaos.engine.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Justin on 10.03.2017.
 */
public class SpriteBatcherUtils {

    protected static Texture pixelTexture = null;
    protected static TextureRegion textureRegion = null;
    protected static Affine2 affine2 = new Affine2();

    public static void drawRect(SpriteBatch batch, Rectangle hitbox, float thickness, Color color) {
        // draw line
        /*
         * SpriteBatcherUtils.drawLine(batch, hitbox.getX(), hitbox.getY(),
         * hitbox.getX() + hitbox.getWidth(), hitbox.getY(), color);
         * 
         * //draw line SpriteBatcherUtils.drawLine(batch, hitbox.getX(),
         * hitbox.getY(), hitbox.getX(), hitbox.getY() + hitbox.getHeight(),
         * color);
         * 
         * //draw line SpriteBatcherUtils.drawLine(batch, hitbox.getX(),
         * hitbox.getY() + hitbox.getHeight(), hitbox.getX() +
         * hitbox.getWidth(), hitbox.getY() + hitbox.getHeight(), color);
         * 
         * //draw line SpriteBatcherUtils.drawLine(batch, hitbox.getX() +
         * hitbox.getWidth(), hitbox.getY(), hitbox.getX() + hitbox.getWidth(),
         * hitbox.getY() + hitbox.getHeight(), color);
         */

        float x = hitbox.getX();
        float y = hitbox.getY();
        float width = hitbox.getWidth();
        float height = hitbox.getHeight();

        // draw border of rectangle
        fillRectangle(batch, x, y, width, thickness, color);
        fillRectangle(batch, x, y, thickness, height, color);
        fillRectangle(batch, x + width - thickness, y, thickness, height, color);
        fillRectangle(batch, x, y + height - thickness, width, thickness, color);
    }

    public static void fillRectangle(SpriteBatch batch, float x, float y, float width, float height, Color color) {
        initTextureIfAbsent();

        // backup color
        Color backupColor = batch.getColor();

        // set rectangle color
        batch.setColor(color);

        batch.draw(textureRegion, x, y, width, height);

        // reset color
        batch.setColor(backupColor);
    }

    /*
     * public static void drawLine (SpriteBatch batch, float x1, float y1, float
     * x2, float y2, Color color) { initTextureIfAbsent();
     * 
     * float thickness = 2;
     * 
     * if (x1 > x2) { //switch values float c = x2; x2 = x1; x1 = c; }
     * 
     * if (y1 > y2) { //switch values float c = y2; y2 = y1; y1 = c; }
     * 
     * float dx = x2 - x1; float dy = y2 - y1;
     * 
     * if (dx < 1) { dx = 1; }
     * 
     * if (dy < 1) { dy = 1; }
     * 
     * float dist = (float)Math.sqrt(dx*dx + dy*dy); float rad = (float)
     * Math.atan2(dy, dx); float angle = (float) Math.toDegrees(rad);
     * 
     * affine2.setToTranslation(x1, y1); affine2.setToRotation(angle);
     * 
     * //backup color Color backupColor = batch.getColor();
     * 
     * batch.setColor(color);
     * 
     * System.out.println("draw line, x: " + x1 + ", y: " + y1 + ", width: " +
     * dx + ", height: " + dy + ".");
     * 
     * batch.draw(textureRegion, dx * thickness, dy * thickness, affine2);
     * //batch.draw(pixelTexture, Math.round(x1), Math.round(y1), dist, 0, 0,
     * rad);
     * 
     * //reset color batch.setColor(backupColor); }
     */

    protected static void initTextureIfAbsent() {
        if (pixelTexture == null) {
            // create pixmap
            Pixmap pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);

            // draw one pixel
            pixmap.setColor(1, 1, 1, 1);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());

            pixelTexture = new Texture(pixmap);

            // new Texture(Gdx.files.internal("utils/onepixel.png"));
            // new Texture(pixmap);

            /*
             * if (!pixelTexture.isManaged()) { throw new
             * IllegalStateException("Could found utils/onepixel.png in resource directory."
             * ); }
             */

            // create texture region
            textureRegion = new TextureRegion(pixelTexture, pixelTexture.getWidth(), pixelTexture.getHeight());

            // cleanup pixmap
            pixmap.dispose();
        }
    }

}
