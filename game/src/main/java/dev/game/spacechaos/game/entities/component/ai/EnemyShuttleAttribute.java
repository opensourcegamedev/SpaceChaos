package dev.game.spacechaos.game.entities.component.ai;

public class EnemyShuttleAttribute {

    private float speed;
    private int scoreByDeath;
    private int currentHealth;
    private int maxHealth;
    private boolean aiRequired;
    private int shootInterval;

    EnemyShuttleAttribute(float speed, int scoreByDeath, int currentHealth, int maxHealth, boolean aiRequired, int shootInterval) {
        this.speed = speed;
        this.scoreByDeath = scoreByDeath;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.aiRequired = aiRequired;
        this.shootInterval = shootInterval;
    }

    public float getSpeed() {
        return speed;
    }

    public int getScoreByDeath() {
        return scoreByDeath;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean getAiRequired() {
        return aiRequired;
    }

    public int getShootInterval() {
        return shootInterval;
    }

}
