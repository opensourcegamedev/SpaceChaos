package dev.game.spacechaos.engine.entity.component.draw;

import com.badlogic.gdx.math.Vector2;
import dev.game.spacechaos.engine.camera.CameraWrapper;
import dev.game.spacechaos.engine.entity.BaseComponent;
import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.IUpdateComponent;
import dev.game.spacechaos.engine.entity.component.PositionComponent;
import dev.game.spacechaos.engine.entity.priority.ECSPriority;
import dev.game.spacechaos.engine.exception.RequiredComponentNotFoundException;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;
import dev.game.spacechaos.engine.utils.MouseUtils;

/**
 * Created by Justin on 08.04.2017.
 */
public class MouseDependentDrawRotationAngle extends BaseComponent implements IUpdateComponent {

    //required components
    protected PositionComponent positionComponent = null;
    protected DrawTextureComponent textureComponent = null;
    protected DrawTextureRegionComponent textureRegionComponent = null;

    protected DrawComponent drawComponent = null;

    protected float frontX = 0;
    protected float frontY = 0;

    protected Vector2 tmpVector = new Vector2();

    @Override
    protected void onInit(BaseGame game, Entity entity) {
        //get components
        this.positionComponent = entity.getComponent(PositionComponent.class);
        this.textureComponent = entity.getComponent(DrawTextureComponent.class);
        this.textureRegionComponent = entity.getComponent(DrawTextureRegionComponent.class);

        //check, if components are available
        if (this.positionComponent == null) {
            throw new IllegalStateException("entity doesnt have an PositionComponent.");
        }

        if (this.textureComponent ==  null && this.textureRegionComponent == null) {
            throw new RequiredComponentNotFoundException("entity doesnt have an DrawTextureComponent or DrawTextureRegionComponent");
        } else if (this.textureRegionComponent != null) {
            this.drawComponent = this.textureRegionComponent;
        } else if (this.textureComponent != null) {
            this.drawComponent = this.textureComponent;
        }
    }

    @Override
    public void update(BaseGame game, GameTime time) {
        //get camera
        CameraWrapper camera = game.getCamera();

        //get mouse angle relative to shuttle
        float angle = MouseUtils.getRelativeMouseAngle(camera, positionComponent.getMiddleX(), positionComponent.getMiddleY()) - 90;

        //rotate texture
        this.drawComponent.setRotationAngle(angle);
    }

    @Override
    public ECSPriority getUpdateOrder() {
        return ECSPriority.NORMAL;
    }

}
