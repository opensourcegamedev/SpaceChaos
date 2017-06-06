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
import dev.game.spacechaos.game.entities.component.combat.ShieldComponent;

/**
 * Draws the health-bar of the shield an entity has.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.0-PreAlpha
 */
public class DrawShieldHPBarComponent extends BaseComponent implements IDrawUILayerComponent {

    private PositionComponent positionComponent = null;
    private ShieldComponent shieldComponent = null;

    private float offsetX = 0;
    private float offsetY = 0;

    private Color backgroundColor = Color.ORANGE;
    private Color foregroundColor = Color.SKY;
    private float barWidth = 0;
    private float barHeight = 5;

    public DrawShieldHPBarComponent(float offsetX, float offsetY, float width, float height) {
        this(offsetX, offsetY);
        this.barWidth = width;
        this.barHeight = height;
    }

    private DrawShieldHPBarComponent(float offsetX, float offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public void onInit(BaseGame game, Entity entity) {
        this.positionComponent = entity.getComponent(PositionComponent.class);
        this.shieldComponent = entity.getComponent(ShieldComponent.class);

        if (this.positionComponent == null) {
            throw new IllegalStateException("Entity doesn't have a PositionComponent.");
        }

        if (this.shieldComponent == null) {
            throw new IllegalStateException("Entity doesn't have a ShieldComponent.");
        }

        if (this.barWidth == 0) {
            this.barWidth = this.positionComponent.getWidth();
        }
    }

    @Override
    public void drawUILayer(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        if (this.shieldComponent.getCurrentShieldHP() > 0 || this.shieldComponent.doesRegenerate()) {
            // get current HP in percent
            float percent = this.shieldComponent.getCurrentShieldHP() / this.shieldComponent.getMaxShieldHP();

            // calculate x and y position
            float x = this.positionComponent.getX() + offsetX;
            float y = this.positionComponent.getY() + this.positionComponent.getHeight() + offsetY;

            // draw background
            SpriteBatcherUtils.fillRectangle(batch, x, y, barWidth, barHeight, backgroundColor);

            // draw foreground
            SpriteBatcherUtils.fillRectangle(batch, x, y, barWidth * percent, barHeight, foregroundColor);
        }
    }

    @Override
    public ECSPriority getUILayerDrawOrder() {
        return ECSPriority.DRAW_HUD;
    }

}
