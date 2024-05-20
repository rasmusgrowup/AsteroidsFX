package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.interfaces.IDamageable;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.interfaces.IMovable;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

/**
 * @author rasan22@student.sdu.dk
 * Class: EnemyProcessor
 * Implements: IEntityProcessingService
 * Provided Interfaces: IEntityProcessingService
 * Required Interfaces: BulletSPI
 */
public class EnemyProcessor implements IEntityProcessingService, IMovable, IDamageable {
    private EnemyPlugin enemyPlugin = new EnemyPlugin();

    /**
     * Method: process
     * Updates the enemy's position, rotation, direction, and health.
     * @param gameData - The game data object containing the game state.
     * @param world - The world object containing all entities in the game.
     */
    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemyShip : world.getEntities(Enemy.class)) {
            move(enemyShip);
            rotate(enemyShip);
            checkBounds(enemyShip, gameData);
            updateDirection(enemyShip, gameData);
            Random rnd = new Random();
            if (rnd.nextInt(50 ) == 5) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {
                            world.addEntity(spi.createBullet(enemyShip, gameData));
                        }
                );
            }
            processHealthChanges(enemyShip, world, gameData);
        }
    }

    /**
     * Method: move
     * Moves the enemy in the direction specified by its directionX and directionY properties.
     * @param enemyShip - The enemy entity to move.
     */
    @Override
    public void move(Entity enemyShip) {
        double newX = enemyShip.getX() + enemyShip.getDirectionX();
        double newY = enemyShip.getY() + enemyShip.getDirectionY();
        enemyShip.setX(newX);
        enemyShip.setY(newY);
    };

    /**
     * Method: checkEnemyShipBounds
     * Checks if the enemy is outside the game window, and moves it to the opposite side if it is.
     * @param enemyShip - The enemy entity to check.
     * @param gameData - The game data object containing the game state.
     */
    @Override
    public void checkBounds(Entity enemyShip, GameData gameData) {
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

    /**
     * Method: rotate
     * Rotates the enemy to a random angle, between 0 and 360 degrees.
     * This is used to make the enemy shoot in a random direction.
     * @param enemyShip - The enemy entity to rotate.
     */
    @Override
    public void rotate(Entity enemyShip) {
        Random rnd = new Random();
        enemyShip.setRotation(rnd.nextInt(360));
    }

    /**
     * Method: changeDirection
     * Changes the enemy's direction based on a sine wave.
     * This is used to make the enemy move in a wavy pattern.
     * @param enemyShip - The enemy entity to change the direction of.
     * @param gameData - The game data object containing the game state.
     */
    public void updateDirection(Entity enemyShip, GameData gameData) {
        double amplitude = 30; // Amplitude of the sine wave
        double frequency = .1; // Frequency of the sine wave (controls how many cycles per unit of time)
        double phaseShift = 0; // Phase shift of the sine wave
        double initialRotation = ((Enemy) enemyShip).getInitialRotation();
        double t = gameData.getElapsedTime();

        // Calculate the sine wave value
        double x = initialRotation + amplitude * Math.sin(2 * Math.PI * frequency * t + phaseShift);
        double y = initialRotation + amplitude * Math.sin(2 * Math.PI * frequency * t + phaseShift + .05);
        double directionX = 2 * Math.cos(Math.toRadians(x) );
        double directionY = 2 * Math.sin(Math.toRadians(y));
        enemyShip.setDirectionX(directionX);
        enemyShip.setDirectionY(directionY);
    }

    /**
     * Method: updateHealth
     * Updates the enemy's health, and respawns the enemy if its health is 0.
     * @param enemy - The enemy entity to update the health of.
     * @param gameData - The game data object containing the game state.
     * @param world - The world object containing all entities in the game.
     */
    @Override
    public void processHealthChanges(Entity enemy, World world, GameData gameData) {
        if (enemy.getHealth() <= 0) {
            world.removeEntity(enemy);
            //gameData.incDestroyedEnemies();
            Entity newEnemy = enemyPlugin.createEnemy(gameData);
            world.addEntity(newEnemy);
        }
    }

    /**
     * Method: getBulletSPIs
     * Gets all the BulletSPI implementations from the ServiceLoader.
     */
    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
