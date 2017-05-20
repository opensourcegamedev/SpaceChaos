package dev.game.spacechaos.game.screen;

import java.util.ArrayList;
import java.util.List;

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
import dev.game.spacechaos.engine.input.InputStates;
import dev.game.spacechaos.engine.screen.impl.BaseScreen;
import dev.game.spacechaos.engine.sound.VolumeManager;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.RandomUtils;
import dev.game.spacechaos.engine.utils.SpawnUtils;
import dev.game.spacechaos.game.entities.factory.EnemyFactory;
import dev.game.spacechaos.game.entities.factory.MeteoriteFactory;
import dev.game.spacechaos.game.entities.factory.PlayerFactory;
import dev.game.spacechaos.game.entities.factory.ProjectileFactory;
import dev.game.spacechaos.game.skybox.SkyBox;

/**
 * This Screen is the singleplayer-mainscreen.
 *
 * @author SpaceChaos-Team (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @version 1.0.0-PreAlpha
 */
public class GameScreen extends BaseScreen {

	private static final String SKYBOX_1 = "./data/images/skybox/space/space.png";
	private static final String SKYBOX_2 = "./data/images/skybox/space/space-1.png";
	private static final String SHUTTLE_IMAGE_PATH = "./data/images/entities/pfau/shuttle.png";
	private static final String SHUTTLE2_IMAGE_PATH = "./data/images/entities/starships/spaceshuttledark.png";
	private static final String PROJECTILE_IMAGE_PATH = "./data/images/entities/projectiles/projectile2.png";
	private static final String TORPEDO_IMAGE_PATH = "./data/images/entities/projectiles/torpedo.png";
	private static final String[] ASTEROID_IMAGE_PATHS = { "./data/images/entities/asteroids/1.png",
			"./data/images/entities/asteroids/2.png", "./data/images/entities/asteroids/3.png",
			"./data/images/entities/asteroids/4.png", "./data/images/entities/asteroids/5.png",
			"./data/images/entities/asteroids/6.png", "./data/images/entities/asteroids/7.png",
			"./data/images/entities/asteroids/8.png" };
	private static final String BACKGROUND_MUSIC_PATH = "./data/music/i-know-your-secret/I_know_your_secret.ogg";
	private static final String BEEP_SOUND_PATH = "./data/sound/beep-sound/beep.ogg";
	private static final String FIRE_SHOOT_SOUND = "./data/sound/spaceshipshooting/Longshot.ogg";
	private static final String TORPEDO_SHOOT_SOUND = "./data/sound/spaceshipshooting/torpedo.wav";

	private static final Color COLLISION_BOX_COLOR = Color.BLUE;
    private static final Color IN_COLLISION_COLOR = Color.YELLOW;

    private Texture projectileTexture = null;
	private Texture torpedoTexture = null;

	private SkyBox skyBox = null;

	//entity component system
    private EntityManager ecs = null;

	//player entity
    private Entity playerEntity = null;

	private Music music = null;
    private long lastBeep = 0;
	private long beepInterval = 8000;
	private Sound fireSound = null;
	private Sound torpedoSound = null;

	private ShapeRenderer shapeRenderer = null;

	private CollisionManager collisionManager = null;

	//list with all enemy entities
    private List<Entity> enemyEntityList = new ArrayList<>();

    public GameScreen() {
		//create shape renderer
		this.shapeRenderer = new ShapeRenderer();
	}

