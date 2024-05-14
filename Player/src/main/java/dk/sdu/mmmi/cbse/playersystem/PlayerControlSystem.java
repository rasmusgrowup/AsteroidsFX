package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

/**
 * @author rasan22@student.sdu.dk
 * Class: PlayerControlSystem
 * Implements: IEntityProcessingService
 * Provided Interfaces: IEntityProcessingService
 * Required Interfaces: BulletSPI
 */
public class PlayerControlSystem implements IEntityProcessingService {
    private static final double ACCELERATION_RATE = 200;
    private static final double DECELERATION_RATE = 25;
    private static final double MAX_SPEED = 400.0;
    private static int currentHealth;

    /**
     * Method: process
     * Updates the player's position, rotation, and velocity based on the player's input,
     * checks if the player is outside the game window, and updates the player's health.
     * @param gameData - The game data object containing the game state.
     * @param world - The world object containing all entities in the game.
    */
    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            // Logic for pressing LEFT
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - 3.5);
                updateDirection(player);
            }

            // Logic for pressing RIGHT
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + 3.5);
                updateDirection(player);
            }

            // Logic for pressing UP
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                accelerate(player, gameData.getDelta());
            } else {
                decelerate(player, DECELERATION_RATE, gameData.getDelta());
            }

            if (gameData.getKeys().isDown(GameKeys.DOWN)) {
                decelerate(player, DECELERATION_RATE * 4, gameData.getDelta());
            }

            // Logic for pressing SPACE
            if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {
                            world.addEntity(spi.createBullet(player, gameData));
                        }
                );
            }
            updatePlayerPosition(player, gameData.getDelta());
            checkPlayerBounds(player, gameData);
            updateHealth(player, gameData);
        }
    }

    /**
     * Method: accelerate
     * Accelerates the player based on the player's rotation and the acceleration rate.
     * @param player - The player entity to accelerate.
     * @param deltaTime - The time since the last update.
     */
    private void accelerate(Entity player, float deltaTime) {
        double accelerationX = PlayerControlSystem.ACCELERATION_RATE * Math.cos(Math.toRadians(player.getRotation()));
        double accelerationY = PlayerControlSystem.ACCELERATION_RATE * Math.sin(Math.toRadians(player.getRotation()));

        player.setVelocityX(player.getVelocityX() + accelerationX * deltaTime);
        player.setVelocityY(player.getVelocityY() + accelerationY * deltaTime);

        double speed = Math.sqrt(player.getVelocityX() * player.getVelocityX() + player.getVelocityY() * player.getVelocityY());
        if (speed > MAX_SPEED) {
            double ratio = MAX_SPEED / speed;
            player.setVelocityX(player.getVelocityX() * ratio);
            player.setVelocityY(player.getVelocityY() * ratio);
        }
    }

    /**
     * Method: decelerate
     * Decelerates the player based on the deceleration rate.
     * @param player - The player entity to decelerate.
     * @param decelerationRate - The rate at which the player should decelerate.
     * @param deltaTime - The time since the last update.
     */
    private void decelerate(Entity player, double decelerationRate, float deltaTime) {

        double velocityMagnitude = Math.sqrt(player.getVelocityX() * player.getVelocityX() + player.getVelocityY() * player.getVelocityY());

        if (velocityMagnitude < decelerationRate * deltaTime) {
            player.setVelocityX(0);
            player.setVelocityY(0);
        } else {
            double unitVelocityX = player.getVelocityX() / velocityMagnitude;
            double unitVelocityY = player.getVelocityY() / velocityMagnitude;
            double decelerationX = -unitVelocityX * decelerationRate;
            double decelerationY = -unitVelocityY * decelerationRate;
            player.setVelocityX(player.getVelocityX() + decelerationX * deltaTime);
            player.setVelocityY(player.getVelocityY() + decelerationY * deltaTime);
        }
    }


    /**
     * Method: updatePlayerPosition
     * Updates the player's position based on the player's velocity,
     * and the Player's current position.
     * @param player - The player entity to update the position of.
     * @param deltaTime - The time since the last update.
     */
    private void updatePlayerPosition(Entity player, float deltaTime) {
        double newX = player.getX() + player.getVelocityX() * deltaTime;
        double newY = player.getY() + player.getVelocityY() * deltaTime;

        player.setX(newX);
        player.setY(newY);
    }

    /**
     * Method: checkPlayerBounds
     * Checks if the player is outside the game window, and moves the player to the opposite side if so.
     * @param player - The player entity to check the bounds of.
     * @param gameData - The game data object containing the game state.
     */
    private void checkPlayerBounds(Entity player, GameData gameData) {
        if (player.getX() < 0) {
            player.setX(gameData.getDisplayWidth() - 1);
        }

        if (player.getX() > gameData.getDisplayWidth()) {
            player.setX(1);
        }

        if (player.getY() < 0) {
            player.setY(gameData.getDisplayHeight() - 1);
        }

        if (player.getY() > gameData.getDisplayHeight()) {
            player.setY(1);
        }
    }

    /**
     * Method: updateDirection
     * Updates the player's direction based on the player's rotation.
     * @param player - The player entity to update the direction of.
     */
    private void updateDirection(Entity player) {
        double radians = Math.toRadians(player.getRotation());
        double directionX = Math.cos(radians);
        double directionY = Math.sin(radians);

        player.setDirectionX(directionX);
        player.setDirectionY(directionY);
    }

    /**
     * Method: reset
     * Resets the player's position, velocity, and rotation to the center of the game window.
     * @param player - The player entity to reset.
     * @param gameData - The game data object containing the game state.
     */
    private void reset(Entity player, GameData gameData) {
        player.setX(gameData.getDisplayWidth() / 2);
        player.setY(gameData.getDisplayHeight() / 2);
        player.setVelocityX(0);
        player.setVelocityY(0);
        player.setRotation(0);
    }

    /**
     * Method: updateHealth
     * Updates the player's health, and sets the game over flag if the player's health is 0.
     * @param player - The player entity to update the health of.
     * @param gameData - The game data object containing the game state.
     */
    private void updateHealth(Entity player, GameData gameData) {
        if (player.getHealth() <= 0) {
            gameData.setGameover(true);
        } else if (player.getHealth() != currentHealth) {
            reset(player, gameData);
        }
        currentHealth = player.getHealth();
        gameData.setPlayerHealth(player.getHealth());
    }

    /**
     * Method: getBulletSPIs
     * Returns a collection of all BulletSPI implementations.
     */
    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
