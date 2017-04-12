package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.collision.CollisionManager;
import dev.game.spacechaos.engine.entity.component.collision.impl.DefaultCollisionManager;
import dev.game.spacechaos.engine.entity.impl.ECS;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.game.entities.factory.EnemyFactory;
import dev.game.spacechaos.game.entities.factory.MeteoritFactory;
import dev.game.spacechaos.game.entities.factory.PlayerFactory;
import dev.game.spacechaos.game.skybox.SkyBox;

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
    protected static final String ASTEROID1_IMAGE_PATH = "./data/images/entities/asteroids/asteroid1_brown.png";

    protected static final String BACKGROUND_MUSIC_PATH = "./data/music/i-know-your-secret/I_know_your_secret.ogg";

    protected static final Color COLLISION_BOX_COLOR = Color.BLUE;

    //background image
    protected Texture bgTexture = null;

    protected SkyBox skyBox = null;

    //entity component system
    protected EntityManager ecs = null;

    //player entity
    protected Entity playerEntity = null;

    protected Music music = null;

    protected ShapeRenderer shapeRenderer = null;

    protected CollisionManager collisionManager = null;

    public GameScreen() {
        //create shape renderer
        this.shapeRenderer = new ShapeRenderer();
    }

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

        //load collision objects
        assetManager.load(ASTEROID1_IMAGE_PATH, Texture.class);

        //load background music
        assetManager.load(BACKGROUND_MUSIC_PATH, Music.class);

        //wait while assets are loading
        assetManager.finishLoadingAsset(BG_IMAGE_PATH);
        assetManager.finishLoadingAsset(SKYBOX_MINUS_X);
        assetManager.finishLoadingAsset(SKYBOX_PLUS_X);
        assetManager.finishLoadingAsset(SKYBOX_MINUS_Y);
        assetManager.finishLoadingAsset(SKYBOX_PLUS_Y);
        assetManager.finishLoadingAsset(SHUTTLE_IMAGE_PATH);
        assetManager.finishLoadingAsset(SHUTTLE2_IMAGE_PATH);
        //assetManager.finishLoadingAsset(PROJECTILE_IMAGE_PATH);
        assetManager.finishLoadingAsset(ASTEROID1_IMAGE_PATH);
        assetManager.finishLoadingAsset(BACKGROUND_MUSIC_PATH);

        //create new entity component system
        this.ecs = new ECS(game);

        //create collision manager
        this.collisionManager = new DefaultCollisionManager(this.ecs);

        //get asset
        this.bgTexture = assetManager.get(BG_IMAGE_PATH, Texture.class);

        Texture skyBox1 = assetManager.get(SKYBOX_MINUS_X);
        Texture skyBox2 = assetManager.get(SKYBOX_PLUS_X);
        Texture skyBox3 = assetManager.get(SKYBOX_MINUS_Y);
        Texture skyBox4 = assetManager.get(SKYBOX_PLUS_Y);

        //get background music
        this.music = assetManager.get(BACKGROUND_MUSIC_PATH);

        //create skybox
        this.skyBox = new SkyBox(new Texture[]{skyBox1, skyBox2, skyBox3, skyBox4}, game.getViewportWidth(), game.getViewportHeight());

        //create new player entity and add to entity-component-system
        this.playerEntity = PlayerFactory.createPlayer(this.ecs, game.getViewportWidth() / 2, game.getViewportHeight() / 2, assetManager.get(SHUTTLE_IMAGE_PATH, Texture.class));
        this.ecs.addEntity(this.playerEntity);

        //add a specific amount of enemy shuttles
        int amount = 5;
        float[][] positions = new float[amount + 1][2]; //player + enemy positions
        positions[0][0] = this.playerEntity.getComponent(PositionComponent.class).getMiddleX();
        positions[0][1] = this.playerEntity.getComponent(PositionComponent.class).getMiddleY();
        for (int n = 0; n < amount; n++) {
            //calculate random enemy position near player
            float x = (float) Math.random() * game.getViewportWidth();
            float y = (float) Math.random() * game.getViewportHeight();

            boolean validPos = false;
            while (!validPos) {
                for (int k = 0; k < n + 1; k++) {
                    if (Math.abs(positions[k][0] - x) > 300 ||
                            Math.abs(positions[k][1] - y) > 300) {
                        if (k == n) { //all positions are valid
                            positions[k + 1][0] = x;
                            positions[k + 1][1] = y;
                            validPos = true;
                        }
                    } else {
                        x = (float) Math.random() * game.getViewportWidth();
                        y = (float) Math.random() * game.getViewportHeight();
                        break; //recheck if valid
                    }
                }
            }

            //create and add new enemy space shuttle to entity-component-system
            Entity enemyEntity = EnemyFactory.createEnemyShuttle(this.ecs, x, y, assetManager.get(SHUTTLE2_IMAGE_PATH, Texture.class), this.playerEntity);
            this.ecs.addEntity(enemyEntity);
        }

        //add some random meteorits
        for (int i = 0; i < 90; i++) {
            //calculate random enemy position near player
            float x = (float) Math.random() * game.getViewportWidth() * 3 - game.getViewportWidth();
            float y = (float) Math.random() * game.getViewportHeight() * 3 - game.getViewportHeight();
            //create and add new meteorit
            Entity entity = MeteoritFactory.createMeteorit(this.ecs, x, y, assetManager.get(ASTEROID1_IMAGE_PATH));
            this.ecs.addEntity(entity);
        }
    }

    @Override
    public void onResume() {
        //play background music
        this.music.setVolume(0.8f);
        this.music.play();
    }

    @Override
    public void onPause() {
        //stop music
        this.music.stop();
    }

    @Override
    public void update(ScreenBasedGame game, GameTime time) {
        //update entities
        this.ecs.update(game, time);
    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {
        //draw skybox
        this.skyBox.draw(time, game.getCamera(), batch);

        //set camera projection matrix
        batch.setProjectionMatrix(game.getCamera().getCombined());

        //draw entities
        this.ecs.draw(time, game.getCamera(), batch);

        //reset shader
        batch.setShader(null);
        batch.setProjectionMatrix(game.getCamera().getCombined());

        //draw abilities and son on
        this.ecs.drawUILayer(time, game.getCamera(), batch);

        //draw lightmap (only for testing purposes)
        //batch.draw(lightingSystem.getFBO().getColorBufferTexture(), 0, 0);

        batch.flush();

        //reset shader, so default shader is used
        batch.setShader(null);

        batch.end();

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setProjectionMatrix(game.getCamera().getCombined());

        //draw colliding objects
        this.collisionManager.drawCollisionBoxes(time, game.getCamera(), shapeRenderer, COLLISION_BOX_COLOR);

        this.shapeRenderer.end();

        batch.begin();
    }

    @Override
    public void destroy() {
        assetManager.unload(BG_IMAGE_PATH);

        //cleanUp entity-component-system
        this.ecs.dispose();
    }

}