	@Override
	protected void onInit(ScreenBasedGame game, AssetManager assetManager) {
		//load all necessary assets

		//load skybox
		assetManager.load(SKYBOX_1, Texture.class);
		assetManager.load(SKYBOX_2, Texture.class);
		assetManager.load(SHUTTLE_IMAGE_PATH, Texture.class);
		assetManager.load(SHUTTLE2_IMAGE_PATH, Texture.class);
		assetManager.load(PROJECTILE_IMAGE_PATH, Texture.class);
		assetManager.load(TORPEDO_IMAGE_PATH, Texture.class);

		//load meteorits
        for (String ASTEROID_IMAGE_PATH : ASTEROID_IMAGE_PATHS) {
            assetManager.load(ASTEROID_IMAGE_PATH, Texture.class);
        }

		//load background music
		assetManager.load(BACKGROUND_MUSIC_PATH, Music.class);

		//load sound effects
		assetManager.load(BEEP_SOUND_PATH, Sound.class);
		assetManager.load(FIRE_SHOOT_SOUND, Sound.class);
		assetManager.load(TORPEDO_SHOOT_SOUND, Sound.class);

		//wait while assets are loading
		assetManager.finishLoadingAsset(SKYBOX_1);
		assetManager.finishLoadingAsset(SKYBOX_2);
		assetManager.finishLoadingAsset(SHUTTLE_IMAGE_PATH);
		assetManager.finishLoadingAsset(SHUTTLE2_IMAGE_PATH);
		assetManager.finishLoadingAsset(PROJECTILE_IMAGE_PATH);
		assetManager.finishLoadingAsset(TORPEDO_IMAGE_PATH);
        for (String ASTEROID_IMAGE_PATH : ASTEROID_IMAGE_PATHS) {
            assetManager.finishLoadingAsset(ASTEROID_IMAGE_PATH);
        }
		assetManager.finishLoadingAsset(BACKGROUND_MUSIC_PATH);
		assetManager.finishLoadingAsset(BEEP_SOUND_PATH);
		assetManager.finishLoadingAsset(FIRE_SHOOT_SOUND);
		assetManager.finishLoadingAsset(TORPEDO_SHOOT_SOUND);

		//create new entity component system
		this.ecs = new ECS(game);

		//create collision manager
		this.collisionManager = new DefaultCollisionManager(this.ecs);

		//get asset
		this.projectileTexture = assetManager.get(PROJECTILE_IMAGE_PATH, Texture.class);
		this.torpedoTexture = assetManager.get(TORPEDO_IMAGE_PATH);

		Texture skyBox1 = assetManager.get(SKYBOX_1);

        //get background music
		this.music = assetManager.get(BACKGROUND_MUSIC_PATH);

		//get sound effects
        this.fireSound = assetManager.get(FIRE_SHOOT_SOUND);
		this.torpedoSound = assetManager.get(TORPEDO_SHOOT_SOUND);

		//create skybox
		this.skyBox = new SkyBox(new Texture[] { /* skyBox2, */ skyBox1 }, game.getViewportWidth(),
				game.getViewportHeight());
	}

