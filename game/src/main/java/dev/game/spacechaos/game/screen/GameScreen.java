package dev.game.spacechaos.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.collision.CollisionManager;
import dev.game.spacechaos.engine.collision.impl.DefaultCollisionManager;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;
import dev.game.spacechaos.engine.entity.component.movement.MouseDependentMovementComponent;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;
import dev.game.spacechaos.engine.entity.impl.ECS;
import dev.game.spacechaos.engine.entity.listener.HPHitListener;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.input.InputStates;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.game.entities.factory.EnemyFactory;
import dev.game.spacechaos.game.entities.factory.MeteoritFactory;
import dev.game.spacechaos.game.entities.factory.PlayerFactory;
import dev.game.spacechaos.game.entities.factory.ProjectileFactory;
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
    protected static final String PROJECTILE_IMAGE_PATH = "./data/images/entities/projectiles/projectile2.png";
    protected static final String TORPEDO_IMAGE_PATH = "./data/images/entities/projectiles/torpedo.png";
    protected static final String ASTEROID1_IMAGE_PATH = "./data/images/entities/asteroids/asteroid1_brown.png";

    protected static final String BACKGROUND_MUSIC_PATH = "./data/music/i-know-your-secret/I_know_your_secret.ogg";
    protected static final String BEEP_SOUND_PATH = "./data/sound/beep-sound/beep.ogg";
    protected static final String FIRE_SHOOT_SOUND = "./data/sound/spaceshipshooting/Longshot.ogg";

    protected static final Color COLLISION_BOX_COLOR = Color.BLUE;
    protected static final Color IN_COLLISION_COLOR = Color.YELLOW;

    //background image
    protected Texture bgTexture = null;

    protected Texture projectileTexture = null;
    protected Texture torpedoTexture = null;

    protected SkyBox skyBox = null;

    //entity component system
    protected EntityManager ecs = null;

    //player entity
    protected Entity playerEntity = null;

    protected Music music = null;
    protected Sound beepSound = null;
    protected long lastBeep = 0;
    protected long beepInterval = 8000;
    protected Sound fireSound = null;

    protected ShapeRenderer shapeRenderer = null;

    protected CollisionManager collisionManager = null;

    //list with all enemy entities
    protected List<Entity> enemyEntityList = new ArrayList<>();

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
        assetManager.load(PROJECTILE_IMAGE_PATH, Texture.class);
        assetManager.load(TORPEDO_IMAGE_PATH, Texture.class);

        //load collision objects
        assetManager.load(ASTEROID1_IMAGE_PATH, Texture.class);

        //load background music
        assetManager.load(BACKGROUND_MUSIC_PATH, Music.class);

        //load sound effects
        assetManager.load(BEEP_SOUND_PATH, Sound.class);
        assetManager.load(FIRE_SHOOT_SOUND, Sound.class);

        //wait while assets are loading
        assetManager.finishLoadingAsset(BG_IMAGE_PATH);
        assetManager.finishLoadingAsset(SKYBOX_MINUS_X);
        assetManager.finishLoadingAsset(SKYBOX_PLUS_X);
        assetManager.finishLoadingAsset(SKYBOX_MINUS_Y);
        assetManager.finishLoadingAsset(SKYBOX_PLUS_Y);
        assetManager.finishLoadingAsset(SHUTTLE_IMAGE_PATH);
        assetManager.finishLoadingAsset(SHUTTLE2_IMAGE_PATH);
        assetManager.finishLoadingAsset(PROJECTILE_IMAGE_PATH);
        assetManager.finishLoadingAsset(TORPEDO_IMAGE_PATH);
        assetManager.finishLoadingAsset(ASTEROID1_IMAGE_PATH);
        assetManager.finishLoadingAsset(BACKGROUND_MUSIC_PATH);
        assetManager.finishLoadingAsset(BEEP_SOUND_PATH);
        assetManager.finishLoadingAsset(FIRE_SHOOT_SOUND);

        //create new entity component system
        this.ecs = new ECS(game);

        //create collision manager
        this.collisionManager = new DefaultCollisionManager(this.ecs);

        //get asset
        this.bgTexture = assetManager.get(BG_IMAGE_PATH, Texture.class);
        this.projectileTexture = assetManager.get(PROJECTILE_IMAGE_PATH, Texture.class);
        this.torpedoTexture = assetManager.get(TORPEDO_IMAGE_PATH);

        Texture skyBox1 = assetManager.get(SKYBOX_MINUS_X);
        Texture skyBox2 = assetManager.get(SKYBOX_PLUS_X);
        Texture skyBox3 = assetManager.get(SKYBOX_MINUS_Y);
        Texture skyBox4 = assetManager.get(SKYBOX_PLUS_Y);

        //get background music
        this.music = assetManager.get(BACKGROUND_MUSIC_PATH);

        //get sound effects
        this.beepSound = assetManager.get(BEEP_SOUND_PATH);
        this.fireSound = assetManager.get(FIRE_SHOOT_SOUND);

        //create skybox
        this.skyBox = new SkyBox(new Texture[]{/*skyBox1, skyBox2, skyBox3, skyBox4*/skyBox2}, game.getViewportWidth(), game.getViewportHeight());
    }

    protected void spawnEnemyShuttles(int amount) {
        //execute this code after updating all entities
        game.runOnUIThread(() -> {
            //add a specific amount of enemy shuttles
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
                Entity enemyEntity = EnemyFactory.createEnemyShuttle(this.ecs, x, y, assetManager.get(SHUTTLE2_IMAGE_PATH, Texture.class), this.playerEntity, (Entity entity) -> {
                    //respawn shuttle
                    spawnEnemyShuttles(1);
                });
                this.ecs.addEntity(enemyEntity);

                //add entity to enemy entity list
                this.enemyEntityList.add(enemyEntity);
            }
        });
    }

    @Override
    public void onResume() {
        //remove all entities
        this.ecs.removeAllEntities();
        this.enemyEntityList.clear();

        //create new player entity and add to entity-component-system
        this.playerEntity = PlayerFactory.createPlayer(this.ecs, game.getViewportWidth() / 2, game.getViewportHeight() / 2, assetManager.get(SHUTTLE_IMAGE_PATH, Texture.class), new HPHitListener() {
            @Override
            public void onHit(float oldValue, float newValue, float maxHP) {
                System.out.println("game over");

                game.getScreenManager().leaveAllAndEnter("gameover");
            }
        });

        this.ecs.addEntity(this.playerEntity);

        //spawn enemy shuttles
        spawnEnemyShuttles(5);

        float minDistance = 800;

        float playerPosX = playerEntity.getComponent(PositionComponent.class).getMiddleX();
        float playerPosY = playerEntity.getComponent(PositionComponent.class).getMiddleY();

        Vector2 tmpVector = new Vector2();

        //add some random meteorits
        for (int i = 0; i < 90; i++) {
            float distance = 0;

            float x = 0;
            float y = 0;

            while (distance < minDistance) {
                //calculate random enemy position near player
                x = (float) Math.random() * game.getViewportWidth() * 3 - game.getViewportWidth();
                y = (float) Math.random() * game.getViewportHeight() * 3 - game.getViewportHeight();

                tmpVector.set(playerPosX - x, playerPosY - y);
                distance = tmpVector.len();
            }

            //create and add new meteorit
            Entity entity = MeteoritFactory.createMeteorit(this.ecs, x, y, assetManager.get(ASTEROID1_IMAGE_PATH));
            this.ecs.addEntity(entity);
        }

        //play background music
        this.music.setVolume(0.8f);
        this.music.setLooping(true);
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

        //check for shoot
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || InputStates.isLeftMouseButtonJustPressed()) {
            MouseDependentMovementComponent mouseDependentMovementComponent = this.playerEntity.getComponent(MouseDependentMovementComponent.class);

            float dirX = mouseDependentMovementComponent.getFrontVec().x;
            float dirY = mouseDependentMovementComponent.getFrontVec().y;

            Entity projectile = ProjectileFactory.createProjectile(
                    this.ecs,
                    dirX + this.playerEntity.getComponent(PositionComponent.class).getMiddleX() - 20,
                    dirY + this.playerEntity.getComponent(PositionComponent.class).getMiddleY() - 20,
                    projectileTexture,
                    dirX,
                    dirY,
                    4f,
                    this.playerEntity,
                    4000L
            );

            projectile.getComponent(DrawTextureComponent.class).setRotationAngle(playerEntity.getComponent(DrawTextureComponent.class).getRotationAngle());
            projectile.getComponent(MoveComponent.class).setMoving(true);
            this.ecs.addEntity(projectile);

            //play fire sound
            this.fireSound.play(0.8f);
        } else if (InputStates.isRightMouseButtonJustPressed()) {
            MouseDependentMovementComponent mouseDependentMovementComponent = this.playerEntity.getComponent(MouseDependentMovementComponent.class);

            float dirX = mouseDependentMovementComponent.getFrontVec().x;
            float dirY = mouseDependentMovementComponent.getFrontVec().y;

            //entity to follow
            Entity enemyEntity = null;

            //TODO: choose nearest enemy shuttle or hovered shuttle
            for (Entity entity : this.enemyEntityList) {
                enemyEntity = entity;
            }

            Entity projectile = ProjectileFactory.createTorpedoProjectile(
                    this.ecs,
                    dirX + this.playerEntity.getComponent(PositionComponent.class).getMiddleX() - 30,
                    dirY + this.playerEntity.getComponent(PositionComponent.class).getMiddleY() - 30,
                    torpedoTexture,
                    dirX,
                    dirY,
                    4f,
                    this.playerEntity,
                    enemyEntity,
                    3000L
            );

            projectile.getComponent(DrawTextureComponent.class).setRotationAngle(playerEntity.getComponent(DrawTextureComponent.class).getRotationAngle()
            );
            projectile.getComponent(MoveComponent.class).setMoving(true);
            this.ecs.addEntity(projectile);
        }

        if (lastBeep == 0) {
            lastBeep = System.currentTimeMillis();
        }

        //play beep sound every 8 seconds
        if (lastBeep + beepInterval < System.currentTimeMillis()) {
            //beepSound.play();
            lastBeep = System.currentTimeMillis();

            //calculate new random beep interval
            beepInterval = 5000 + (long) (Math.random() * 5000);
        }
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

        this.shapeRenderer.setProjectionMatrix(game.getCamera().getCombined());
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.shapeRenderer.setColor(Color.BLACK);

        //draw colliding objects
        this.collisionManager.drawCollisionBoxes(time, game.getCamera(), shapeRenderer, COLLISION_BOX_COLOR, IN_COLLISION_COLOR);

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
