package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.impl.ECS;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.game.entities.factory.EnemyFactory;
import dev.game.spacechaos.game.entities.factory.PlayerFactory;
import dev.game.spacechaos.game.entities.outdated.EnemySpaceShuttle;
import dev.game.spacechaos.game.entities.outdated.PlayerSpaceShuttle;
import dev.game.spacechaos.game.entities.outdated.SpaceShuttle;
import dev.game.spacechaos.game.entities.collision.CollisionObject;
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

    protected static final String ASTEROID1_IMAGE_PATH = "./data/images/entities/asteroids/asteroid1_brown.png";

    //background image
    protected Texture bgTexture = null;

    protected SkyBox skyBox = null;

    //spaceshuttle
    //protected SpaceShuttle spaceShuttle;

    //protected List<SpaceShuttle> enemySpaceShuttles = new ArrayList<>();
    //protected List<Projectile> playerProjectiles = new ArrayList<>(); //one list for player and enemy projectiles?

    //list with all colliding objects, like meteorits
    //protected List<CollisionObject> collisionObjectList = new ArrayList<>();

    //entity component system
    protected EntityManager ecs = null;

    //player entity
    protected Entity playerEntity = null;

    public GameScreen () {
        //
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

        //create new entity component system
        this.ecs = new ECS(game);

        //get asset
        this.bgTexture = assetManager.get(BG_IMAGE_PATH, Texture.class);

        Texture skyBox1 = assetManager.get(SKYBOX_MINUS_X);
        Texture skyBox2 = assetManager.get(SKYBOX_PLUS_X);
        Texture skyBox3 = assetManager.get(SKYBOX_MINUS_Y);
        Texture skyBox4 = assetManager.get(SKYBOX_PLUS_Y);

        //create space shuttle img, x, y
        //spaceShuttle = new PlayerSpaceShuttle(assetManager.get(SHUTTLE_IMAGE_PATH, Texture.class), game.getViewportWidth() / 2, game.getViewportHeight() / 2);

        //create skybox
        this.skyBox = new SkyBox(new Texture[]{skyBox1, skyBox2, skyBox3, skyBox4}, game.getViewportWidth(), game.getViewportHeight());

        //TODO: add some enemy space shuttles, check if its not in close proximity to any shuttle
        /*for (int amount = 0; amount < 2; amount++) {
            float x = (float) Math.random() * game.getViewportWidth();
            float y = (float) Math.random() * game.getViewportHeight();
            enemySpaceShuttles.add(new EnemySpaceShuttle(assetManager.get(SHUTTLE2_IMAGE_PATH, Texture.class), x, y, spaceShuttle));
        }

        //get texture of asteroids
        Texture texture = assetManager.get(ASTEROID1_IMAGE_PATH);
        TextureRegion textureRegion = new TextureRegion(texture);

        this.collisionObjectList.add(new CollisionObject(game.getViewportWidth() / 2 - 100, game.getViewportHeight() / 2 - 100, 3, textureRegion));*/

        //create new player entity and add to entity-component-system
        this.playerEntity = PlayerFactory.createPlayer(this.ecs, game.getViewportWidth() / 2, game.getViewportHeight() / 2, assetManager.get(SHUTTLE_IMAGE_PATH, Texture.class));
        this.ecs.addEntity(this.playerEntity);

        //TODO: add some enemy space shuttles, check if its not in close proximity to any shuttle
        for (int amount = 0; amount < 2; amount++) {
            //calculate random enemy position near player
            float x = (float) Math.random() * game.getViewportWidth();
            float y = (float) Math.random() * game.getViewportHeight();

            //create and add new enemy space shuttle to entity-component-system
            Entity enemyEntity = EnemyFactory.createEnemyShuttle(this.ecs, x, y, assetManager.get(SHUTTLE2_IMAGE_PATH, Texture.class));
            this.ecs.addEntity(enemyEntity);
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
        /*CameraWrapper camera = game.getCamera();

        //update shuttle
        spaceShuttle.update(game, game.getCamera(), time);

        //update collision objects
        this.collisionObjectList.stream().forEach(obj -> {
            obj.update(game, game.getCamera(), time);
        });

        //update enemy space shuttles
        this.enemySpaceShuttles.stream().forEach(shuttle -> {
            shuttle.update(game, game.getCamera(), time);
        });*/

        //update entities
        this.ecs.update(game, time);
    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {
        //draw background skybox image
        //batch.draw(this.bgTexture, 0, 0, game.getViewportWidth(), game.getViewportHeight());

        //draw skybox
        this.skyBox.draw(time, game.getCamera(), batch);

        //draw collision objects
        /*this.collisionObjectList.stream().forEach(obj -> {
            obj.draw(time, game.getCamera(), batch);
        });

        //draw enemy space shuttles
        this.enemySpaceShuttles.stream().forEach(shuttle -> {
            shuttle.draw(time, game.getCamera(), batch);
        });

        //draw shuttle
        spaceShuttle.draw(time, game.getCamera(), batch);*/

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
    }

    @Override
    public void destroy() {
        assetManager.unload(BG_IMAGE_PATH);

        //cleanUp entity-component-system
        this.ecs.dispose();
    }

}
