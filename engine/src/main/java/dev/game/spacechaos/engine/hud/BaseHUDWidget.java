package dev.game.spacechaos.engine.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 08.02.2017.
 */
public abstract class BaseHUDWidget implements HUDWidget {

    protected float x = 0;
    protected float y = 0;
    protected float width = 100;
    protected float height = 100;

    protected float groupX = 0;
    protected float groupY = 0;

    @Override
    public void drawLayer1(GameTime time, ShapeRenderer shapeRenderer) {

    }

    @Override
    public void drawLayer2(GameTime time, SpriteBatch batch) {

    }

    public float getX() {
        return this.x + groupX;
    }

    public float getY() {
        return this.y + groupY;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public void setDimension(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void onMoveGroup(float groupX, float groupY) {
        this.groupX = groupX;
        this.groupY = groupY;
    }

    protected boolean isInner(float mouseX, float mouseY) {
        if (mouseX >= getX() && mouseX <= (getX() + getWidth())) {
            if (mouseY >= getY() && mouseY <= (getY() + getHeight())) {
                return true;
            }
        }

        return false;
    }

    protected boolean isMouseInner(BaseGame game) {
        // get relation between current window size and original viewport size
        float a = Gdx.graphics.getWidth() / 1280f;
        float b = Gdx.graphics.getHeight() / 720f;

        // get mouse coordinates
        float mouseX = Gdx.input.getX() / a;
        float mouseY = game.getViewportHeight() - (Gdx.input.getY() / b);

        return isInner(mouseX, mouseY);
    }

    @Override
    public void dispose() {
        //
    }

}
