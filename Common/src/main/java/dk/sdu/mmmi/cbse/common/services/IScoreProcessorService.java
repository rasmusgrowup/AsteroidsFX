package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * @author rasan22@student.sdu.dk
 * The IScoreProcessorService interface is used to process the score of the game.
 */
public interface IScoreProcessorService {

    /**
     * The method is used to specify, what should happen when the score is processed.
     * @param gameData - The gameData object containing the game data.
     * @param world - The world object containing the game world.
     * @param entity1 - The entity object containing the first entity.
     * @param entity2 - The entity object containing the second entity.
     */
    void processScore(GameData gameData, World world, Entity entity1, Entity entity2);
}
