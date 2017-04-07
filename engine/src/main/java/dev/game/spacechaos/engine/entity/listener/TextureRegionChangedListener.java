package dev.game.spacechaos.engine.entity.listener;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Justin on 11.02.2017.
 */
public interface TextureRegionChangedListener {

    public void onTextureRegionChanged(TextureRegion oldTextureRegion, TextureRegion newTextureRegion);

}
