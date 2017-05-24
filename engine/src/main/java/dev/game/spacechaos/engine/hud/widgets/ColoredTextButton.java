package dev.game.spacechaos.engine.hud.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.hud.BaseHUDWidget;
import dev.game.spacechaos.engine.hud.ClickListener;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.SpriteBatcherUtils;

/**
 * Created by Justin on 17.04.2017.
 */
public class ColoredTextButton extends BaseHUDWidget {

    protected String text = "";
    protected BitmapFont font = null;

    protected Color color = Color.ORANGE;
    protected Color hoverColor = Color.YELLOW;

    protected boolean hovered = false;
    protected boolean isClicked = false;
    protected ClickListener clickListener = null;

    public ColoredTextButton (String text, BitmapFont font) {
        this.text = text;
        this.font = font;
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        //get mouse coordinates
        float mouseX = Gdx.input.getX();
        float mouseY = game.getViewportHeight() - Gdx.input.getY();

        //check if mouse is inner button
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

            //check if user has released button
            if (oldClicked == true) {
                //user has clicked button
                if (clickListener != null) {
                    clickListener.onClick();
                }
            }
        }
    }

    @Override
    public void drawLayer0(GameTime time, SpriteBatch batch) {
        //draw background texture
        if (hovered) {
            SpriteBatcherUtils.fillRectangle(batch, getX(), getY(), getWidth(), getHeight(), this.hoverColor);
        } else {
            SpriteBatcherUtils.fillRectangle(batch, getX(), getY(), getWidth(), getHeight(), this.color);
        }

        float paddingBottom = (this.height / 2) - 32;

        //draw button text
        this.font.draw(batch, text, x + 50/* + 80*/, y + 40);
    }

    public void setBackgroundColor (Color color) {
        this.color = color;
    }

    public void setBackgroundHoverColor (Color color) {
        this.hoverColor = color;
    }

    protected boolean isInner (float mouseX, float mouseY) {
        if (mouseX >= getX() && mouseX <= (getX() + getWidth())) {
            if (mouseY >= getY() && mouseY <= (getY() + getHeight())) {
                return true;
            }
        }

        return false;
    }

    public void setClickListener (ClickListener listener) {
        this.clickListener = listener;
    }

}
