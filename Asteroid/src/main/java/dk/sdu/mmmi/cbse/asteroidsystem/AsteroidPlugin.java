package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import javafx.scene.paint.Color;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {
    private double spawnInterval = 5;
    private double lastSpawnTime = 0;

    @Override
    public void start(GameData gameData, World world) {
    }

    public void updateAsteroids(GameData gameData, World world) {
        double elapsedTime = gameData.getElapsedTime();

        // Check if 5 seconds have passed since the last spawn
        if (elapsedTime - lastSpawnTime >= spawnInterval) {
            Random rnd = new Random();
            Entity asteroid = createAsteroid(rnd.nextInt(gameData.getDisplayWidth()), rnd.nextInt(gameData.getDisplayHeight()));
            world.addEntity(asteroid);

            // Update the last spawn time
            lastSpawnTime = elapsedTime;
        }
    }

    private Entity createAsteroid(int i, int i1) {
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
        asteroid.setX(i);
        asteroid.setY(i1);
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

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }
}
