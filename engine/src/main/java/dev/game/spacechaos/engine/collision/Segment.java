package dev.game.spacechaos.engine.collision;

import com.badlogic.gdx.math.Vector2;
import dev.game.spacechaos.engine.collision.pool.RangePool;
import dev.game.spacechaos.engine.collision.pool.RangePoolFactory;

/**
 * Created by Justin on 11.04.2017.
 */
public class Segment {

    protected Vector2 point1 = null;
    protected Vector2 point2 = null;

    public Segment (Vector2 point1, Vector2 point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Range projectSegment (Vector2 onto) {
        //create new range or use an recycled range
        Range range = RangePool.create();

        //normailize vector
        onto.nor();

        //calculate min and max value with dot product
        float min = onto.dot(point1);
        float max = onto.dot(point2);
        range.set(min, max);

        return range;
    }

    /*public boolean overlaps (Segment b) {
        //
    }*/

}
