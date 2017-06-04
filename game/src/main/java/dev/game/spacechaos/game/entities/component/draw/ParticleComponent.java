package dev.game.spacechaos.game.entities.component.draw;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IDrawComponent;
import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.game.fx.BaseParticleEffect;

/**
 * Draws and updates the given {@linkplain BaseParticleEffect particle effects}.
 * <p>
 * Requires a {@link PositionComponent}.
 *
 * @author SpaceChaos-Team
 *         (https://github.com/opensourcegamedev/SpaceChaos/blob/master/CONTRIBUTORS.md)
 * @since 1.0.1-PreAlpha
 */
public class ParticleComponent extends BaseComponent implements IDrawComponent, IUpdateComponent {

    private List<BaseParticleEffect> effects;

    public ParticleComponent(List<BaseParticleEffect> effects) {
        this.effects = effects;
    }

    public ParticleComponent(BaseParticleEffect effect) {
        this.effects = new ArrayList<BaseParticleEffect>();
        effects.add(effect);
    }

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        for (BaseParticleEffect effect : effects) {
            effect.init(entity);
        }
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        for (BaseParticleEffect effect : effects) {
            effect.update(time.getDeltaTime());
        }
    }

    @Override
    public ECSPriority getUpdateOrder() {
        return ECSPriority.DRAW_PARTICLES;
    }

    @Override
    public void draw(GameTime time, CameraWrapper camera, SpriteBatch batch) {
        for (BaseParticleEffect effect : effects) {
            effect.draw(time, batch);
        }
    }

    @Override
    public ECSPriority getDrawOrder() {
        return ECSPriority.DRAW_PARTICLES;
    }
}
