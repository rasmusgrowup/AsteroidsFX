package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Random;

public class AsteroidProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            move(asteroid);
            checkAsteroidsBounds(asteroid, gameData);
            rotate(asteroid);
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

    private void checkAsteroidBounds(Entity asteroid, GameData gameData) {
        if (asteroid.getX() < 0 || asteroid.getX() > gameData.getDisplayWidth()) {
            asteroid.setDirectionX(-asteroid.getDirectionX());
        }

        if (asteroid.getY() < 0 || asteroid.getY() > gameData.getDisplayHeight()) {
            asteroid.setDirectionY(-asteroid.getDirectionY());
        }
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
