package dev.game.spacechaos.game.entities.component.draw;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IDrawUILayerComponent;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.SpriteBatcherUtils;
import dev.game.spacechaos.game.entities.component.combat.HPComponent;

/**
 * Created by Justin on 14.03.2017.
 */
public class DrawHPBarComponent extends BaseComponent implements IDrawUILayerComponent {

    protected PositionComponent positionComponent = null;
    protected HPComponent hpComponent = null;

    protected float offsetX = 0;
    protected float offsetY = 0;

    protected Color backgroundColor = Color.RED;
    protected Color foregroundColor = Color.GREEN;
    protected float barWidth = 0;
    protected float barHeight = 5;

    public DrawHPBarComponent(float offsetX, float offsetY, float width, float height) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.barWidth = width;
        this.barHeight = height;
    }

    public DrawHPBarComponent(float offsetX, float offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public void onInit (BaseGame game, Entity entity) {
        super.init(game, entity);

        this.positionComponent = entity.getComponent(PositionComponent.class);
        this.hpComponent = entity.getComponent(HPComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }

        if (this.hpComponent == null) {
            throw new IllegalStateException("entity doesnt have an HPComponent.");
        }

        if (this.barWidth == 0) {
            this.barWidth = this.positionComponent.getWidth();
        }
    }

    @Override
    public void drawUILayer (GameTime time, CameraWrapper camera, SpriteBatch batch) {
        //get current HP in percent
        float percent = this.hpComponent.getPercent();

        //calculate x and y position
        float x = this.positionComponent.getX() + offsetX;
        float y = this.positionComponent.getY() + this.positionComponent.getHeight() + offsetY;

        //draw background
        SpriteBatcherUtils.fillRectangle(batch, x, y, barWidth, barHeight, backgroundColor);

        //draw foreground
        SpriteBatcherUtils.fillRectangle (batch, x, y, barWidth * percent, barHeight, foregroundColor);
    }

    @Override
    public ECSPriority getUILayerDrawOrder() {
        return ECSPriority.DRAW_HUD;
    }

}
