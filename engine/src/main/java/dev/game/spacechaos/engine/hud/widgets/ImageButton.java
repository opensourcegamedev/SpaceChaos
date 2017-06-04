package dev.game.spacechaos.engine.hud.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.hud.BaseHUDWidget;
import dev.game.spacechaos.engine.hud.ClickListener;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.SpriteBatcherUtils;

/**
 * Created by Justin on 24.05.2017.
 */
public class ImageButton extends BaseHUDWidget {

    protected boolean hovered = false;
    protected boolean isClicked = false;
    protected ClickListener clickListener = null;

    protected Texture texture = null;
    protected Texture hoverTexture = null;

    public ImageButton(Texture texture, Texture hoverTexture) {
        this.texture = texture;
        this.hoverTexture = hoverTexture;
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        // get mouse coordinates
        float mouseX = Gdx.input.getX();
        float mouseY = game.getViewportHeight() - Gdx.input.getY();

        // check if mouse is inner button
        if (isMouseInner(game)) {
            this.hovered = true;
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
        // draw background texture
        if (hovered) {
            batch.draw(this.hoverTexture, getX(), getY(), getWidth(), getHeight());
        } else {
            batch.draw(this.texture, getX(), getY(), getWidth(), getHeight());
        }
    }

    public void setBackgroundTexture(Texture texture) {
        this.texture = texture;
    }

    public void setBackgroundHoverTexture(Texture hoverTexture) {
        this.hoverTexture = hoverTexture;
    }

    protected boolean isInner(float mouseX, float mouseY) {
        if (mouseX >= getX() && mouseX <= (getX() + getWidth())) {
            if (mouseY >= getY() && mouseY <= (getY() + getHeight())) {
                return true;
            }
        }

        return false;
    }

    public void setClickListener(ClickListener listener) {
        this.clickListener = listener;
    }

}
