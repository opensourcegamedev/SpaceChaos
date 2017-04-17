package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.font.BitmapFontFactory;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 11.04.2017.
 */
public class HUDOverlayScreen extends BaseScreen {

    protected long startTime = 0;
    protected long elapsedTime = 0;

    protected BitmapFont font1 = null;
    protected BitmapFont font2 = null;

    protected String timeText = "";
    protected long minutes = 0;
    protected long seconds = 0;

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //generate fonts
        this.font1 = BitmapFontFactory.createFont("./data/font/spartakus/SparTakus.ttf", 48, Color.WHITE, Color.BLUE, 3);
        this.font2 = BitmapFontFactory.createFont("./data/font/spartakus/SparTakus.ttf", 48, Color.RED, Color.WHITE, 3);
    }

    public void onResume () {
        //set start time
        this.startTime = System.currentTimeMillis();
    }

    public void onPause () {
        //
    }

    @Override
    public void update(ScreenBasedGame game, GameTime time) {
        //calculate elapsed time
        this.elapsedTime = System.currentTimeMillis() - this.startTime;

        //get seconds
        this.seconds = this.elapsedTime / 1000;

        //get minutes
        this.minutes = this.seconds / 60;

        //correct seconds
        this.seconds -= this.minutes * 60;

        //generate text
        this.timeText = "";

        if (this.minutes < 10) {
            this.timeText = " " + this.minutes;
        } else {
            this.timeText = "" + this.minutes;
        }

        this.timeText = this.timeText + ":";

        if (this.seconds < 10) {
            this.timeText = this.timeText + "0" + this.seconds;
        } else {
            this.timeText = this.timeText + "" + this.seconds;
        }

        game.getSharedData().put("lastElapsedTimeText", this.timeText);
    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {
        //set UI camera
        batch.setProjectionMatrix(game.getUICamera().combined);

        //draw time text
        if (seconds >= 0 && seconds <= 3 && this.elapsedTime > 5000) {
            this.font2.draw(batch, this.timeText, game.getViewportWidth() - 220, game.getViewportHeight() - 50);
        } else {
            this.font1.draw(batch, this.timeText, game.getViewportWidth() - 220, game.getViewportHeight() - 50);
        }
    }

    @Override
    public void destroy() {

    }

}
