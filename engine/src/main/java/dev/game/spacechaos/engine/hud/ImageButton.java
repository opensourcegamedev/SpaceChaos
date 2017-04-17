package dev.game.spacechaos.engine.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 08.02.2017.
 */
@Deprecated
public class ImageButton {


    protected float x = 0;
    protected float y = 0;
    protected float width = 200;
    protected float height = 100;

    protected boolean hovered = false;
    protected boolean isClicked = false;

    protected ClickListener clickListener = null;

    //assets
    protected Texture bgTexture = null;
    protected Texture hoverTexture = null;
    protected BitmapFont font = null;
    protected Texture iconTexture = null;
    protected String text = "";

    public ImageButton(Texture bgTexture, Texture hoverTexture, BitmapFont font, String text) {
        this.bgTexture = bgTexture;
        this.hoverTexture = hoverTexture;
        this.font = font;
        this.text = text;
    }

    public void setPosition (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update (BaseGame game, GameTime time) {
        //get mouse coordinates
        float mouseX = Gdx.input.getX();
        float mouseY = game.getViewportHeight() - Gdx.input.getY();

        //check, if mouse is inner button
        if (isInner(mouseX, mouseY)) {
            this.hovered = true;
        } else {
            hovered = false;
        }

        boolean oldClicked = this.isClicked;

        if (isInner(mouseX, mouseY) && Gdx.input.isTouched()) {
            this.isClicked = true;
        } else {
            this.isClicked = false;

            //check, if user has released button
            if (oldClicked == true) {
                //user has clicked button
                if (clickListener != null) {
                    clickListener.onClick();
                }
            }
        }
    }

    public void draw (GameTime time, SpriteBatch batch) {
        //draw background texture
        if (hovered) {
            batch.draw(this.hoverTexture, x, y);
        } else {
            batch.draw(this.bgTexture, x, y);
        }

        float paddingBottom = (this.height / 2) - 32;

        //draw icon
        if (iconTexture != null) {
            batch.draw(iconTexture, x + 10, y + paddingBottom);
        }

        //draw button text
        this.font.draw(batch, text, x + 80, y + 60);
    }

    protected boolean isInner (float mouseX, float mouseY) {
        if (mouseX >= x && mouseX <= (x + width)) {
            if (mouseY >= y && mouseY <= (y + height)) {
                return true;
            }
        }

        return false;
    }

    public void setClickListener (ClickListener listener) {
        this.clickListener = listener;
    }

    public void setIcon (Texture iconTexture) {
        this.iconTexture = iconTexture;
    }

}
