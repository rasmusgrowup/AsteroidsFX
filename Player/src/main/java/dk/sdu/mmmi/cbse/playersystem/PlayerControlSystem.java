package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class PlayerControlSystem implements IEntityProcessingService {
    private static final double ACCELERATION_RATE = 200;
    private static final double DECELERATION_RATE = 25;
    private static final double MAX_SPEED = 400.0;

    @Override
    public void process(GameData gameData, World world) {
            
        for (Entity player : world.getEntities(Player.class)) {
            // Logic for pressing LEFT
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - 2.5);
                updateDirection(player);
            }

            // Logic for pressing RIGHT
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + 2.5);
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

            if(gameData.getKeys().isPressed(GameKeys.SPACE)) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {
                            world.addEntity(spi.createBullet(player, gameData));
                        }
                );
            }

            updatePlayerPosition(player, gameData.getDelta());
            checkPlayerBounds(player, gameData);
        }
    }

    private void accelerate(Entity player, float deltaTime) {
        double accelerationX = PlayerControlSystem.ACCELERATION_RATE * Math.cos(Math.toRadians(player.getRotation()));
        double accelerationY = PlayerControlSystem.ACCELERATION_RATE * Math.sin(Math.toRadians(player.getRotation()));

        player.setVelocityX(player.getVelocityX() + accelerationX * deltaTime);
        player.setVelocityY(player.getVelocityY() + accelerationY * deltaTime);

        // Limit speed to MAX_SPEED
        double speed = Math.sqrt(player.getVelocityX() * player.getVelocityX() + player.getVelocityY() * player.getVelocityY());
        if (speed > MAX_SPEED) {
            double ratio = MAX_SPEED / speed;
            player.setVelocityX(player.getVelocityX() * ratio);
            player.setVelocityY(player.getVelocityY() * ratio);
        }
    }

    private void decelerate(Entity player, double decelerationRate, float deltaTime) {
        // Calculate the magnitude of the current velocity
        double velocityMagnitude = Math.sqrt(player.getVelocityX() * player.getVelocityX() +
                player.getVelocityY() * player.getVelocityY());

        // If the magnitude is smaller than the deceleration rate, stop the player
        if (velocityMagnitude < decelerationRate * deltaTime) {
            player.setVelocityX(0);
            player.setVelocityY(0);
        } else {
            // Calculate the unit vector of the current velocity
            double unitVelocityX = player.getVelocityX() / velocityMagnitude;
            double unitVelocityY = player.getVelocityY() / velocityMagnitude;

            // Apply deceleration in the opposite direction of the current velocity
            double decelerationX = -unitVelocityX * decelerationRate;
            double decelerationY = -unitVelocityY * decelerationRate;

            // Update the velocity
            player.setVelocityX(player.getVelocityX() + decelerationX * deltaTime);
            player.setVelocityY(player.getVelocityY() + decelerationY * deltaTime);
        }
    }


    private void updatePlayerPosition(Entity player, float deltaTime) {
        double newX = player.getX() + player.getVelocityX() * deltaTime;
        double newY = player.getY() + player.getVelocityY() * deltaTime;

        player.setX(newX);
        player.setY(newY);
    }

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

    private void updateDirection(Entity player) {
        double radians = Math.toRadians(player.getRotation());
        double directionX = Math.cos(radians);
        double directionY = Math.sin(radians);

        player.setDirectionX(directionX);
        player.setDirectionY(directionY);
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
