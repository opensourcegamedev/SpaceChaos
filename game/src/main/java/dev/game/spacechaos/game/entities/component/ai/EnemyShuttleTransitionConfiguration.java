package dev.game.spacechaos.game.entities.component.ai;

import java.util.ArrayList;
import java.util.List;

public class EnemyShuttleTransitionConfiguration {

    private List<EnemyShuttleTransition> enemyTransition = new ArrayList<>();

     public void initializeEnemyTransitions() {
         enemyTransition.add(new EnemyShuttleTransition(100, 100, 3.5f, 40, false, 0));
         enemyTransition.add(new EnemyShuttleTransition(4000, 4000, 1.1f, 1250, true, 5000));
         enemyTransition.add(new EnemyShuttleTransition(1000, 1000, 1.5f, 200, true, 4000));
         enemyTransition.add(new EnemyShuttleTransition(100, 100, 4.0f, 80, false, 0));
         enemyTransition.add(new EnemyShuttleTransition(100, 100, 5.0f, 120, false, 0));
    }

    public List<EnemyShuttleTransition> getEnemyTransition() {
        return enemyTransition;
    }
}
