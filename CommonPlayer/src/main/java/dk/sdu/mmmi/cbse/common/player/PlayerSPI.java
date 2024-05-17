package dk.sdu.mmmi.cbse.common.player;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 * @author rasan22@student.sdu.dk
 * Interface: PlayerSPI
 */
public interface PlayerSPI {
    /**
     * Method: createPlayer
     * Creates a player entity.
     * @param gameData - The game data object containing the game state.
     * @return Entity - The player entity.
     */
    Entity createPlayer(GameData gameData);
}
