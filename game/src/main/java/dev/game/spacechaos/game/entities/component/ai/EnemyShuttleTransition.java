package dev.game.spacechaos.game.entities.component.ai;

public class EnemyShuttleTransition {

    public int maxHealth;
    public float speed;
    public int scoreByDeath;
    public int currentHealth;
    public boolean aiRequired;
    public int shootIntervall;

    EnemyShuttleTransition(int currentHealth, int maxHealth, float speed, int scoreByDeath,
                           boolean aiRequired, int shootIntervall) {

        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.speed = speed;
        this.scoreByDeath = scoreByDeath;
        this.aiRequired = aiRequired;
        this.shootIntervall = shootIntervall;
    }
}
