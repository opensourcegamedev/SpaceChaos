package dev.game.spacechaos.game.entities.component.ai;

import java.util.ArrayList;
import java.util.List;

public class EnemyShuttleAttributeConfiguration {

    private List<EnemyShuttleAttribute> enemyShuttleAttributeList = new ArrayList<>();


    public void initializeEnemyAttributes() {
        enemyShuttleAttributeList.add(new EnemyShuttleAttribute(2.2f, 45, 150, 150, false, 0));
        enemyShuttleAttributeList.add(new EnemyShuttleAttribute(1.1f, 2750, 4000, 4000, true, 4300));
        enemyShuttleAttributeList.add(new EnemyShuttleAttribute(1.5f, 185, 900, 900, true, 3300));
        enemyShuttleAttributeList.add(new EnemyShuttleAttribute(2.7f, 115, 150, 150, false, 0));
        enemyShuttleAttributeList.add(new EnemyShuttleAttribute(2.0f, 85, 150, 150, false, 0));
    }

    public List<EnemyShuttleAttribute> getEnemyShuttleAttributeList() {
        return enemyShuttleAttributeList;
    }
}
