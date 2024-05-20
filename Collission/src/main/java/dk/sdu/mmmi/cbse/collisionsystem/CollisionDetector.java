package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.services.ICollisionProcessingService;
import dk.sdu.mmmi.cbse.common.services.IHealthProcessorService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.interfaces.IOwnable;
import dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

/**
 * @author rasan22@student.sdu.dk
 * The CollisionDetector class is used to detect collisions between entities in the game.
 * The class implements the IPostEntityProcessingService interface
 * Provided Interfaces: IPostEntityProcessingService
 * Required Interfaces: IScoreProcessorService, OwnerSPI, IHealthProcessorService
 */
public class CollisionDetector implements IPostEntityProcessingService, ICollisionProcessingService {

    /**
     * The method is used to process the game data and world objects, in order to detect collisions between entities.
     * The method loops through all entities in the world and checks if they collide with each other.
     * If a collision is detected, the method calls the processScore method in all IScoreProcessorService implementations,
     * and the processHealth method in all IHealthProcessorService implementations.
     * @param gameData - The gameData object containing the game data.
     * @param world - The world object containing the game world.
     */
    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = new ArrayList<>(world.getEntities());

        for (int i = 0; i < entities.size(); i++) {
            Entity entity1 = entities.get(i);

            for (Entity entity2 : entities.subList(i + 1, entities.size())) {
                if (entity1.getClass().equals(entity2.getClass())) {
                    continue;
                }

                IOwnable ownable1 = getIOwnable(entity1);
                if (ownable1 != null && ownable1.getOwner().equals(entity2)) {
                    continue;
                }

                IOwnable ownable2 = getIOwnable(entity2);
                if (ownable2 != null && ownable2.getOwner().equals(entity1)) {
                    continue;
                }

                if (collides(entity1, entity2)) {
                    for (IHealthProcessorService healthProcessorService : getIHealthProcessorService()) {
                        healthProcessorService.process(entity1, entity2);
                    }
                    for (IScoreProcessorService scoreProcessorService : getIScoreProcessorService()) {
                        scoreProcessorService.process(entity1, entity2, gameData);
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
    @Override
    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    /**
     * Method: getIScoreProcessorService
     * Gets all the Score Processor Services using ServiceLoader.
     * @return A collection of all the Score Processor Services.
     */
    private Collection<? extends IScoreProcessorService> getIScoreProcessorService() {
        return ServiceLoader.load(IScoreProcessorService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    /**
     * Method: getIHealthProcessorService
     * Gets all the Health Processor Services using ServiceLoader.
     * @return A collection of all the Health Processor Services.
     */
    private Collection<? extends IHealthProcessorService> getIHealthProcessorService() {
        return ServiceLoader.load(IHealthProcessorService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    /**
     * Method: getIOwnable
     * Gets the IOwnable interface of an entity.
     * @param entity - The entity to get the IOwnable interface of.
     * @return The IOwnable interface of the entity.
     */
    private IOwnable getIOwnable(Entity entity) {
        if (entity instanceof IOwnable) {
            return (IOwnable) entity;
        } else {
            return null;
        }
    }
}