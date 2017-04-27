package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.listener.UpdateHPListener;
import dev.game.spacechaos.engine.font.BitmapFontFactory;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.hud.FilledBar;
import dev.game.spacechaos.engine.hud.HUD;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.game.entities.component.combat.HPComponent;
import dev.game.spacechaos.game.entities.factory.ProjectileFactory;

/**
 * Created by Justin on 11.04.2017.
 */
public class HUDOverlayScreen extends BaseScreen {

    protected long startTime = 0;
    protected long elapsedTime = 0;

    protected BitmapFont font1 = null;
    protected BitmapFont font2 = null;

    protected String timeText = "";
    protected String torpedoAmountText = "";
    protected long minutes = 0;
    protected long seconds = 0;

    //player entity and health component
    protected Entity playerEntity = null;
    protected HPComponent hpComponent = null;

    protected HUD hud = null;
    protected ShapeRenderer shapeRenderer = null;

    protected FilledBar filledBar = null;

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //generate fonts
        this.font1 = BitmapFontFactory.createFont("./data/font/spartakus/SparTakus.ttf", 48, Color.WHITE, Color.BLUE, 3);
        this.font2 = BitmapFontFactory.createFont("./data/font/spartakus/SparTakus.ttf", 48, Color.RED, Color.WHITE, 3);

        //create new Head-Up-Display
        this.hud = new HUD();
        this.filledBar = new FilledBar(this.font1);
        this.filledBar.setPosition(game.getViewportWidth() - 380, game.getViewportHeight() - 90);
        this.filledBar.setDimension(100, 40);
        this.hud.addWidget(this.filledBar);

        this.shapeRenderer = new ShapeRenderer();
    }

    public void onResume () {
        //set start time
        this.startTime = System.currentTimeMillis();

        //get player entity and health component
        this.playerEntity = game.getSharedData().get("playerEntity", Entity.class);
        this.hpComponent = this.playerEntity.getComponent(HPComponent.class);

        //set values and add listener to auto update values on change
        this.filledBar.setMaxValue(this.hpComponent.getMaxHP());
        this.filledBar.setValue(this.hpComponent.getCurrentHP());
        this.hpComponent.addUpdateListener(new UpdateHPListener() {
            @Override
            public void onHPUpdate(float oldValue, float newValue, float maxValue) {
                //update values on filled bar (HUD)
                filledBar.setMaxValue(hpComponent.getMaxHP());
                filledBar.setValue(hpComponent.getCurrentHP());
            }
        });

        //set HP bar colors
        this.filledBar.setBackgroundColor(Color.RED);
        this.filledBar.setForegroundColor(Color.GREEN);
    }

    public void onPause () {
        //
    }

    @Override
    public void update(ScreenBasedGame game, GameTime time) {

        this.torpedoAmountText = String.valueOf(ProjectileFactory.getTorpedosLeft());

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

        //update HUD
        this.hud.update(game, time);
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

        //draw ammo hud
        this.font2.draw(batch, this.torpedoAmountText, game.getViewportWidth() - 220, 70);
        this.font1.draw(batch, this.torpedoAmountText, game.getViewportWidth() - 220, 70);

        //draw first (SpriteBatch) layer of HUD
        this.hud.drawLayer0(time, batch);
        batch.flush();
        batch.end();

        //draw second layer (ShapeRenderer) of HUD
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setProjectionMatrix(game.getUICamera().combined);
        this.hud.drawLayer1(time, this.shapeRenderer);
        this.shapeRenderer.end();

        //draw last (SpriteBatch) layer of HUD
        batch.begin();
        batch.setProjectionMatrix(game.getUICamera().combined);
        this.hud.drawLayer2(time, batch);
    }

    @Override
    public void destroy() {

    }

}
