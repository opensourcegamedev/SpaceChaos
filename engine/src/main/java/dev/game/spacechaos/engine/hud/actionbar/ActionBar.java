package dev.game.spacechaos.engine.hud.actionbar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.hud.BaseHUDWidget;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 05.03.2017.
 */
public class ActionBar extends BaseHUDWidget {

    // item specific data
    protected float itemWidth = 0;
    protected float itemHeight = 0;
    protected int rows = 1;
    protected int cols = 1;
    protected float paddingLeft = 5;
    protected float paddingRight = 5;
    protected float paddingTop = 5;
    protected float paddingBottom = 5;

    // items
    protected ActionBarItem[][] items = null;
    protected float startX = 50;
    protected float startY = 0;

    // blank item
    protected ActionBarItem blankItem = null;

    // textures
    protected Texture bgTexture = null;
    protected BitmapFont font = null;

    /**
     * default constructor
     */
    public ActionBar(float itemWidth, float itemHeight, int rows, int cols, Texture bgTexture, Texture blankTexture,
            BitmapFont font) {
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        this.rows = rows;
        this.cols = cols;

        this.bgTexture = bgTexture;

        // create new blank item
        this.blankItem = new ActionBarItem(blankTexture, font);

        this.items = new ActionBarItem[rows][cols];

        float lastX = startX;
        float lastY = startY;

        for (int i = 0; i < rows; i++) {
            lastY += paddingBottom;

            for (int j = 0; j < cols; j++) {
                // set blank item
                items[i][j] = new ActionBarItem(blankTexture, font);

                lastX += paddingLeft;

                // calculate position and dimension
                items[i][j].setDimension(itemWidth, itemHeight);
                items[i][j].onMoveGroup(getX(), getY());
                items[i][j].setPosition(lastX, lastY);

                lastX += itemWidth + paddingRight;
            }

            lastY += itemHeight + paddingTop;
        }

        // calculate dimension
        float width = bgTexture.getWidth();
        float height = bgTexture.getHeight();
        setDimension(width, height);

        this.font = font;
    }

    public ActionBarItem getItem(int x, int y) {
        if (x < 0 || x >= cols) {
            throw new IllegalArgumentException("x parameter isnt in range (0 <= x < cols).");
        }

        if (y < 0 || y >= rows) {
            throw new IllegalArgumentException("y parameter isnt in range (0 <= y < rows).");
        }

        return this.items[y][x];
    }

    public void setItem(int x, int y, ActionBarItem item) {
        if (x < 0 || x >= cols) {
            throw new IllegalArgumentException("x parameter isnt in range (0 <= x < cols).");
        }

        if (y < 0 || y >= rows) {
            throw new IllegalArgumentException("y parameter isnt in range (0 <= y < rows).");
        }

        items[y][x] = item;
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        // update items
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // update item
                if (items[i][j] != null) {
                    items[i][j].update(game, time);
                }
            }
        }
    }

    @Override
    public void drawLayer0(GameTime time, SpriteBatch batch) {
        // draw background
        batch.draw(this.bgTexture, getX(), getX());

        // draw items
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // update item
                if (items[i][j] != null) {
                    items[i][j].drawLayer0(time, batch);
                }
            }
        }
    }

    @Override
    public void drawLayer1(GameTime time, ShapeRenderer shapeRenderer) {
        // draw items
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // update item
                if (items[i][j] != null) {
                    items[i][j].drawLayer1(time, shapeRenderer);
                }
            }
        }
    }

    @Override
    public void drawLayer2(GameTime time, SpriteBatch batch) {
        // draw items
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // update item
                if (items[i][j] != null) {
                    items[i][j].drawLayer2(time, batch);
                }
            }
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);

        // update item positions
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // update item position
                if (items[i][j] != null) {
                    items[i][j].onMoveGroup(x, y);
                }
            }
        }
    }

}
