package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {

    private EnemySPI enemySPI; // Reference to the AsteroidSPI instance

    public CollisionDetector() {
        // Get the AsteroidSPI instance using ServiceLoader
        this.enemySPI = getEnemySPI();
    }

    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                // if the two entities are identical, skip the iteration
                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }

                // if the two entities are asteroids, continue
                if (entity1 instanceof Asteroid && entity2 instanceof Asteroid) {
                    continue;
                }

                // if the bullet collides with its owner, continue
                if (entity1 instanceof Bullet && entity2.equals(((Bullet) entity1).getOwner())) {
                    continue;
                }

                // if the bullet collides with its owner, continue
                if (entity2 instanceof Bullet && entity1.equals(((Bullet) entity2).getOwner())) {
                    continue;
                }

                // CollisionDetection
                if (this.collides(entity1, entity2)) {
                    if (entity1 instanceof Enemy) {
                        System.out.println("Added new enemy");
                        world.addEntity(getEnemySPI().createEnemy(gameData));
                        gameData.incDestroyedEnemies();
                    }
                    if (entity2 instanceof Enemy) {
                        System.out.println("Added new enemy");
                        world.addEntity(getEnemySPI().createEnemy(gameData));
                        gameData.incDestroyedEnemies();
                    }
                    if (entity1 instanceof Asteroid) {
                        gameData.incDestroyedAsteroid();
                    }
                    if (entity2 instanceof Asteroid) {
                        gameData.incDestroyedAsteroid();
                    }
                    world.removeEntity(entity1);
                    world.removeEntity(entity2);
                }
            }
        }
    }

    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    private EnemySPI getEnemySPI() {
        return ServiceLoader.load(EnemySPI.class).findFirst().orElse(null);
    }
}