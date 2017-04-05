package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.game.entities.EnemySpaceShuttle;
import dev.game.spacechaos.game.entities.PlayerSpaceShuttle;
import dev.game.spacechaos.game.entities.Projectile;
import dev.game.spacechaos.game.entities.SpaceShuttle;
import dev.game.spacechaos.game.skybox.SkyBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 28.03.2017.
 */
public class GameScreen extends BaseScreen {

    protected static final String BG_IMAGE_PATH = "./data/images/skybox/galaxy/galaxy+X.png";
    protected static final String SKYBOX_PLUS_X = "./data/images/skybox/galaxy/galaxy+X.png";
    protected static final String SKYBOX_MINUS_X = "./data/images/skybox/galaxy/galaxy-X.png";
    protected static final String SKYBOX_PLUS_Y = "./data/images/skybox/galaxy/galaxy+Y.png";
    protected static final String SKYBOX_MINUS_Y = "./data/images/skybox/galaxy/galaxy-Y.png";
    protected static final String SHUTTLE_IMAGE_PATH = "./data/images/entities/starships/spaceshuttle.png";
    protected static final String SHUTTLE2_IMAGE_PATH = "./data/images/entities/starships/spaceshuttledark.png";
    //protected static final String PROJECTILE_IMAGE_PATH = "./data/images/entities/projectiles/projectile1.png";

    //background image
    protected Texture bgTexture = null;

    protected SkyBox skyBox = null;

    //spaceshuttle
    protected SpaceShuttle spaceShuttle;

    protected List<SpaceShuttle> enemySpaceShuttles = new ArrayList<>();
    //protected List<Projectile> playerProjectiles = new ArrayList<>(); //one list for player and enemy projectiles?

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        //load all neccessary assets

        //load skybox
        assetManager.load(BG_IMAGE_PATH, Texture.class);
        assetManager.load(SKYBOX_MINUS_X, Texture.class);
        assetManager.load(SKYBOX_PLUS_X, Texture.class);
        assetManager.load(SKYBOX_MINUS_Y, Texture.class);
        assetManager.load(SKYBOX_PLUS_Y, Texture.class);
        assetManager.load(SHUTTLE_IMAGE_PATH, Texture.class);
        assetManager.load(SHUTTLE2_IMAGE_PATH, Texture.class);
        //assetManager.load(PROJECTILE_IMAGE_PATH, Texture.class);

        //wait while assets are loading
        assetManager.finishLoadingAsset(BG_IMAGE_PATH);
        assetManager.finishLoadingAsset(SKYBOX_MINUS_X);
        assetManager.finishLoadingAsset(SKYBOX_PLUS_X);
        assetManager.finishLoadingAsset(SKYBOX_MINUS_Y);
        assetManager.finishLoadingAsset(SKYBOX_PLUS_Y);
        assetManager.finishLoadingAsset(SHUTTLE_IMAGE_PATH);
        assetManager.finishLoadingAsset(SHUTTLE2_IMAGE_PATH);
        //assetManager.finishLoadingAsset(PROJECTILE_IMAGE_PATH);

        System.out.println("test");
        System.out.println("test");

        //get asset
        this.bgTexture = assetManager.get(BG_IMAGE_PATH, Texture.class);

        Texture skyBox1 = assetManager.get(SKYBOX_MINUS_X);
        Texture skyBox2 = assetManager.get(SKYBOX_PLUS_X);
        Texture skyBox3 = assetManager.get(SKYBOX_MINUS_Y);
        Texture skyBox4 = assetManager.get(SKYBOX_PLUS_Y);

        //create space shuttle img, x, y
        spaceShuttle = new PlayerSpaceShuttle(assetManager.get(SHUTTLE_IMAGE_PATH, Texture.class), game.getViewportWidth() / 2, game.getViewportHeight() / 2);

        //create skybox
        this.skyBox = new SkyBox(new Texture[]{skyBox1, skyBox2, skyBox3, skyBox4}, game.getViewportWidth(), game.getViewportHeight());

        //TODO: add some enemy space shuttles, check if its not in close proximity to any shuttle
        for (int amount = 0; amount < 2; amount++) {
            float x = (float) Math.random() * game.getViewportWidth();
            float y = (float) Math.random() * game.getViewportHeight();
            enemySpaceShuttles.add(new EnemySpaceShuttle(assetManager.get(SHUTTLE2_IMAGE_PATH, Texture.class), x, y, spaceShuttle));
        }
    }

    @Override
    public void onResume() {
        //
    }

    @Override
    public void onPause() {
        //
    }

    @Override
    public void update(ScreenBasedGame game, GameTime time) {
        //get camera
        CameraWrapper camera = game.getCamera();

        //update shuttle
        spaceShuttle.update(game, game.getCamera(), time);

        //update enemy space shuttles
        this.enemySpaceShuttles.stream().forEach(shuttle -> {
            shuttle.update(game, game.getCamera(), time);
        });
    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {
        //draw background skybox image
        //batch.draw(this.bgTexture, 0, 0, game.getViewportWidth(), game.getViewportHeight());

        //draw skybox
        this.skyBox.draw(time, game.getCamera(), batch);

        //draw enemy space shuttles
        this.enemySpaceShuttles.stream().forEach(shuttle -> {
            shuttle.draw(time, game.getCamera(), batch);
        });

        //draw shuttle
        spaceShuttle.draw(time, game.getCamera(), batch);
    }

    @Override
    public void destroy() {
        assetManager.unload(BG_IMAGE_PATH);
    }

}
