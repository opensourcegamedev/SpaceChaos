package dev.game.spacechaos.engine.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 08.02.2017.
 */
public class HUD {

    /**
    * list with all HUD widgets
    */
    protected List<HUDWidget> widgetList = new ArrayList<>();

    public HUD () {
        //
    }

    public void update (BaseGame game, GameTime time) {
        this.widgetList.stream().forEach(widget -> {
            widget.update(game, time);
        });
    }

    public void drawLayer0 (GameTime time, SpriteBatch batch) {
        this.widgetList.stream().forEach(widget -> {
            widget.drawLayer0(time, batch);
        });
    }

    public void drawLayer1 (GameTime time, ShapeRenderer shapeRenderer) {
        this.widgetList.stream().forEach(widget -> {
            widget.drawLayer1(time, shapeRenderer);
        });
    }

    public void drawLayer2 (GameTime time, SpriteBatch batch) {
        this.widgetList.stream().forEach(widget -> {
            widget.drawLayer2(time, batch);
        });
    }

    public void addWidget (HUDWidget widget) {
        this.widgetList.add(widget);
    }

    public void removeWidget (HUDWidget widget) {
        this.widgetList.remove(widget);
    }

    public void removeAllWidgets () {
        this.widgetList.clear();
    }

}
