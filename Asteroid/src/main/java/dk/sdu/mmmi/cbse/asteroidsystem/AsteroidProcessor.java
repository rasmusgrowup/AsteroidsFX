package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroid.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Arrays;
import java.util.Random;

/**
 * @author rasan22@student.sdu.dk
 * This class is responsible for processing asteroids.
 * It moves the asteroids, checks if they are out of bounds, rotates them and updates their health.
 * Implements the IEntityProcessingService interface.
 * Provided Interfaces: IEntityProcessingService
 * Required Interfaces: None
 */
public class AsteroidProcessor implements IEntityProcessingService, IAsteroidSplitter {
    private AsteroidPlugin asteroidPlugin = new AsteroidPlugin();

    /**
     * This method processes the asteroids.
     * It moves the asteroids, checks if they are out of bounds, rotates them and updates their health.
     * @param gameData GameData object
     * @param world World object
     */
    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            move(asteroid);
            checkAsteroidsBounds(asteroid, gameData);
            rotate(asteroid);
            updateHealth(asteroid, world, gameData);
        }
    }

    /**
     * This method updates the health of the asteroid.
     * If the asteroid's health is less than or equal to 0, the asteroid is removed from the world.
     * If the asteroid's size is greater than 15, the asteroid is split into two smaller asteroids.
     * @param asteroid Entity object
     * @param world World object
     * @param gameData GameData object
     */
    private void updateHealth(Entity asteroid, World world, GameData gameData) {
        if (asteroid.getHealth() <= 0) {
            if (asteroid.getSize() > 15) {
                splitAsteroid(gameData, world, asteroid);
            }
            world.removeEntity(asteroid);
        }
    }

    @Override
    public void splitAsteroid(GameData gameData, World world, Entity asteroid) {
        Entity asteroid1 = asteroidPlugin.createAsteroid(gameData);
        Entity asteroid2 = asteroidPlugin.createAsteroid(gameData);
        double[] polygonCoordinates = Arrays.stream(asteroid.getPolygonCoordinates()).map(point -> point * 0.5).toArray();
        asteroid1.setPolygonCoordinates(polygonCoordinates);
        asteroid2.setPolygonCoordinates(polygonCoordinates);
        asteroid1.setSize(asteroid.getSize() / 2);
        asteroid2.setSize(asteroid.getSize() / 2);
        asteroid1.setDirectionX(asteroid.getDirectionX());
        asteroid2.setDirectionY(asteroid.getDirectionY());
        asteroid1.setRadius((float) asteroid1.getSize());
        asteroid2.setRadius((float) asteroid2.getSize());
        asteroid1.setX(asteroid.getX());
        asteroid1.setY(asteroid.getY());
        asteroid2.setX(asteroid.getX());
        asteroid2.setY(asteroid.getY());
        world.addEntity(asteroid1);
        world.addEntity(asteroid2);
    }

    /**
     * This method moves the asteroid.
     * It calculates the new x and y coordinates of the asteroid based on its direction.
     * @param asteroid Entity object
     */
    public void move(Entity asteroid) {
        double newX = asteroid.getX() + asteroid.getDirectionX();
        double newY = asteroid.getY() + asteroid.getDirectionY();
        asteroid.setX(newX);
        asteroid.setY(newY);
    };

    /**
     * This method rotates the asteroid.
     * It rotates the asteroid by a random angle.
     * @param asteroid Entity object
     */
    public void rotate(Entity asteroid) {
        asteroid.setRotation(asteroid.getRotation() + new Random().nextDouble(0.4));
    }

    /**
     * This method checks if the asteroid is out of bounds.
     * If the asteroid is out of bounds, it is moved to the opposite side of the screen.
     * @param asteroid Entity object
     * @param gameData GameData object
     */
    private void checkAsteroidsBounds(Entity asteroid, GameData gameData) {
        if (asteroid.getX() < 0 - asteroid.getSize()) {
            asteroid.setX(gameData.getDisplayWidth() + asteroid.getSize() - 1);
        }

        if (asteroid.getX() > gameData.getDisplayWidth() + asteroid.getSize()) {
            asteroid.setX(-asteroid.getSize() + 1);
        }

        if (asteroid.getY() < 0 - asteroid.getSize()) {
            asteroid.setY(gameData.getDisplayHeight() + asteroid.getSize() - 1);
        }

        if (asteroid.getY() > gameData.getDisplayHeight() + asteroid.getSize()) {
            asteroid.setY(-asteroid.getSize() + 1);
        }
    }
}
