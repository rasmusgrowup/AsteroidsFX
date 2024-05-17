package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * @author rasan22@student.sdu.dk
 * The IHealthProcessorService interface is used to process the health of entities in the game world.
 */
public interface IHealthProcessorService {
    /**
     * The method is used to specify, what should happen when the score is processed.
     * @param entity1 - The entity object containing the first entity.
     * @param entity2 - The entity object containing the second entity.
     */
    void processHealth(Entity entity1, Entity entity2);
}
