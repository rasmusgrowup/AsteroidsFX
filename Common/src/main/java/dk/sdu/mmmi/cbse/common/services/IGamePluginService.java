package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * @author rasan22@student.sdu.dk
 * The IGamePluginService interface is used to start and stop the game plugin services.
 */
public interface IGamePluginService {

    /**
     * The method is used to specify, what should happen when the game plugin service is started.
     * @param gameData - The gameData object containing the game data.
     * @param world - The world object containing the game world.
     */
    void start(GameData gameData, World world);

    /**
     * The method is used to specify, what should happen when the game plugin service is stopped.
     * @param gameData - The gameData object containing the game data.
     * @param world - The world object containing the game world.
     */
    void stop(GameData gameData, World world);
}
