package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 * @author rasan22@student.sdu.dk
 * Interface for the CollisionProcessingService
 * Preconditions: The game data and world must be initialized.
 * Postconditions: The collision processing service is implemented.
 */
public interface ICollisionProcessingService {
    Boolean collides(Entity entity1, Entity entity2);
}
