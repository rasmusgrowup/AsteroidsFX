package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

/**
 * @author rasan22@student.sdu.dk
 * The CollisionDetector class is used to detect collisions between entities in the game.
 * The class implements the IPostEntityProcessingService interface
 * Provided Interfaces: IPostEntityProcessingService
 * Required Interfaces: IScoreProcessorService
 */
public class CollisionDetector implements IPostEntityProcessingService {

    /**
     * The method is used to process the game data and world objects, in order to detect collisions between entities.
     * The method loops through all entities in the world and checks if they collide with each other.
     * If a collision is detected, the method calls the processScore method in all IScoreProcessorService implementations.
     * @param gameData - The gameData object containing the game data.
     * @param world - The world object containing the game world.
     */
    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        Collection<Entity> entities = world.getEntities();

        for (Entity entity1 : entities) {
            for (Entity entity2 : entities) {
                if (entity1.equals(entity2)) {
                    continue;
                }

                if (entity1 instanceof Asteroid && entity2 instanceof Asteroid) {
                    continue;
                }

                if (entity1 instanceof Bullet && entity2.equals(((Bullet) entity1).getOwner())) {
                    continue;
                } else if (entity2 instanceof Bullet && entity1.equals(((Bullet) entity2).getOwner())) {
                    continue;
                }

                if (collides(entity1, entity2)) {
                    for (IScoreProcessorService scoreProcessorService : getIScoreProcessorService()) {
                        scoreProcessorService.processScore(gameData, world, entity1, entity2);
                    }
                }
            }
        }
    }

    /**
     * The method is used to check if two entities collide with each other.
     * The method calculates the distance between the two entities and checks if the distance is less than the sum of the radii of the two entities.
     * @param entity1 - The first entity to check for collision.
     * @param entity2 - The second entity to check for collision.
     * @return - Returns a boolean value indicating if the two entities collide with each other.
     */
    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    /**
     * The method gets all IScoreProcessorService implementations.
     * The method uses the ServiceLoader class to load all IScoreProcessorService implementations.
     * @return - Returns a collection of IScoreProcessorService implementations.
     */
    private Collection<? extends IScoreProcessorService> getIScoreProcessorService() {
        return ServiceLoader.load(IScoreProcessorService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}