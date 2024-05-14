package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import javafx.scene.paint.Color;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class is responsible for creating asteroids and adding them to the world.
 * It also schedules the creation of asteroids every 5 seconds.
 * Implements the IGamePluginService and AsteroidSPI interfaces.
 * Provided Interfaces: IGamePluginService, AsteroidSPI
 * Required Interfaces: None
 */
public class AsteroidPlugin implements IGamePluginService, AsteroidSPI {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * This method is called when the plugin is started.
     * It starts the creation of asteroids.
     * @param gameData GameData object
     * @param world World object
     */
    @Override
    public void start(GameData gameData, World world) {
        startAsteroidCreation(gameData, world);
    }

    /**
     * This method is called when the plugin is stopped.
     * It stops the creation of asteroids and removes all asteroids from the world.
     * @param gameData GameData object
     * @param world World object
     */
    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
        stopScheduler();
    }

    /**
     * This method creates an asteroid entity.
     * The asteroid is given a random rotation and size.
     * The asteroid is spawned from one of the four corners of the screen.
     * The asteroid is given a random direction and speed.
     * @param gameData - GameData object
     * @return Entity object
     */
    @Override
    public Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        asteroid.setRotation(rnd.nextInt(360));
        int size = 15 + rnd.nextInt(50);
        double c1 = Math.cos(Math.PI * 2 / 5);
        double c2 = Math.cos(Math.PI / 5);
        double s1 = Math.sin(Math.PI * 2 / 5);
        double s2 = Math.sin(Math.PI * 4 / 5);
        asteroid.setPolygonCoordinates(
                size, 0.0,
                size * c1, -1 * size * s1,
                -1 * size * c2, -1 * size * s2,
                -1 * size * c2, size * s2,
                size * c1, size * s1
        );
        asteroid.setX(rnd.nextInt(gameData.getDisplayWidth()));
        asteroid.setY(rnd.nextInt(gameData.getDisplayHeight()));
        switch (rnd.nextInt(4)) {
            case 0: spawnFromLeftUpperCorner(asteroid, gameData); break;
            case 1: spawnFromLeftLowerCorner(asteroid, gameData); break;
            case 2: spawnFromRightUpperCorner(asteroid, gameData); break;
            case 3: spawnFromRightLowerCorner(asteroid, gameData); break;
            default:
                System.out.println("An error occurred in switch case EnemyPlugin");
        }
        double acceleration = new Random().nextDouble(0.5);
        double directionX = 0.3 + acceleration * Math.cos(Math.toRadians(asteroid.getRotation()));
        double directionY = 0.3 + acceleration * Math.sin(Math.toRadians(asteroid.getRotation()));
        asteroid.setDirectionX(directionX);
        asteroid.setDirectionY(directionY);
        asteroid.setSize(size);
        asteroid.setRadius(size);
        asteroid.setFillColor(Color.BLACK);
        asteroid.setStrokeColor(Color.WHITE);
        asteroid.setHealth(1);
        asteroid.setDamage(3);
        return asteroid;
    }

    /**
     * This method schedules the creation of asteroids every 5 seconds.
     * @param gameData - GameData object
     * @param world - World object
     */
    public void startAsteroidCreation(GameData gameData, World world) {
        // Schedule the createAsteroid task to run every 5 seconds
        scheduler.scheduleAtFixedRate(() -> {
            Entity asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
        }, 0, 5, TimeUnit.SECONDS);
    }

    /**
     * This method stops the scheduler.
     */
    public void stopScheduler() {
        scheduler.shutdown();
    }

    /**
     * This method spawns an asteroid from the left upper corner of the screen.
     * @param asteroid - Entity object
     * @param gameData - GameData object
     */
    private static void spawnFromLeftUpperCorner(Entity asteroid, GameData gameData) {
        Random rnd = new Random();
        asteroid.setRotation(rnd.nextInt(70) + 10);
        asteroid.setX(1);
        asteroid.setY(1);
    }

    /**
     * This method spawns an asteroid from the left lower corner of the screen.
     * @param asteroid - Entity object
     * @param gameData - GameData object
     */
    private static void spawnFromLeftLowerCorner(Entity asteroid, GameData gameData) {
        Random rnd = new Random();
        asteroid.setRotation(rnd.nextInt(70) + 280);
        asteroid.setX(1);
        asteroid.setY(gameData.getDisplayHeight() - 1);
    }

    /**
     * This method spawns an asteroid from the right upper corner of the screen.
     * @param asteroid - Entity object
     * @param gameData - GameData object
     */
    private static void spawnFromRightUpperCorner(Entity asteroid, GameData gameData) {
        Random rnd = new Random();
        asteroid.setRotation(rnd.nextInt(70) + 100);
        asteroid.setX(gameData.getDisplayWidth() - 1);
        asteroid.setY(1);
    }

    /**
     * This method spawns an asteroid from the right lower corner of the screen.
     * @param asteroid - Entity object
     * @param gameData - GameData object
     */
    private static void spawnFromRightLowerCorner(Entity asteroid, GameData gameData) {
        Random rnd = new Random();
        asteroid.setRotation(rnd.nextInt(70) + 190);
        asteroid.setX(gameData.getDisplayWidth() - 1);
        asteroid.setY(gameData.getDisplayHeight() - 1);
    }
}
