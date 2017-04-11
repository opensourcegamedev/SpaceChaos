package dev.game.spacechaos.engine.collision;

import com.badlogic.gdx.utils.Pool;

/**
 * Created by Justin on 11.04.2017.
 */
public class Range implements Pool.Poolable {

    protected float min = 0;
    protected float max = 0;

    public Range (float min, float max) {
        set(min, max);
    }

    public Range () {
        //
    }

    public float getMin () {
        return this.min;
    }

    public float getMax () {
        return this.max;
    }

    public void set (float min, float max) {
        if (min > max) {
            //swap values
            float d = max;
            max = min;
            min = d;
        }

        this.min = min;
        this.max = max;
    }

    public boolean overlaps (Range r) {
        return ColliderUtils.overlaping(this.min, this.max, r.min, r.max);
    }

    public Range hull (Range b, Range newRange) {
        Range rangeHull = newRange;

        rangeHull.min = this.min < b.min ? this.min : b.min;
        rangeHull.max = this.max > b.max ? this.max : b.max;

        return rangeHull;
    }

    public Range hull (Range b) {
        return this.hull(b, new Range());
    }

    @Override
    public void reset() {
        this.min = 0;
        this.max = 0;
    }
}
