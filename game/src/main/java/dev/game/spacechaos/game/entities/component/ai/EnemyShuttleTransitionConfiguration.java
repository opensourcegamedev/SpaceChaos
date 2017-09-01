package dev.game.spacechaos.game.entities.component.ai;

import java.util.ArrayList;
import java.util.List;

public class EnemyShuttleTransitionConfiguration {

    private List<EnemyShuttleTransition> enemyTransition = new ArrayList<>();

     public void initializeEnemyTransitions() {
         enemyTransition.add(new EnemyShuttleTransition(50, 50, 3.0f, 80,
                 true, 0));
         enemyTransition.add(new EnemyShuttleTransition(4000, 4000, 1.1f, 1000,
                 true, 100));
         enemyTransition.add(new EnemyShuttleTransition(1000, 1000, 1.5f, 200,
                 true, 700));
         enemyTransition.add(new EnemyShuttleTransition(1000, 1000, 1.5f, 200,
                 false, 500));
         enemyTransition.add(new EnemyShuttleTransition(1000, 1000, 1.5f, 200,
                 false, 600));
    }

    public List<EnemyShuttleTransition> getEnemyTransition() {
        return enemyTransition;
    }
}
