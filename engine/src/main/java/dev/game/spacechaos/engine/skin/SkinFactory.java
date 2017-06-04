package dev.game.spacechaos.engine.skin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Justin on 06.02.2017.
 */
public class SkinFactory {

    public static Skin createSkin(final String atlasFile, final String jsonFile) {
        Skin skin = new Skin();

        // load texture atlas
        TextureAtlas atlas = new TextureAtlas(Gdx.files.absolute(atlasFile));
        skin.addRegions(atlas);

        // load skin json file
        skin.load(Gdx.files.absolute(jsonFile));

        return skin;
    }

    public static Skin createSkin(final String jsonFile) {
        Skin skin = new Skin(Gdx.files.absolute(jsonFile));
        skin.getAtlas().getTextures().iterator().next().setFilter(Texture.TextureFilter.Nearest,
                Texture.TextureFilter.Nearest);
        skin.getFont("default-font").getData().markupEnabled = true;
        float scale = 1;
        skin.getFont("default-font").getData().setScale(scale);

        return skin;
    }

}