	private void spawnEnemyShuttles(int amount) {
		//execute this code after updating all entities
		game.runOnUIThread(() -> {
			//add a specific amount of enemy shuttles
			float[][] positions = new float[amount + 1][2]; //player + enemy
			//positions
			positions[0][0] = this.playerEntity.getComponent(PositionComponent.class).getMiddleX();
			positions[0][1] = this.playerEntity.getComponent(PositionComponent.class).getMiddleY();
			for (int enemyNumber = 0; enemyNumber < amount; enemyNumber++) {
				//calculate random enemy position near player
				Vector2 randomPos = SpawnUtils.getRandomSpawnPosition(1000, 3000,
						this.playerEntity.getComponent(PositionComponent.class).getMiddleX()
								- game.getViewportWidth() / 2,
						this.playerEntity.getComponent(PositionComponent.class).getMiddleY()
								- game.getViewportHeight() / 2);
				float x = randomPos.x;
				float y = randomPos.y;
				//float x = (float) Math.random() * game.getViewportWidth() * 5
				//- (game.getViewportWidth() * 2);
				//float y = (float) Math.random() * game.getViewportHeight() *
				//5 - (game.getViewportHeight() * 2);

				boolean validPos = false;
				while (!validPos) {
					if ((x > 0 && x < game.getViewportWidth())
							&& (y > 0 && y < game.getViewportHeight())) {
						validPos = true;
					} else {
						x = (float) Math.random() * game.getViewportWidth() * 5 - (game.getViewportWidth() * 2);
						y = (float) Math.random() * game.getViewportHeight() * 5 - (game.getViewportHeight() * 2);
					}
				}

				//create and add new enemy space shuttle to
				//entity-component-system
				Entity enemyEntity = EnemyFactory.createEnemyShuttle(this.ecs, x, y,
						assetManager.get(SHUTTLE2_IMAGE_PATH, Texture.class), this.playerEntity, projectileTexture,
						(Entity entity) -> {
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
		this.playerEntity = PlayerFactory.createPlayer(this.ecs, game.getViewportWidth() / 2,
				game.getViewportHeight() / 2, assetManager.get(SHUTTLE_IMAGE_PATH, Texture.class), (oldValue, newValue, maxHP) -> {
                    System.out.println("game over");

                    game.getScreenManager().leaveAllAndEnter("gameover");
                });

		this.ecs.addEntity(this.playerEntity);

		//add player entity to shared data
		game.getSharedData().put("playerEntity", this.playerEntity);

		//push HUD overlay screen (GUI)
		game.getScreenManager().push("hud");

		//spawn enemy shuttles
		spawnEnemyShuttles(5);

		float minDistance = 1000;

		float playerPosX = playerEntity.getComponent(PositionComponent.class).getMiddleX();
		float playerPosY = playerEntity.getComponent(PositionComponent.class).getMiddleY();

		Vector2 tmpVector = new Vector2();

		//add some random meteorits
		for (int i = 0; i < 180; i++) {
			float distance = 0;

			float x = 0;
			float y = 0;

			while (distance < minDistance) {
				//calculate random enemy position near player
				//x = (float) Math.random() * game.getViewportWidth() * 3 -
				//game.getViewportWidth();
				//y = (float) Math.random() * game.getViewportHeight() * 3 -
				//game.getViewportHeight();

				//calculate random enemy position near player
				Vector2 randomPos = SpawnUtils.getRandomSpawnPosition(100, game.getViewportWidth() * 2);
				x = randomPos.x;
				y = randomPos.y;

				tmpVector.set(playerPosX - x, playerPosY - y);
				distance = tmpVector.len();
			}

			//create and add new meteorit
			Entity entity = MeteoriteFactory.createMeteorite(this.ecs, x, y, assetManager
					.get(ASTEROID_IMAGE_PATHS[RandomUtils.getRandomNumber(0, ASTEROID_IMAGE_PATHS.length - 1)]));
			this.ecs.addEntity(entity);
		}

		//play background music
		this.music.setVolume(VolumeManager.getInstance().getBackgroundMusicVolume());
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
			MouseDependentMovementComponent mouseDependentMovementComponent = this.playerEntity
					.getComponent(MouseDependentMovementComponent.class);

			float dirX = mouseDependentMovementComponent.getFrontVec().x;
			float dirY = mouseDependentMovementComponent.getFrontVec().y;

			Entity projectile = ProjectileFactory.createProjectile(this.ecs,
					dirX + this.playerEntity.getComponent(PositionComponent.class).getMiddleX() - 20,
					dirY + this.playerEntity.getComponent(PositionComponent.class).getMiddleY() - 20, projectileTexture,
					dirX, dirY, this.playerEntity);

			projectile.getComponent(DrawTextureComponent.class)
					.setRotationAngle(playerEntity.getComponent(DrawTextureComponent.class).getRotationAngle());
			projectile.getComponent(MoveComponent.class).setMoving(true);
			this.ecs.addEntity(projectile);

			//play fire sound
			this.fireSound.play(VolumeManager.getInstance().getEnvVolume());
		} else if (InputStates.isRightMouseButtonJustPressed()) {
			if (ProjectileFactory.canShootTorpedo()) {
				MouseDependentMovementComponent mouseDependentMovementComponent = this.playerEntity
						.getComponent(MouseDependentMovementComponent.class);

				float dirX = mouseDependentMovementComponent.getFrontVec().x;
				float dirY = mouseDependentMovementComponent.getFrontVec().y;

				//entity to follow
				Entity enemyEntity = null;

				//TODO: choose nearest enemy shuttle or hovered shuttle
				for (Entity entity : this.enemyEntityList) {
					enemyEntity = entity;
				}

				Entity projectile = ProjectileFactory.createTorpedoProjectile(this.ecs,
						dirX + this.playerEntity.getComponent(PositionComponent.class).getMiddleX() - 30,
						dirY + this.playerEntity.getComponent(PositionComponent.class).getMiddleY() - 30,
						torpedoTexture, dirX, dirY, this.playerEntity, enemyEntity);

				projectile.getComponent(DrawTextureComponent.class)
						.setRotationAngle(playerEntity.getComponent(DrawTextureComponent.class).getRotationAngle());
				projectile.getComponent(MoveComponent.class).setMoving(true);
				this.ecs.addEntity(projectile);

				//play torpedo sound
				this.torpedoSound.play(VolumeManager.getInstance().getEnvVolume() - 0.4f);
			}
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

		batch.flush();

		//reset shader, so default shader is used
		batch.setShader(null);

		batch.end();

		this.shapeRenderer.setProjectionMatrix(game.getCamera().getCombined());
		this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		this.shapeRenderer.setColor(Color.BLACK);

		//draw colliding objects
        boolean debug = true;
        if (debug) {
			this.collisionManager.drawCollisionBoxes(time, game.getCamera(), shapeRenderer, COLLISION_BOX_COLOR,
					IN_COLLISION_COLOR);
		}

		this.shapeRenderer.end();

		batch.begin();
	}

	@Override
	public void destroy() {
		//cleaning up entity-component-system
		this.ecs.dispose();
	}

}
