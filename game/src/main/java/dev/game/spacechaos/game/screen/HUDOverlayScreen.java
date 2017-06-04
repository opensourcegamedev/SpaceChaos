package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.font.BitmapFontFactory;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.hud.FilledBar;
import dev.game.spacechaos.engine.hud.HUD;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.game.entities.component.combat.HPComponent;
import dev.game.spacechaos.game.entities.component.combat.StatComponent;
import dev.game.spacechaos.game.entities.factory.ProjectileFactory;

/**
 * The screen is on top of the {@link GameScreen}. It shows all the relevant
 * information for the player, e.g. the amount of torpedos you got left, the
 * time you already survived and the health-bar.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class HUDOverlayScreen extends BaseScreen {

	private static final String TORPEDO_IMAGE_PATH = "./data/images/entities/projectiles/torpedo.png";

	protected Texture torpedoTexture = null;

	private long startTime = 0;
	private long elapsedTime = 0;

	private BitmapFont font1 = null;
	private BitmapFont font2 = null;

	private String timeText = "";
	private String torpedoAmountText = "";
	private String scoreText = "";
	private long seconds = 0;

	private HPComponent hpComponent = null;

	private StatComponent statComponent = null;

	private HUD hud = null;
	private ShapeRenderer shapeRenderer = null;

	private FilledBar filledBar = null;

	@Override
	protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
		// generate fonts
		this.font1 = BitmapFontFactory.createFont("./data/font/spartakus/SparTakus.ttf", 48, Color.WHITE, Color.BLUE,
				3);
		this.font2 = BitmapFontFactory.createFont("./data/font/spartakus/SparTakus.ttf", 48, Color.RED, Color.WHITE, 3);

		// load & get assets
		assetManager.load(TORPEDO_IMAGE_PATH, Texture.class);
		assetManager.finishLoadingAsset(TORPEDO_IMAGE_PATH);
		this.torpedoTexture = assetManager.get(TORPEDO_IMAGE_PATH, Texture.class);

		// create new Head-Up-Display
		this.hud = new HUD();
		this.filledBar = new FilledBar(this.font1);
		this.filledBar.setPosition(game.getViewportWidth() - 400, game.getViewportHeight() - 85);
		this.filledBar.setDimension(155, 35);
		this.hud.addWidget(this.filledBar);

		this.shapeRenderer = new ShapeRenderer();
	}

	public void onResume() {
		// set start time
		this.startTime = System.currentTimeMillis();

		// get player entity and health component
		Entity playerEntity = game.getSharedData().get("playerEntity", Entity.class);
		this.hpComponent = playerEntity.getComponent(HPComponent.class);
		this.statComponent = playerEntity.getComponent(StatComponent.class);

		// set values and add listener to auto update values on change
		this.filledBar.setMaxValue(this.hpComponent.getMaxHP());
		this.filledBar.setValue(this.hpComponent.getCurrentHP());
		this.hpComponent.addUpdateListener((oldValue, newValue, maxValue) -> {
			// update values on filled bar (HUD)
			filledBar.setMaxValue(hpComponent.getMaxHP());
			filledBar.setValue(hpComponent.getCurrentHP());
		});

		// set HP bar colors
		this.filledBar.setBackgroundColor(Color.RED);
		this.filledBar.setForegroundColor(Color.GREEN);
	}

	public void onPause() {
		//
	}

	@Override
	public void update(ScreenBasedGame game, GameTime time) {

		this.torpedoAmountText = String.valueOf(ProjectileFactory.getTorpedosLeft());

		// calculate elapsed time
		this.elapsedTime = System.currentTimeMillis() - this.startTime;
		this.seconds = this.elapsedTime / 1000;
		long minutes = this.seconds / 60;
		this.seconds -= minutes * 60;

		// generate onscreen time text
		this.timeText = "";
		if (minutes < 10) {
			this.timeText = " " + minutes;
		} else {
			this.timeText = "" + minutes;
		}
		this.timeText = this.timeText + ":";
		if (this.seconds < 10) {
			this.timeText = this.timeText + "0" + this.seconds;
		} else {
			this.timeText = this.timeText + "" + this.seconds;
		}

		game.getSharedData().put("lastElapsedTimeText", this.timeText);

		this.scoreText = "SCORE: " + String.valueOf((statComponent.getEnemyKills() * 50) + (this.elapsedTime / 100));
		game.getSharedData().put("score", this.scoreText.substring(7));

		// update HUD
		this.hud.update(game, time);
	}

	@Override
	public void draw(GameTime time, SpriteBatch batch) {
		// set UI camera
		batch.setProjectionMatrix(game.getUICamera().combined);

		// draw time text
		if (seconds >= 0 && seconds <= 3 && this.elapsedTime > 5000) {
			this.font2.draw(batch, this.timeText, game.getViewportWidth() - 220, game.getViewportHeight() - 50);
		} else {
			this.font1.draw(batch, this.timeText, game.getViewportWidth() - 220, game.getViewportHeight() - 50);
		}

		// draw ammo hud
		this.font2.draw(batch, this.torpedoAmountText, game.getViewportWidth() - 220, 70);
		this.font1.draw(batch, this.torpedoAmountText, game.getViewportWidth() - 220, 70);

		// draw score
		font2.draw(batch, scoreText, 30, game.getViewportHeight() - 50);
		font1.draw(batch, scoreText, 30, game.getViewportHeight() - 50);

		// draw first (SpriteBatch) layer of HUD
		this.hud.drawLayer0(time, batch);
		batch.flush();
		batch.end();

		// draw second layer (ShapeRenderer) of HUD
		this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		this.shapeRenderer.setProjectionMatrix(game.getUICamera().combined);
		this.hud.drawLayer1(time, this.shapeRenderer);
		this.shapeRenderer.end();

		this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		this.shapeRenderer.setColor(Color.WHITE);
		this.shapeRenderer.rect(game.getViewportWidth() - 100, 20, 80, 80);

		this.shapeRenderer.end();

		// draw last (SpriteBatch) layer of HUD
		batch.begin();
		batch.setProjectionMatrix(game.getUICamera().combined);
		this.hud.drawLayer2(time, batch);

		// draw torpedo, if available
		boolean torpedoAvailable = game.getSharedData().get("can_shoot_torpedo", Boolean.class);

		if (torpedoAvailable) {
			batch.draw(this.torpedoTexture, game.getViewportWidth() - 91, 30);
		}
	}

	@Override
	public void destroy() {

	}

}
