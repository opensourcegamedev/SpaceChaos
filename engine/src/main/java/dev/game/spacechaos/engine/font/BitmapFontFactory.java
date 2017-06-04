package dev.game.spacechaos.engine.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Justin on 06.02.2017.
 */
public class BitmapFontFactory {

    public static BitmapFont createFont(String fontPath, int size, Color color) {
        // load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.absolute(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;

        BitmapFont font48 = generator.generateFont(parameter);
        generator.dispose();

        return font48;
    }

    public static BitmapFont createFont(String fontPath, int size, Color color, Color borderColor, int borderWidth) {
        // load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.absolute(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        // https://github.com/libgdx/libgdx/wiki/Gdx-freetype
        parameter.size = size;
        parameter.borderColor = borderColor;
        parameter.borderWidth = borderWidth;
        parameter.color = color;

        BitmapFont font48 = generator.generateFont(parameter);
        generator.dispose();

        return font48;
    }

    public static BitmapFont createFont(FileHandle fileHandle, int size, Color color, Color borderColor,
            int borderWidth) {
        // load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fileHandle);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        // https://github.com/libgdx/libgdx/wiki/Gdx-freetype
        parameter.size = size;
        parameter.borderColor = borderColor;
        parameter.borderWidth = borderWidth;
        parameter.color = color;

        BitmapFont font48 = generator.generateFont(parameter);
        generator.dispose();

        return font48;
    }

}
