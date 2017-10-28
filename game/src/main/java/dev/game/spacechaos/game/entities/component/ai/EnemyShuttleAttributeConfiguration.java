package dev.game.spacechaos.game.entities.component.ai;

import java.util.ArrayList;
import java.util.List;

public class EnemyShuttleAttributeConfiguration {

    private List<EnemyShuttleAttribute> enemyShuttleAttributeList = new ArrayList<>();


    public void initializeEnemyAttributes() {
        enemyShuttleAttributeList.add(new EnemyShuttleAttribute(2.2f, 25, 30, 30, false, 0));
        enemyShuttleAttributeList.add(new EnemyShuttleAttribute(1.1f, 2250, 4000, 4000, true, 5000));
        enemyShuttleAttributeList.add(new EnemyShuttleAttribute(1.5f, 125, 700, 700, true, 4000));
        enemyShuttleAttributeList.add(new EnemyShuttleAttribute(2.7f, 65, 30, 30, false, 0));
        enemyShuttleAttributeList.add(new EnemyShuttleAttribute(2.0f, 115, 30, 30, false, 0));
    }

    public List<EnemyShuttleAttribute> getEnemyShuttleAttributeList() {
        return enemyShuttleAttributeList;
    }
}
