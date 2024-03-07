package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import javafx.scene.paint.Color;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AsteroidPlugin implements IGamePluginService {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void start(GameData gameData, World world) {
        startAsteroidCreation(gameData, world);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
        stopScheduler();
    }

    public Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        asteroid.setRotation(rnd.nextInt(360));
        double size = 15 + rnd.nextInt(50);
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
        Double acceleration = new Random().nextDouble(0.5);
        double directionX = 0.3 + acceleration * Math.cos(Math.toRadians(asteroid.getRotation()));
        double directionY = 0.3 + acceleration * Math.sin(Math.toRadians(asteroid.getRotation()));
        asteroid.setDirectionX(directionX);
        asteroid.setDirectionY(directionY);
        asteroid.setSize(size);
        asteroid.setFillColor(Color.BLACK);
        asteroid.setStrokeColor(Color.WHITE);
        return asteroid;
    }

    public void startAsteroidCreation(GameData gameData, World world) {
        // Schedule the createAsteroid task to run every 5 seconds
        scheduler.scheduleAtFixedRate(() -> {
            Entity asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
        }, 0, 5, TimeUnit.SECONDS);
    }

    // Method to stop the scheduler (if needed)
    public void stopScheduler() {
        scheduler.shutdown();
    }

    private void spawnFromLeftUpperCorner(Entity asteroid, GameData gameData) {
        Random rnd = new Random();
        asteroid.setRotation(rnd.nextInt(70) + 10);
        asteroid.setX(1);
        asteroid.setY(1);
    }

    private void spawnFromLeftLowerCorner(Entity asteroid, GameData gameData) {
        Random rnd = new Random();
        asteroid.setRotation(rnd.nextInt(70) + 280);
        asteroid.setX(1);
        asteroid.setY(gameData.getDisplayHeight() - 1);
    }

    private void spawnFromRightUpperCorner(Entity asteroid, GameData gameData) {
        Random rnd = new Random();
        asteroid.setRotation(rnd.nextInt(70) + 100);
        asteroid.setX(gameData.getDisplayWidth() - 1);
        asteroid.setY(1);
    }

    private void spawnFromRightLowerCorner(Entity asteroid, GameData gameData) {
        Random rnd = new Random();
        asteroid.setRotation(rnd.nextInt(70) + 190);
        asteroid.setX(gameData.getDisplayWidth() - 1);
        asteroid.setY(gameData.getDisplayHeight() - 1);
    }
}
