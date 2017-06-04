package dev.game.spacechaos.engine.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Justin on 15.02.2017.
 */
public class AnimationTextureUtils {

    public static Animation<TextureRegion> createAnimationFromTexture(Texture texture, float duration, int rows,
            int cols) {
        // split texture into texture regions
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / cols, texture.getHeight() / rows);

        TextureRegion[] frames = new TextureRegion[cols * rows];
        int index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        // create new animation
        Animation<TextureRegion> animation = new Animation<TextureRegion>(duration, frames);

        return animation;

    }

}
