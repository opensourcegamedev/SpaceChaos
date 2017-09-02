package dev.game.spacechaos.game.entities.component.ai;

import java.util.ArrayList;
import java.util.List;

public class EnemyShuttleTransitionConfiguration {

    private List<EnemyShuttleTransition> enemyTransition = new ArrayList<>();

     public void initializeEnemyTransitions() {
         enemyTransition.add(new EnemyShuttleTransition(150, 300, 3.5f, 25, false, 0));
         enemyTransition.add(new EnemyShuttleTransition(4000, 4000, 1.1f, 2250, true, 5000));
         enemyTransition.add(new EnemyShuttleTransition(700, 700, 1.5f, 125, true, 4000));
         enemyTransition.add(new EnemyShuttleTransition(150, 300, 4.0f, 65, false, 0));
         enemyTransition.add(new EnemyShuttleTransition(150, 300, 5.0f, 115, false, 0));
    }

    public List<EnemyShuttleTransition> getEnemyTransition() {
        return enemyTransition;
    }
}
