package dev.game.spacechaos.engine.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

/**
 * Created by Justin on 12.02.2017.
 */
public class PixmapUtils {

    public static Pixmap fillFormWithConstantColor(Pixmap pixmap, Color fillColor) {
        // set fill color
        pixmap.setColor(fillColor);

        Color color = new Color();

        for (int x = 0; x < pixmap.getWidth(); x++) {
            for (int y = 0; y < pixmap.getHeight(); y++) {
                int colorInt = pixmap.getPixel(x, y);
                color.set(colorInt);

                // get color alpha value
                float alpha = color.a;

                if (alpha > 0) {
                    pixmap.setColor(fillColor);
                    pixmap.fillRectangle(x, y, 1, 1);
                } else {
                    pixmap.setColor(new Color(0, 0, 0, 0));
                    pixmap.fillRectangle(x, y, 1, 1);
                }
            }
        }

        return pixmap;
    }

}
