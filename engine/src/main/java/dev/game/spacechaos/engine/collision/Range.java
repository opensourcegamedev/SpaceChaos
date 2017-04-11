package dev.game.spacechaos.engine.collision;

/**
 * Created by Justin on 11.04.2017.
 */
public class Range {

    protected float min = 0;
    protected float max = 0;

    public Range (float min, float max) {
        set(min, max);
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

}
