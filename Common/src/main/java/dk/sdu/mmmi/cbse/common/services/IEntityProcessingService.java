package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * @author rasan22@student.sdu.dk
 * The IEntityProcessingService interface is used to process entities in the game world.
 */
public interface IEntityProcessingService {

    /**
     * The method is used to process entities in the game world.
     * @param gameData - The gameData object containing the game data.
     * @param world - The world object containing the game world.
     */
    void process(GameData gameData, World world);
}
