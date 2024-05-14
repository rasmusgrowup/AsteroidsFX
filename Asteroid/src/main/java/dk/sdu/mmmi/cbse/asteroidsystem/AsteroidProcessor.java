package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Arrays;
import java.util.Random;

public class AsteroidProcessor implements IEntityProcessingService {
    private AsteroidPlugin asteroidPlugin = new AsteroidPlugin();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            move(asteroid);
            checkAsteroidsBounds(asteroid, gameData);
            rotate(asteroid);
            updateHealth(asteroid, world, gameData);
        }
    }

    private void updateHealth(Entity asteroid, World world, GameData gameData) {
        if (asteroid.getHealth() <= 0) {
            if (asteroid.getSize() > 15) {
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
            world.removeEntity(asteroid);
            //gameData.incDestroyedAsteroid();
        }
    }

    public void move(Entity asteroid) {
        double newX = asteroid.getX() + asteroid.getDirectionX();
        double newY = asteroid.getY() + asteroid.getDirectionY();
        asteroid.setX(newX);
        asteroid.setY(newY);
    };

    public void rotate(Entity asteroid) {
        asteroid.setRotation(asteroid.getRotation() + new Random().nextDouble(0.4));
    }

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
