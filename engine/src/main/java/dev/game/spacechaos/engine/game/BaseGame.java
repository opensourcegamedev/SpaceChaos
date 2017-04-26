package dev.game.spacechaos.engine.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.cursor.CursorManager;
import dev.game.spacechaos.engine.cursor.DefaultCursorManager;
import dev.game.spacechaos.engine.settings.GameSettings;
import dev.game.spacechaos.engine.settings.IniGameSettings;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.FileUtils;
import dev.game.spacechaos.engine.window.ResizeListener;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Justin on 06.02.2017.
 */
public abstract class BaseGame extends ApplicationAdapter {

    /**
    * list with resize listeners
    */
    protected List<ResizeListener> resizeListeners = new ArrayList<>();

    /**
    * instance of game time
    */
    protected GameTime time = GameTime.getInstance();

    /**
    * backround color
    */
    protected Color bgColor = Color.BLACK;

    /**
    * sprite batcher
    */
    protected SpriteBatch batch = null;

    /**
    * instance of asset manager
    */
    protected final AssetManager assetManager = new AssetManager();

    protected static int VIEWPORT_WIDTH = 1280;
    protected static int VIEWPORT_HEIGHT = 720;

    protected OrthographicCamera camera = null;
    protected CameraWrapper cameraWrapper = null;
    private Camera uiCamera = null;

    protected AtomicBoolean useCamera = new AtomicBoolean(false);

    protected String settingsDir = "./data/config/";

    /**
    * map with all game settings
    */
    protected Map<String,GameSettings> settingsMap = new ConcurrentHashMap<>();

    protected FPSLogger fpsLogger = new FPSLogger();
    protected String shaderPath = "./data/shader/";

    protected CursorManager cursorManager = null;

    //tasks which should be executed in OpenGL context thread
    protected Queue<Runnable> uiQueue = new ConcurrentLinkedQueue<>();

    protected long lastFPSWarning = 0;

    @Override
    public void resize(final int width, final int height) {
        this.resizeListeners.stream().forEach(consumer -> {
            consumer.onResize(width, height);
        });
    }

    public void addResizeListener (ResizeListener listener) {
        this.resizeListeners.add(listener);
    }

    public void removeResizeListener (ResizeListener listener) {
        this.resizeListeners.remove(listener);
    }

    public AssetManager getAssetManager () {
        return this.assetManager;
    }

    @Override
    public final void create() {
        //create sprite batcher
        this.batch = new SpriteBatch();

        this.VIEWPORT_WIDTH = Gdx.graphics.getWidth();
        this.VIEWPORT_HEIGHT = Gdx.graphics.getHeight();

        //initialize camera
        this.camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        this.camera.translate(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2, 0);
        //this.camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);

        this.cameraWrapper = new CameraWrapper(this.camera);
        this.cameraWrapper.update(this.time);

        //initialize UI camera
        this.uiCamera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        this.uiCamera.translate(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2, 0);
        this.uiCamera.update();

        //create new cursor manager
        this.cursorManager = new DefaultCursorManager();

        try {
            this.initGame();
        } catch (Exception e) {
            e.printStackTrace();

            try {
                //write crash dump
                FileUtils.writeFile("./crash.log", e.getLocalizedMessage(), StandardCharsets.UTF_8);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            System.exit(0);
        }
    }

    @Override
    public final void render() {
        //update time
        this.time.update();

        int fps = getFPS();
        if (fps <= 59 && fps != 0) {
            //check if warning was already logged this second
            long now = System.currentTimeMillis();
            long nowWarnSecond = now / 1000;
            long lastWarnSecond = lastFPSWarning / 1000;

            if (nowWarnSecond != lastWarnSecond) {
                System.err.println("Warning! FPS is <= 59, FPS: " + fps);

                lastFPSWarning = System.currentTimeMillis();
            }
        }

        //execute tasks, which should be executed in OpenGL context thread
        Runnable runnable = uiQueue.poll();

        while (runnable != null) {
            runnable.run();

            runnable = uiQueue.poll();
        }

        //update game
        this.update(this.time);

        //set cursor
        this.cursorManager.update(this, this.time);

        //update camera
        //this.camera.update();
        this.cameraWrapper.update(this.time);

        //update UI camera
        this.uiCamera.update();

        //clear all color buffer bits and clear screen
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.batch.begin();

        this.batch.setProjectionMatrix(this.camera.combined);

        //draw game
        this.draw(time, this.batch);

        this.batch.end();

        //System.out.println("render calls: " + this.batch.renderCalls);

        //this.fpsLogger.log();
    }

    protected abstract void initGame ();

    public CameraWrapper getCamera () {
        return this.cameraWrapper;
    }

    public /*OrthographicCamera*/CameraWrapper getCamera2D () {
        return this.cameraWrapper;
    }

    public Camera getUICamera () {
        return this.uiCamera;
    }

    public int getFPS () {
        return Gdx.graphics.getFramesPerSecond();
    }

    public String getShaderDir () {
        return this.shaderPath;
    }

    public void runOnUIThread (Runnable runnable) {
        this.uiQueue.offer(runnable);
    }

    /**
    * get instance of settings or null, if instance doesnst exists
     *
     * @param name name of settings
     *
     * @return instance of settings
    */
    public GameSettings getSettings (String name) {
        name = name.toLowerCase();

        GameSettings settings = this.settingsMap.get(name);

        if (settings == null) {
            throw new NullPointerException("instance of settings (name: " + name + ") is null.");
        }

        return settings;
    }

    /**
     * get instance of global settings
     *
     * @return instance of global settings
     */
    public GameSettings getSettings () {
        //get global settings
        return this.getSettings("game");
    }

    /**
    * load settings
    */
    public boolean loadSettings (String name, String path) {
        name = name.toLowerCase();
        path = path.toLowerCase();

        GameSettings settings = new IniGameSettings();

        try {
            if (new File(path).exists()) {
                settings.loadFromFile(new File(path));
            } else {
                if (new File(settingsDir + path).exists()) {
                    settings.loadFromFile(new File(settingsDir + path));
                } else {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        this.settingsMap.put(name, settings);

        return true;
    }

    public float getVolume () {
        return this.getSettings().getFloat("Music", "volume");
    }

    public float getVolume (float addVolume) {
        float volume = this.getSettings().getFloat("Music", "volume");

        volume += addVolume;

        if (volume > 1) {
            volume = 1;
        }

        return volume;
    }

    public String getLang () {
        return this.getSettings().getOrDefault("Game", "lang", "en");
    }

    public CursorManager getCursorManager () {
        return this.cursorManager;
    }

    public int getViewportWidth () {
        return this.VIEWPORT_WIDTH;
    }

    public int getViewportHeight () {
        return this.VIEWPORT_HEIGHT;
    }

    protected abstract void update (GameTime time);

    protected abstract void draw (GameTime time, SpriteBatch batch);

    @Override
    public final void dispose() {
        this.destroyGame();

        this.batch.dispose();
    }

    protected abstract void destroyGame ();

}
