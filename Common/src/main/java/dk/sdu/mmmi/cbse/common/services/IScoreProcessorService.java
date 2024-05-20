package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 * @author rasan22@student.sdu.dk
 * The IScoreProcessorService interface is used to process the score of the game.
 */
public interface IScoreProcessorService {

    /**
     * The method is used to specify, what should happen when the score is processed.
     * @param entity1 - The entity object containing the first entity.
     * @param entity2 - The entity object containing the second entity.
     */
    void process(Entity entity1, Entity entity2, GameData gameData);
}
