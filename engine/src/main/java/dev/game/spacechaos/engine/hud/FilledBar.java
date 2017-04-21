package dev.game.spacechaos.engine.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 08.02.2017.
 */
public class FilledBar extends BaseHUDWidget {

    protected String text = "";

    protected Color backgroundColor = Color.GREEN;
    protected Color foregroundColor = Color.RED;
    protected Color textColor = Color.WHITE;

    protected float paddingLeft = 0;
    protected float paddingRight = 0;
    protected float paddingTop = 0;
    protected float paddingBottom = 0;

    protected BitmapFont font = null;
    protected float percent = 0;
    protected float value = 0;
    protected float maxValue = 100;

    protected float textPaddingBottom = 28;

    public FilledBar (BitmapFont font) {
        this.font = font;
    }

    public void update (BaseGame game, GameTime time) {
        //calculate percent
        this.percent = this.value / this.maxValue;
    }

    @Override public void drawLayer0(GameTime time, SpriteBatch batch) {

    }

    @Override public void drawLayer1(GameTime time, ShapeRenderer shapeRenderer) {
        //draw background
        shapeRenderer.setColor(this.backgroundColor);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());

        float maxWidth = getWidth() - (paddingLeft + paddingRight);
        float maxHeight = getHeight() - (paddingTop + paddingBottom);

        float barWidth = maxWidth * percent;
        float barHeight = maxHeight;

        //draw foreground
        shapeRenderer.setColor(this.foregroundColor);
        shapeRenderer.rect(getX() + paddingBottom, getY() + paddingLeft, barWidth, barHeight);
    }

    @Override public void drawLayer2(GameTime time, SpriteBatch batch) {
        float startX = getX() + (getWidth() / 2) - (this.text.length() * this.font.getSpaceWidth());
        float startY = getY() + (getHeight() / 2) - this.font.getLineHeight() + textPaddingBottom;

        //draw text
        this.font.setColor(this.textColor);
        this.font.draw(batch, this.text, startX, startY);
    }

    public void setFont (BitmapFont font) {
        this.font = font;
    }

    public String getText () {
        return this.text;
    }

    public void setText (String text) {
        this.text = text;
    }

    public float getValue () {
        return this.value;
    }

    public void setValue (float value) {
        this.value = value;
    }

    public float getMaxValue () {
        return this.maxValue;
    }

    public void setMaxValue (float maxValue) {
        this.maxValue = maxValue;
    }

    public float getPercent () {
        return this.percent * 100;
    }

    public void setBackgroundColor (Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setForegroundColor (Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

}
