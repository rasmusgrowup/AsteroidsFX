package dk.sdu.mmmi.cbse.common.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 * @author rasan22@student.sdu.dk
 * Interface: EnemySPI
 */
public interface EnemySPI {
    /**
     * Method: createEnemy
     * Creates an enemy entity.
     * @param gameData - The game data object containing the game state.
     * @return Entity - The enemy entity.
     */
    Entity createEnemy(GameData gameData);
}