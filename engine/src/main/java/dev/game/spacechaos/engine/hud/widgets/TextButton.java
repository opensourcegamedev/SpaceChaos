package dev.game.spacechaos.engine.hud.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.hud.BaseHUDWidget;
import dev.game.spacechaos.engine.hud.ClickListener;
import dev.game.spacechaos.engine.sound.VolumeManager;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.SpriteBatcherUtils;

import java.awt.*;

/**
 * Created by Justin on 22.04.2017.
 */
public class TextButton extends BaseHUDWidget {

    protected Color bgColor = Color.FIREBRICK;// Color.CHARTREUSE
    protected Color hoverColor = Color.ORANGE;
    protected String text = "";
    protected ClickListener clickListener = null;
    protected boolean hovered = false;
    protected boolean isClicked = false;

    protected BitmapFont font = null;

    protected Sound hoverSound = null;

    // Text padding
    protected float paddingTop = 10;
    protected float paddingLeft = 20;

    public TextButton(String text, BitmapFont font, float x, float y) {
        this.text = text;
        setPosition(x, y);

        setDimension(200, 50);
        this.font = font;
    }

    public TextButton(String text, BitmapFont font) {
        this(text, font, 100, 100);
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        // check if mouse is inner
        if (isMouseInner(game)) {
            if (!hovered) {
                // mouse enter widget

                // play sound
                if (this.hoverSound != null) {
                    this.hoverSound.play(VolumeManager.getInstance().getEnvVolume());
                }
            }

            hovered = true;
        } else {
            hovered = false;
        }

        boolean oldClicked = this.isClicked;

        if (isMouseInner(game) && Gdx.input.isTouched()) {
            this.isClicked = true;
        } else {
            this.isClicked = false;

            // check if user has released button
            if (oldClicked == true) {
                // user has clicked button
                if (clickListener != null) {
                    clickListener.onClick();
                }
            }
        }
    }

    @Override
    public void drawLayer0(GameTime time, SpriteBatch batch) {
        // draw rectangle
        SpriteBatcherUtils.fillRectangle(batch, getX(), getY(), getWidth(), getHeight(),
                this.hovered ? this.hoverColor : this.bgColor);

        this.font.draw(batch, this.text, getX() + paddingLeft, getY() + getHeight() - paddingTop);
    }

    @Override
    public void drawLayer1(GameTime time, ShapeRenderer shapeRenderer) {
        //
    }

    @Override
    public void drawLayer2(GameTime time, SpriteBatch batch) {
        //
    }

    public void setHoverSound(Sound sound) {
        this.hoverSound = sound;
    }

    public void setClickListener(ClickListener listener) {
        this.clickListener = listener;
    }

}
