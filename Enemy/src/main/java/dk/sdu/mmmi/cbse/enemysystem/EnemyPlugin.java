package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import javafx.scene.paint.Color;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        Entity enemyShip = createEnemy(gameData);
        world.addEntity(enemyShip);
    }

    @Override
    public void stop(GameData gameData, World world) {

    }

    private Entity createEnemy(GameData gameData) {
        Entity enemyShip = new Enemy();
        double sizeFactor = 2;
        enemyShip.setPolygonCoordinates(
                sizeFactor * 0,
                sizeFactor * -7,
                sizeFactor * 5,
                sizeFactor * -5,
                sizeFactor * 7,
                sizeFactor * 0,
                sizeFactor * 5,
                sizeFactor * 5,
                sizeFactor * 0,
                sizeFactor * 7,
                sizeFactor * -5,
                sizeFactor * 5,
                sizeFactor * -7,
                sizeFactor * 0,
                sizeFactor * -5,
                sizeFactor * -5
        );

        /*
        enemyShip.setPolygonCoordinates(
                sizeFactor * -15,
                sizeFactor * 0,
                sizeFactor * -7,
                sizeFactor * 7,
                sizeFactor * 7,
                sizeFactor * 7,
                sizeFactor * 15,
                sizeFactor * 0,
                sizeFactor * 7,
                sizeFactor * -7,
                sizeFactor * -7,
                sizeFactor * -7);
         */
        Random rnd = new Random();
        switch (rnd.nextInt(4)) {
            case 0: spawnFromLeftUpperCorner(enemyShip, gameData); break;
            case 1: spawnFromLeftLowerCorner(enemyShip, gameData); break;
            case 2: spawnFromRightUpperCorner(enemyShip, gameData); break;
            case 3: spawnFromRightLowerCorner(enemyShip, gameData); break;
            default:
                System.out.println("An error occurred in switch case EnemyPlugin");
        }
        double directionX = 2 * Math.cos(Math.toRadians(enemyShip.getRotation()));
        double directionY = 2 * Math.sin(Math.toRadians(enemyShip.getRotation()));
        enemyShip.setDirectionX(directionX);
        enemyShip.setDirectionY(directionY);
        enemyShip.setFillColor(Color.BLACK);
        enemyShip.setStrokeColor(Color.WHITE);
        return enemyShip;
    }

    private void spawnFromLeftUpperCorner(Entity enemyShip, GameData gameData) {
        Random rnd = new Random();
        enemyShip.setRotation(rnd.nextInt(70) + 10);
        enemyShip.setX(1);
        enemyShip.setY(1);
    }

    private void spawnFromLeftLowerCorner(Entity enemyShip, GameData gameData) {
        Random rnd = new Random();
        enemyShip.setRotation(rnd.nextInt(70) + 280);
        enemyShip.setX(1);
        enemyShip.setY(gameData.getDisplayHeight() - 1);
    }

    private void spawnFromRightUpperCorner(Entity enemyShip, GameData gameData) {
        Random rnd = new Random();
        enemyShip.setRotation(rnd.nextInt(70) + 100);
        enemyShip.setX(gameData.getDisplayWidth() - 1);
        enemyShip.setY(1);
    }

    private void spawnFromRightLowerCorner(Entity enemyShip, GameData gameData) {
        Random rnd = new Random();
        enemyShip.setRotation(rnd.nextInt(70) + 190);
        enemyShip.setX(gameData.getDisplayWidth() - 1);
        enemyShip.setY(gameData.getDisplayHeight() - 1);
    }
}
