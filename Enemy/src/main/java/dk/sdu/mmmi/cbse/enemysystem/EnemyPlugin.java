package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * @author rasan22@student.sdu.dk
 * Class: EnemyPlugin
 * Implements: IGamePluginService, EnemySPI
 * Provided Interfaces: IGamePluginService, EnemySPI
 * Required Interfaces: none
 */
public class EnemyPlugin implements IGamePluginService, EnemySPI {

    /**
     * Method: start
     * Creates a new instance of Enemy and adds it to the world.
     * @param gameData - The game data object containing the game state.
     * @param world - The world object containing all entities in the game.
     */
    @Override
    public void start(GameData gameData, World world) {
        Entity enemyShip = createEnemy(gameData);
        world.addEntity(enemyShip);
    }

    /**
     * Method: stop
     * Removes all enemy entities from the world.
     * @param gameData - The game data object containing the game state.
     * @param world - The world object containing all entities in the game.
     */

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            world.removeEntity(enemy);
        }
    }

    /**
     * Method: createEnemy
     * Creates a new instance of Enemy with the specified properties.
     * The properties must be set, in order for the enemy to be drawn correctly.
     * @param gameData - The game data object containing the game state.
     * @return enemyShip - The enemy entity that was created.
     */
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
        enemyShip.setHealth(2);
        enemyShip.setDamage(3);
        return enemyShip;
    }

    /**
     * Method: spawnFromLeftUpperCorner
     * Spawn the enemy from the left upper corner of the screen.
     * @param enemyShip - The enemy entity to spawn.
     * @param gameData - The game data object containing the game state.
     */
    private void spawnFromLeftUpperCorner(Entity enemyShip, GameData gameData) {
        Random rnd = new Random();
        enemyShip.setRotation(rnd.nextInt(70) + 10);
        enemyShip.setX(1);
        enemyShip.setY(1);
    }

    /**
     * Method: spawnFromLeftLowerCorner
     * Spawn the enemy from the left lower corner of the screen.
     * @param enemyShip - The enemy entity to spawn.
     * @param gameData - The game data object containing the game state.
     */
    private void spawnFromLeftLowerCorner(Entity enemyShip, GameData gameData) {
        Random rnd = new Random();
        enemyShip.setRotation(rnd.nextInt(70) + 280);
        enemyShip.setX(1);
        enemyShip.setY(gameData.getDisplayHeight() - 1);
    }

    /**
     * Method: spawnFromRightUpperCorner
     * Spawn the enemy from the right upper corner of the screen.
     * @param enemyShip - The enemy entity to spawn.
     * @param gameData - The game data object containing the game state.
     */
    private void spawnFromRightUpperCorner(Entity enemyShip, GameData gameData) {
        Random rnd = new Random();
        enemyShip.setRotation(rnd.nextInt(70) + 100);
        enemyShip.setX(gameData.getDisplayWidth() - 1);
        enemyShip.setY(1);
    }

    /**
     * Method: spawnFromRightLowerCorner
     * Spawn the enemy from the right lower corner of the screen.
     * @param enemyShip - The enemy entity to spawn.
     * @param gameData - The game data object containing the game state.
     */
    private void spawnFromRightLowerCorner(Entity enemyShip, GameData gameData) {
        Random rnd = new Random();
        enemyShip.setRotation(rnd.nextInt(70) + 190);
        enemyShip.setX(gameData.getDisplayWidth() - 1);
        enemyShip.setY(gameData.getDisplayHeight() - 1);
    }
}
