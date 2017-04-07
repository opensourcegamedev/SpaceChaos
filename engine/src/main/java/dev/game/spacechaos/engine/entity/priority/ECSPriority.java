package dev.game.spacechaos.engine.entity.priority;

/**
 * Created by Justin on 10.02.2017.
 */
public enum ECSPriority {

    BACKGROUND(1),

    DRAW_HUD(2),

    DRAW_PARTICLES(3),

    VERY_LOW(4),

    LOW(5),

    DRAW_SHADOW(6),

    DRAW_HOVER_EFFECT(7),

    NORMAL(8),

    HIGH(9),

    VERY_HIGH(10),

    HUD(11);

    private final int id;

    ECSPriority(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }

}
