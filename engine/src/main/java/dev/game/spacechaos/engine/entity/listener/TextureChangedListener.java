package dev.game.spacechaos.engine.entity.listener;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Justin on 11.02.2017.
 */
public interface TextureChangedListener {

    public void onTextureChanged(Texture oldTexture, Texture newTexture);

}
