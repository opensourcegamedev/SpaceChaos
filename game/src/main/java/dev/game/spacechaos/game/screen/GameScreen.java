package dev.game.spacechaos.game.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import dev.game.spacechaos.engine.collision.CollisionManager;
import dev.game.spacechaos.engine.collision.impl.DefaultCollisionManager;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;
import dev.game.spacechaos.engine.entity.component.movement.MouseDependentMovementComponent;
import dev.game.spacechaos.engine.entity.component.movement.MoveComponent;
import dev.game.spacechaos.engine.entity.impl.ECS;
import dev.game.spacechaos.engine.game.ScreenBasedGame;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.sound.VolumeManager;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.RandomUtils;
import dev.game.spacechaos.engine.utils.SpawnUtils;
import dev.game.spacechaos.game.entities.component.combat.WeaponInventoryComponent;
import dev.game.spacechaos.game.entities.component.powerup.HealthpackComponent;
import dev.game.spacechaos.game.entities.component.powerup.ShieldpackComponent;
import dev.game.spacechaos.game.entities.component.powerup.TorpedoAmmoCrateComponent;
import dev.game.spacechaos.game.entities.factory.EnemyFactory;
import dev.game.spacechaos.game.entities.factory.MeteoriteFactory;
import dev.game.spacechaos.game.entities.factory.PlayerFactory;
import dev.game.spacechaos.game.entities.factory.PowerupFactory;
import dev.game.spacechaos.game.entities.factory.ProjectileFactory;
import dev.game.spacechaos.game.input.InputManager;
import dev.game.spacechaos.game.skybox.SkyBox;

