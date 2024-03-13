package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemyShip : world.getEntities(Enemy.class)) {
            move(enemyShip);
            rotate(enemyShip);
            checkEnemyShipBounds(enemyShip, gameData);
            changeDirection(enemyShip, gameData.getElapsedTime());
            Random rnd = new Random();
            if (rnd.nextInt(50 ) == 5) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {
                            world.addEntity(spi.createBullet(enemyShip, gameData));
                        }
                );
            }
        }
    }

    public void move(Entity enemyShip) {
        double newX = enemyShip.getX() + enemyShip.getDirectionX();
        double newY = enemyShip.getY() + enemyShip.getDirectionY();
        enemyShip.setX(newX);
        enemyShip.setY(newY);
    };

    private void checkEnemyShipBounds(Entity enemyShip, GameData gameData) {
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

    private void rotate(Entity enemyShip) {
        Random rnd = new Random();
        enemyShip.setRotation(rnd.nextInt(360));
    }

    private void changeDirection(Entity enemyShip, double t) {
        double amplitude = 30; // Amplitude of the sine wave
        double frequency = .1; // Frequency of the sine wave (controls how many cycles per unit of time)
        double phaseShift = 0; // Phase shift of the sine wave
        double initialRotation = ((Enemy) enemyShip).getInitialRotation();

        // Calculate the sine wave value
        double x = initialRotation + amplitude * Math.sin(2 * Math.PI * frequency * t + phaseShift);
        double y = initialRotation + amplitude * Math.sin(2 * Math.PI * frequency * t + phaseShift + .05);
        double directionX = 2 * Math.cos(Math.toRadians(x) );
        double directionY = 2 * Math.sin(Math.toRadians(y));
        enemyShip.setDirectionX(directionX);
        enemyShip.setDirectionY(directionY);
    }


    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
