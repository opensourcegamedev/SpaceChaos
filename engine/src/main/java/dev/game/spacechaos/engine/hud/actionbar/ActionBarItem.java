package dev.game.spacechaos.engine.hud.actionbar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.hud.BaseHUDWidget;
import dev.game.spacechaos.engine.input.InputStates;
import dev.game.spacechaos.engine.time.CooldownTimer;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.MouseUtils;

/**
 * Created by Justin on 05.03.2017.
 */
public class ActionBarItem extends BaseHUDWidget {

    protected String key = "N/A";
    protected Texture texture = null;
    protected BitmapFont font = null;

    protected float textPaddingX = 12;
    protected float textPaddingY = 16;

    protected boolean hovered = false;
    protected Color hoverColor = new Color(0.7f, 0.7f, 1.0f, 0.8f);
    protected ActionCommand command = null;
    protected boolean clicked = false;
    protected boolean isHoverable = true;
    protected boolean clickable = true;

    protected CustomHoverAdapter customHoverAdapter = null;
    protected CustomClickAdapter customClickAdapter = new CustomClickAdapter() {
        @Override
        public boolean isPressed(BaseGame game, ActionBarItem item, GameTime time) {
            Vector3 mousePos = MouseUtils.getMousePositionWithCamera(game.getUICamera());
            float mouseX = mousePos.x;
            float mouseY = mousePos.y;

            return InputStates.isLeftMouseButtonPressed() && isInner(mouseX, mouseY);
        }
    };

    // cooldown timer
    protected CooldownTimer cooldownTimer = null;
    protected boolean useCooldown = false;

    public ActionBarItem(Texture texture, BitmapFont font) {
        this.texture = texture;
        this.font = font;

        this.setDimension(texture.getWidth(), texture.getHeight());

        // create new cooldown timer
        this.cooldownTimer = new CooldownTimer(0);
    }

    public boolean isInner(float mouseX, float mouseY) {
        float x = getX();
        float y = getY();

        if (x <= mouseX && mouseX <= x + getWidth()) {
            if (y <= mouseY && mouseY <= y + getHeight()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        // update cooldown timer
        this.cooldownTimer.update(time);

        // check if hovered
        hovered = false;

        Vector3 mousePos = MouseUtils.getMousePositionWithCamera(game.getUICamera());
        float mouseX = mousePos.x;
        float mouseY = mousePos.y;

        if (isInner(mouseX, mouseY)) {
            hovered = true;
        }

        if (!hovered && this.customHoverAdapter != null) {
            hovered = this.customHoverAdapter.isHovered(game, this, time);
        }

        if (!clicked && this.customClickAdapter != null) {
            if (this.customClickAdapter.isPressed(game, this, time)) {
                clicked = true;
            }
        }

        if (clicked && !this.customClickAdapter.isPressed(game, this, time)) {
            onClick();
            clicked = false;
        }

        /*
         * if (hovered) { if (this.customClickAdapter.isPressed(game, this,
         * time) && clickable) { clicked = true; } else { if (clicked) {
         * //execute onClick action this.onClick(); }
         * 
         * clicked = false; } } else { if (clicked) { //execute onClick action
         * this.onClick(); }
         * 
         * clicked = false; }
         * 
         * if (!isHoverable) { hovered = false; }
         */
    }

    @Override
    public void drawLayer0(GameTime time, SpriteBatch batch) {
        if (hovered) {
            batch.setColor(hoverColor);
        }

        batch.draw(this.texture, getX(), getY(), getWidth(), getHeight());

        batch.setColor(1, 1, 1, 1);

        // draw key text
        font.draw(batch, key.toUpperCase(), getX() + getWidth() - (textPaddingX * key.length()), getY() + textPaddingY);
    }

    public void setTexture(Texture texture) {
        Texture oldTexture = this.texture;

        this.texture = texture;

        if (oldTexture != null) {
            oldTexture.dispose();
        }
    }

    public void setKeyText(String key) {
        this.key = key;
    }

    public void setActionCommand(ActionCommand command) {
        this.command = command;
    }

    public void removeActionCommand() {
        this.command = null;
    }

    public void onClick() {
        // check if cooldown timer is running
        if (useCooldown && this.cooldownTimer.isRunning()) {
            // we cannot execute action, because cooldown timer is running
            return;
        }

        if (this.clickable && this.command != null) {
            this.command.execute();

            // check if action requires an cooldown timer
            if (useCooldown) {
                // start cooldown timer
                this.cooldownTimer.start();
            }
        }

        System.out.println("click.");
    }

    public boolean isHovered() {
        return this.hovered;
    }

    public void setHoverable(boolean hoverable) {
        this.isHoverable = hoverable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public void setCustomHoverAdapter(CustomHoverAdapter hoverAdapter) {
        this.customHoverAdapter = hoverAdapter;
    }

    public void removeCustomHoverAdapter() {
        this.customHoverAdapter = null;
    }

    public void setCustomClickAdapter(CustomClickAdapter clickAdapter) {
        this.customClickAdapter = clickAdapter;
    }

    public void removeCustomClickAdapter() {
        this.customClickAdapter = null;
    }

    /**
     * set cooldown timer interval in milliseconds
     *
     * @param interval
     *            interval in milliseconds
     */
    public void setCooldownTimerInterval(long interval) {
        if (interval <= 0) {
            this.useCooldown = false;
        }

        this.cooldownTimer.setInterval(interval);
    }

}
