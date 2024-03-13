package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import javafx.scene.paint.Color;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class EnemyPlugin implements IGamePluginService, EnemySPI {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void start(GameData gameData, World world) {
        Entity enemyShip = createEnemy(gameData);
        world.addEntity(enemyShip);
    }

    @Override
    public void stop(GameData gameData, World world) {
        stopScheduler();
    }

    @Override
    public Entity createEnemy(GameData gameData) {
        Enemy enemyShip = new Enemy();
        int sizeFactor = 3;
        int numPoints = 36;
        double radius = sizeFactor * 7;
        enemyShip.setSize(radius);
        double[] coordinates = new double[numPoints * 2];
        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            coordinates[2 * i] = radius * Math.cos(angle);
            coordinates[2 * i + 1] = radius * Math.sin(angle);
        }
        enemyShip.setPolygonCoordinates(coordinates);
        Random rnd = new Random();
        switch (rnd.nextInt(4)) {
            case 0: spawnFromLeftUpperCorner(enemyShip, gameData); break;
            case 1: spawnFromLeftLowerCorner(enemyShip, gameData); break;
            case 2: spawnFromRightUpperCorner(enemyShip, gameData); break;
            case 3: spawnFromRightLowerCorner(enemyShip, gameData); break;
            default:
                System.out.println("An error occurred in switch case EnemyPlugin");
        }
        double initialRotation = enemyShip.getRotation();
        enemyShip.setInitialRotation(initialRotation);
        enemyShip.setCanShoot(false);
        double directionX = 2 * Math.cos(Math.toRadians(enemyShip.getRotation()));
        double directionY = 2 * Math.sin(Math.toRadians(enemyShip.getRotation()));
        enemyShip.setDirectionX(directionX);
        enemyShip.setDirectionY(directionY);
        enemyShip.setRadius(sizeFactor * 7);
        enemyShip.setFillColor(Color.BLACK);
        enemyShip.setStrokeColor(Color.WHITE);
        //startScheduler(enemyShip);
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

    public void stopScheduler() {
        scheduler.shutdown();
    }
}