/**
 * This Screen is the singleplayer-mainscreen.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class GameScreen extends BaseScreen {

    private static final String SKYBOX_1 = "./data/images/skybox/space/space.png";
    private static final String SKYBOX_2 = "./data/images/skybox/space/space-1.png";
    private static final String SHUTTLE_IMAGE_PATH = "./data/images/entities/pfau/shuttle.png";
    private static final String SHIELD_IMAGE_PATH = "./data/images/fx/shield-effect/shield-small.png";
    private static final String SHUTTLE2_IMAGE_PATH = "./data/images/entities/starships/spaceshuttledark.png";
    private static final String PROJECTILE_BLUE_IMAGE_PATH = "./data/images/entities/projectiles/projectile2.png";
    private static final String PROJECTILE_RED_IMAGE_PATH = "./data/images/entities/projectiles/projectile1.png";
    private static final String TORPEDO_IMAGE_PATH = "./data/images/entities/projectiles/torpedo.png";

    private static final String[] ASTEROID_IMAGE_PATHS = {
            "./data/images/entities/asteroids/1.png",
            "./data/images/entities/asteroids/2.png", "./data/images/entities/asteroids/3.png",
            "./data/images/entities/asteroids/4.png", "./data/images/entities/asteroids/5.png",
            "./data/images/entities/asteroids/6.png", "./data/images/entities/asteroids/7.png",
            "./data/images/entities/asteroids/8.png" };

    private static final String[] ENEMY_SHUTTLE_IMAGE_PATHS = {
            "./data/images/entities/starships/spaceshuttledark.png",
            "./data/images/entities/starships/spaceshuttle.png",
            "./data/images/entities/supresswarnings/enemy_shuttle.png",
            "./data/images/entities/supresswarnings/shuttle.png",
            "./data/images/entities/spaceshooter/png/enemies/enemyblack1.png"};

    private static final String HEALTHPACK_IMAGE_PATH = "./data/images/entities/spaceshooter/PNG/Power-ups/powerupGreen_bolt.png";
    private static final String AMMOPACK_IMAGE_PATH = "./data/images/entities/spaceshooter/PNG/Power-ups/things_silver.png";
    private static final String SHIELDPACK_IMAGE_PATH = "./data/images/entities/spaceshooter/PNG/Power-ups/powerupRed_shield.png";

    private static final String BACKGROUND_MUSIC_PATH = "./data/music/i-know-your-secret/I_know_your_secret.ogg";
    private static final String BEEP_SOUND_PATH = "./data/sound/beep-sound/beep.ogg";
    private static final String FIRE_SHOOT_SOUND = "./data/sound/spaceshipshooting/Longshot.ogg";
    private static final String TORPEDO_SHOOT_SOUND = "./data/sound/spaceshipshooting/torpedo.wav";

    private static final Color COLLISION_BOX_COLOR = Color.BLUE;
    private static final Color IN_COLLISION_COLOR = Color.YELLOW;

    private Texture projectileBlueTexture = null;
    private Texture projectileRedTexture = null;
    private Texture torpedoTexture = null;
    private List<Texture> enemyShuttleTexture = new ArrayList<>();

    private SkyBox skyBox = null;

    // entity component system
    private EntityManager ecs = null;

    // player entity
    private Entity playerEntity = null;

    private Music music = null;
    private long lastBeep = 0;
    private long beepInterval = 8000;
    private Sound fireSound = null;
    private Sound torpedoSound = null;

    private ShapeRenderer shapeRenderer = null;

    private CollisionManager collisionManager = null;

    // list with all enemy entities, meteorits and items
    private List<Entity> enemyEntityList = new ArrayList<>();
    private List<Entity> meteorEntityList = new ArrayList<>();
    private List<Entity> powerupEntityList = new ArrayList<>();

    // input
    private InputManager inputManager;

    // settings
    private boolean debug;

    public GameScreen() {
        // create shape renderer
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
        // load all necessary assets

        // load skybox
        assetManager.load(SKYBOX_1, Texture.class);
        assetManager.load(SKYBOX_2, Texture.class);

        assetManager.load(SHUTTLE_IMAGE_PATH, Texture.class);
        assetManager.load(SHIELD_IMAGE_PATH, Texture.class);
        assetManager.load(SHUTTLE2_IMAGE_PATH, Texture.class);
        assetManager.load(PROJECTILE_BLUE_IMAGE_PATH, Texture.class);
        assetManager.load(PROJECTILE_RED_IMAGE_PATH, Texture.class);
        assetManager.load(TORPEDO_IMAGE_PATH, Texture.class);

        // load meteorits
        for (String ASTEROID_IMAGE_PATH : ASTEROID_IMAGE_PATHS) {
            assetManager.load(ASTEROID_IMAGE_PATH, Texture.class);
        }

        // load enemy shuttles
        for (String SHUTTLE_IMAGE_PATH : ENEMY_SHUTTLE_IMAGE_PATHS) {
            assetManager.load(SHUTTLE_IMAGE_PATH, Texture.class);
        }

        // load power-ups
        assetManager.load(HEALTHPACK_IMAGE_PATH, Texture.class);
        assetManager.load(AMMOPACK_IMAGE_PATH, Texture.class);
        assetManager.load(SHIELDPACK_IMAGE_PATH, Texture.class);

        // load background music
        assetManager.load(BACKGROUND_MUSIC_PATH, Music.class);

        // load sound effects
        assetManager.load(BEEP_SOUND_PATH, Sound.class);
        assetManager.load(FIRE_SHOOT_SOUND, Sound.class);
        assetManager.load(TORPEDO_SHOOT_SOUND, Sound.class);

        // wait while assets are loading
        assetManager.finishLoadingAsset(SKYBOX_1);
        assetManager.finishLoadingAsset(SKYBOX_2);
        assetManager.finishLoadingAsset(SHUTTLE_IMAGE_PATH);
        assetManager.finishLoadingAsset(SHIELD_IMAGE_PATH);
        assetManager.finishLoadingAsset(SHUTTLE2_IMAGE_PATH);
        assetManager.finishLoadingAsset(PROJECTILE_BLUE_IMAGE_PATH);
        assetManager.finishLoadingAsset(PROJECTILE_RED_IMAGE_PATH);
        assetManager.finishLoadingAsset(TORPEDO_IMAGE_PATH);

        // load asteorids
        for (String ASTEROID_IMAGE_PATH : ASTEROID_IMAGE_PATHS) {
            assetManager.finishLoadingAsset(ASTEROID_IMAGE_PATH);
        }

        // load space shuttles
        for (String SHUTTLE_IMAGE_PATH : ENEMY_SHUTTLE_IMAGE_PATHS) {
            assetManager.finishLoadingAsset(SHUTTLE_IMAGE_PATH);
        }

        // load powerups
        assetManager.finishLoadingAsset(HEALTHPACK_IMAGE_PATH);
        assetManager.finishLoadingAsset(AMMOPACK_IMAGE_PATH);
        assetManager.finishLoadingAsset(SHIELDPACK_IMAGE_PATH);

        assetManager.finishLoadingAsset(BACKGROUND_MUSIC_PATH);
        assetManager.finishLoadingAsset(BEEP_SOUND_PATH);
        assetManager.finishLoadingAsset(FIRE_SHOOT_SOUND);
        assetManager.finishLoadingAsset(TORPEDO_SHOOT_SOUND);

        // create new entity component system
        this.ecs = new ECS(game);

        // create collision manager
        this.collisionManager = new DefaultCollisionManager(this.ecs);

        // get asset
        this.projectileBlueTexture = assetManager.get(PROJECTILE_BLUE_IMAGE_PATH, Texture.class);
        this.projectileRedTexture = assetManager.get(PROJECTILE_RED_IMAGE_PATH, Texture.class);
        this.torpedoTexture = assetManager.get(TORPEDO_IMAGE_PATH);

        this.enemyShuttleTexture.add(assetManager.get(ENEMY_SHUTTLE_IMAGE_PATHS[0]));
        this.enemyShuttleTexture.add(assetManager.get(ENEMY_SHUTTLE_IMAGE_PATHS[1]));
        this.enemyShuttleTexture.add(assetManager.get(ENEMY_SHUTTLE_IMAGE_PATHS[2]));
        this.enemyShuttleTexture.add(assetManager.get(ENEMY_SHUTTLE_IMAGE_PATHS[3]));
        this.enemyShuttleTexture.add(assetManager.get(ENEMY_SHUTTLE_IMAGE_PATHS[4]));

        Texture skyBox1 = assetManager.get(SKYBOX_1);

        // get background music
        this.music = assetManager.get(BACKGROUND_MUSIC_PATH);

        // get sound effects
        this.fireSound = assetManager.get(FIRE_SHOOT_SOUND);
        this.torpedoSound = assetManager.get(TORPEDO_SHOOT_SOUND);

        // create skybox
        this.skyBox = new SkyBox(new Texture[] { /* skyBox2, */ skyBox1 }, game.getViewportWidth(),
                game.getViewportHeight());

        this.inputManager = new InputManager();
    }

    private void spawnEnemyShuttles(int amount) {
        // execute this code after updating all entities
        game.runOnUIThread(() -> {
            // add a specific amount of enemy shuttles
            for (int enemyNumber = 0; enemyNumber < amount; enemyNumber++) {
                // calculate random enemy position near player
                Vector2 randomPos = SpawnUtils.getRandomSpawnPosition(900, 1000,
                        this.playerEntity.getComponent(PositionComponent.class).getMiddleX(),
                        this.playerEntity.getComponent(PositionComponent.class).getMiddleY());
                float x = randomPos.x;
                float y = randomPos.y;

                // create and add new enemy space shuttle to
                // entity-component-system
                Entity enemyEntity = EnemyFactory.createEnemyShuttle(this.ecs, x, y,
                        assetManager.get(ENEMY_SHUTTLE_IMAGE_PATHS[RandomUtils.getRandomNumber(0,
                                ENEMY_SHUTTLE_IMAGE_PATHS.length - 1)],
                                Texture.class), this.playerEntity, projectileRedTexture,
                        (Entity causingEntity) -> spawnEnemyShuttles(1), enemyShuttleTexture);
                this.ecs.addEntity(enemyEntity);

                // add entity to enemy entity list
                this.enemyEntityList.add(enemyEntity);
            }
        });
    }

    private void spawnMeteorites(int amount) {
        // execute this code after updating all entities
        game.runOnUIThread(() -> {
            // add a specific amount of meteorites
            for (int meteorNumber = 0; meteorNumber < amount; meteorNumber++) {
                // calculate random meteor position near player
                Vector2 randomPos = SpawnUtils.getRandomSpawnPosition(1000, 1600,
                        this.playerEntity.getComponent(PositionComponent.class).getMiddleX(),
                        this.playerEntity.getComponent(PositionComponent.class).getMiddleY());
                float x = randomPos.x;
                float y = randomPos.y;

                // create and add new meteor to
                // entity-component-system
                Entity entity = MeteoriteFactory.createMeteorite(this.ecs, x, y, assetManager
                        .get(ASTEROID_IMAGE_PATHS[RandomUtils.getRandomNumber(0, ASTEROID_IMAGE_PATHS.length - 1)]));
                this.ecs.addEntity(entity);

                // add entity to meteor entity list
                this.meteorEntityList.add(entity);
            }
        });
    }

    private void spawnPowerups(int amount) {
        // execute this code after updating all entities
        game.runOnUIThread(() -> {
            // add a specific amount of powerups
            for (int i = 0; i < amount; i++) {
                // calculate random powerup position near player
                Vector2 randomPos = SpawnUtils.getRandomSpawnPosition(100, 1300,
                        this.playerEntity.getComponent(PositionComponent.class).getMiddleX(),
                        this.playerEntity.getComponent(PositionComponent.class).getMiddleY());
                float x = randomPos.x;
                float y = randomPos.y;

                Entity entity;

                // create and add new powerup to entity-component-system
                if (RandomUtils.rollTheDice(3)) {
                    entity = PowerupFactory.createHealthpack(this.ecs, x, y, assetManager.get(HEALTHPACK_IMAGE_PATH));
                    // add listener to respawn power up
                    entity.getComponent(HealthpackComponent.class).setRemoveListener((Entity entity1) -> {
                        spawnPowerups(1);
                    });
                } else if (RandomUtils.rollTheDice(2)){
                    entity = PowerupFactory.createTorpedoAmmoCrate(this.ecs, x, y,
                            assetManager.get(AMMOPACK_IMAGE_PATH));
                    // add listener to respawn power up
                    entity.getComponent(TorpedoAmmoCrateComponent.class).setRemoveListener((Entity entity1) -> {
                        spawnPowerups(1);
                    });
                } else {
                    entity = PowerupFactory.createShieldpack(this.ecs, x, y,
                            assetManager.get(SHIELDPACK_IMAGE_PATH));
                    // add listener to respawn power up
                    entity.getComponent(ShieldpackComponent.class).setRemoveListener((Entity entity1) -> {
                        spawnPowerups(1);
                    });
                }

                this.ecs.addEntity(entity);

                // add entity to powerup entity list
                this.powerupEntityList.add(entity);
            }
        });
    }

    @Override
    public void onResume() {
        // remove all entities
        this.ecs.removeAllEntities();
        this.enemyEntityList.clear();

        // create new player entity and add to entity-component-system
        this.playerEntity = PlayerFactory.createPlayer(this.ecs, game.getViewportWidth() / 2,
                game.getViewportHeight() / 2, assetManager.get(SHUTTLE_IMAGE_PATH, Texture.class), (causingEntity) -> {
                    System.out.println("game over");
                    game.getScreenManager().leaveAllAndEnter("gameover");
                }, assetManager.get(SHIELD_IMAGE_PATH, Texture.class));
        this.ecs.addEntity(this.playerEntity);

        // add player entity to shared data
        game.getSharedData().put("playerEntity", this.playerEntity);

        // input
        Gdx.input.setInputProcessor(inputManager);

        // push HUD overlay screen (GUI)
        game.getScreenManager().push("hud");

        // spawn entities, meteorits and power ups
        spawnEnemyShuttles(6);
        spawnMeteorites(38);
        spawnPowerups(9);

        // get game flags
        debug = (Boolean) game.getSharedData().get("debug");

        // play background music
        this.music.setVolume(VolumeManager.getInstance().getBackgroundMusicVolume());
        this.music.setLooping(true);
        this.music.play();
    }

    @Override
    public void onPause() {
        // stop music
        this.music.stop();

        // input
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void update(ScreenBasedGame game, GameTime time) {
        // update entities
        this.ecs.update(game, time);

        // respawn enemies that are too far away
        for (Entity ent : this.enemyEntityList) {
            if (SpawnUtils.getDistance(playerEntity, ent) > 1000) {
                try {
                    // Enemy is too far away -> respawn
                    ent.dispose();
                    this.ecs.removeEntity(ent);
                    spawnEnemyShuttles(1);
                } catch (NullPointerException npe) {
                    // Enemy got killed.
                }
            }
        }

        // respawn meteorites that are too far away
        for (Entity ent : this.meteorEntityList) {
            if (SpawnUtils.getDistance(playerEntity, ent) > 1400) {
                try {
                    // Meteor is too far away -> respawn
                    ent.dispose();
                    this.ecs.removeEntity(ent);
                    spawnMeteorites(1);
                } catch (NullPointerException npe) {
                    // Meteor got destroyed.
                }
            }
        }

        // respawn powerups that are to far away
        for (Entity ent : this.powerupEntityList) {
            if (SpawnUtils.getDistance(playerEntity, ent) > 1200) {
                try {
                    // Powerup is too far away -> respawn
                    ent.dispose();
                    this.ecs.removeEntity(ent);
                    spawnPowerups(1);
                } catch (NullPointerException npe) {
                    // Powerup got catched.

                    // TODO: remove this quick & dirty fix
                }
            }
        }

        processInput();

        if (lastBeep == 0) {
            lastBeep = System.currentTimeMillis();
        }

        // play beep sound every 8 seconds
        if (lastBeep + beepInterval < System.currentTimeMillis()) {
            // beepSound.play();
            lastBeep = System.currentTimeMillis();

            // calculate new random beep interval
            beepInterval = 5000 + (long) (Math.random() * 5000);
        }
    }

    private void processInput() {
        WeaponInventoryComponent weaponInvComponent = this.playerEntity.getComponent(WeaponInventoryComponent.class);

        // LEFT WEAPON
        if (inputManager.isSpaceJustPressed() || inputManager.isLeftMouseButtonJustPressed()) {
            if (weaponInvComponent.canShootNowWithLeftWeapon()) {
                weaponInvComponent.setLastShotWithLeftWeapon();

                MouseDependentMovementComponent mouseDependentMovementComponent = this.playerEntity
                        .getComponent(MouseDependentMovementComponent.class);

                float dirX = mouseDependentMovementComponent.getFrontVec().x;
                float dirY = mouseDependentMovementComponent.getFrontVec().y;

                Entity projectile = ProjectileFactory.createProjectile(this.ecs,
                        dirX + this.playerEntity.getComponent(PositionComponent.class).getMiddleX() - 20,
                        dirY + this.playerEntity.getComponent(PositionComponent.class).getMiddleY() - 20,
                        projectileBlueTexture, dirX, dirY, this.playerEntity);

                // weaponInvComponent.subAmmoForLeftWeapon();

                projectile.getComponent(DrawTextureComponent.class)
                        .setRotationAngle(playerEntity.getComponent(DrawTextureComponent.class).getRotationAngle());
                projectile.getComponent(MoveComponent.class).setMoving(true);
                this.ecs.addEntity(projectile);

                // play fire sound
                this.fireSound.play(VolumeManager.getInstance().getEnvVolume());
            }
        }
        // RIGHT WEAPON
        if (inputManager.isRightMouseButtonJustPressed()) {
            if (weaponInvComponent.canShootNowWithRightWeapon()) {
                weaponInvComponent.setLastShotWithRightWeapon();

                MouseDependentMovementComponent mouseDependentMovementComponent = this.playerEntity
                        .getComponent(MouseDependentMovementComponent.class);

                float dirX = mouseDependentMovementComponent.getFrontVec().x;
                float dirY = mouseDependentMovementComponent.getFrontVec().y;

                // entity to follow
                Entity enemyEntity = null;

                // TODO: choose nearest enemy shuttle or hovered shuttle
                for (Entity entity : this.enemyEntityList) {
                    enemyEntity = entity;
                }

                Entity projectile = ProjectileFactory.createTorpedoProjectile(this.ecs,
                        dirX + this.playerEntity.getComponent(PositionComponent.class).getMiddleX() - 30,
                        dirY + this.playerEntity.getComponent(PositionComponent.class).getMiddleY() - 30,
                        torpedoTexture, dirX, dirY, this.playerEntity, enemyEntity);

                weaponInvComponent.subAmmoForRightWeapon();

                projectile.getComponent(DrawTextureComponent.class)
                        .setRotationAngle(playerEntity.getComponent(DrawTextureComponent.class).getRotationAngle());
                projectile.getComponent(MoveComponent.class).setMoving(true);
                this.ecs.addEntity(projectile);

                // play torpedo sound
                this.torpedoSound.play(VolumeManager.getInstance().getEnvVolume() - 0.4f);
            }
        }
    }

    @Override
    public void draw(GameTime time, SpriteBatch batch) {
        // draw skybox
        this.skyBox.draw(time, game.getCamera(), batch);

        // set camera projection matrix
        batch.setProjectionMatrix(game.getCamera().getCombined());

        // draw entities
        this.ecs.draw(time, game.getCamera(), batch);

        // reset shader
        batch.setShader(null);
        batch.setProjectionMatrix(game.getCamera().getCombined());

        // draw abilities and so on
        this.ecs.drawUILayer(time, game.getCamera(), batch);

        batch.flush();

        // reset shader, so default shader is used
        batch.setShader(null);

        batch.end();

        this.shapeRenderer.setProjectionMatrix(game.getCamera().getCombined());
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.shapeRenderer.setColor(Color.BLACK);

        // draw collision boxes
        if (debug) {
            this.collisionManager.drawCollisionBoxes(time, game.getCamera(), shapeRenderer, COLLISION_BOX_COLOR,
                    IN_COLLISION_COLOR);
        }

        this.shapeRenderer.end();

        batch.begin();
    }

    @Override
    public void destroy() {
        // cleaning up entity-component-system
        this.ecs.dispose();
    }

}
