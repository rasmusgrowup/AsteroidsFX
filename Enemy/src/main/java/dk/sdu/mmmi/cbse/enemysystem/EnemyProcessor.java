package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemyShip : world.getEntities(Enemy.class)) {
            move(enemyShip);
            checkEnemyShipBounds(enemyShip, gameData);
        }
    }

    public void move(Entity enemyShip) {
        double newX = enemyShip.getX() + enemyShip.getDirectionX();
        double newY = enemyShip.getY() + enemyShip.getDirectionY();
        enemyShip.setX(newX);
        enemyShip.setY(newY);
    };

    private void checkEnemyShipBounds(Entity enemyShip, GameData gameData) {
        if (enemyShip.getX() < 0 - enemyShip.getSize()) {
            enemyShip.setX(gameData.getDisplayWidth() + enemyShip.getSize() - 1);
        }

        if (enemyShip.getX() > gameData.getDisplayWidth() + enemyShip.getSize()) {
            enemyShip.setX(-enemyShip.getSize() + 1);
        }

        if (enemyShip.getY() < 0 - enemyShip.getSize()) {
            enemyShip.setY(gameData.getDisplayHeight() + enemyShip.getSize() - 1);
        }

        if (enemyShip.getY() > gameData.getDisplayHeight() + enemyShip.getSize()) {
            enemyShip.setY(-enemyShip.getSize() + 1);
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
