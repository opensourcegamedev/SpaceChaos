package dev.game.spacechaos.engine.cursor;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

/**
 * Created by Justin on 15.02.2017.
 */
public interface CursorManager {

    public void setCursorTexture(Pixmap cursorImage);

    public void setSystemCursor(Cursor.SystemCursor cursor);

    public void update(BaseGame game, GameTime time);

    public void resetCursor();

}
