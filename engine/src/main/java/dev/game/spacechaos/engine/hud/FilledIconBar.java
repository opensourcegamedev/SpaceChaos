package dev.game.spacechaos.engine.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Justin on 09.02.2017.
 */
public class FilledIconBar extends WidgetGroup {

    protected ImageWidget heartImageWidget = null;
    protected FilledBar filledBar = null;
    protected BitmapFont font = null;

    public FilledIconBar(Texture heartTexture, BitmapFont font) {
        super();

        this.font = font;

        // add heart icon
        this.heartImageWidget = new ImageWidget(heartTexture);
        this.heartImageWidget.setPosition(0, 0);
        this.addWidget(this.heartImageWidget);

        // add health widget
        this.filledBar = new FilledBar(this.font);
        this.filledBar.setPosition(40, 6);
        this.filledBar.setDimension(80, 20);
        this.addWidget(this.filledBar);
    }

    public void setMaxValue(float maxValue) {
        this.filledBar.setMaxValue(maxValue);
    }

    public float getValue() {
        return this.filledBar.getValue();
    }

    public void setValue(float value) {
        this.filledBar.setValue(value);
    }

    public float getPercent() {
        return this.filledBar.getPercent();
    }

    public String getText() {
        return this.filledBar.getText();
    }

    public void setText(String text) {
        this.filledBar.setText(text);
    }

    public FilledBar getFilledBar() {
        return this.filledBar;
    }

}
