package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * @author rasan22@student.sdu.dk
 * The IPostEntityProcessingService interface is used to process the entities after they have been processed.
 */
public interface IPostEntityProcessingService {

    /**
     * The method is used to specify, what should happen when the entities are processed.
     * @param gameData - The gameData object containing the game data.
     * @param world - The world object containing the game world.
     */
    void process(GameData gameData, World world);
}
