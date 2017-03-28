package dev.game.spacechaos.engine.cursor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import dev.game.spacechaos.engine.game.BaseGame;
import dev.game.spacechaos.engine.time.GameTime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 15.02.2017.
 */
public class DefaultCursorManager implements CursorManager {

    protected boolean changed = false;

    protected Pixmap newCursorImage = null;
    protected Cursor.SystemCursor cursor = null;

    protected Map<Pixmap,Cursor> cursorCacheMap = new ConcurrentHashMap<>();

    @Override public void setCursorTexture(Pixmap cursorImage) {
        this.cursor = null;

        if (this.newCursorImage != cursorImage) {
            this.newCursorImage = cursorImage;
            changed = true;
        }
    }

    @Override public void setSystemCursor(Cursor.SystemCursor cursor) {
        this.newCursorImage = null;

        if (this.cursor != cursor) {
            this.cursor = cursor;
            changed = true;
        }
    }

    @Override public void update(BaseGame game, GameTime time) {
        if (this.changed) {
            if (this.newCursorImage != null) {
                Cursor currentCursor = this.cursorCacheMap.get(this.newCursorImage);

                if (currentCursor == null) {
                    currentCursor = Gdx.graphics.newCursor(this.newCursorImage, 0, 0);
                    this.cursorCacheMap.put(this.newCursorImage, currentCursor);
                }

                Gdx.graphics.setCursor(currentCursor);
            } else if (this.cursor != null) {
                Gdx.graphics.setSystemCursor(this.cursor);
            }
        } else {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }

        this.newCursorImage = null;
        this.cursor = null;
        this.changed = false;
    }

    @Override public void resetCursor() {
        this.newCursorImage = null;
        this.cursor = null;
        changed = false;

        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
    }

}
