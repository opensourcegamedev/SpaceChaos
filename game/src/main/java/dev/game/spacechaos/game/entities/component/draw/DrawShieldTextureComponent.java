package dev.game.spacechaos.game.entities.component.draw;

import com.badlogic.gdx.graphics.Texture;

import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.annotation.InjectComponent;
import dev.game.spacechaos.engine.entity.component.draw.DrawTextureComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.game.entities.component.combat.ShieldComponent;
import dev.game.spacechaos.game.fx.BaseParticleEffect;

@InjectComponent
public class DrawShieldTextureComponent extends DrawTextureComponent implements IUpdateComponent {

    @InjectComponent(nullable = false)
    private ShieldComponent shieldComponent = null;

    public DrawShieldTextureComponent(Texture texture, float originX, float originY) {
        super(texture, originX, originY);
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        if (isVisible()) {
            if (shieldComponent.getCurrentShieldHP() <= 0)
                setVisible(false);
        } else {
            if(shieldComponent.getCurrentShieldHP() > 0)
                setVisible(true);
        }
    }

    @Override
    public ECSPriority getUpdateOrder() {
        return ECSPriority.DRAW_HOVER_EFFECT;
    }

}
