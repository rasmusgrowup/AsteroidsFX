package dk.sdu.mmmi.cbse.common.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 * @author rasan22@student.sdu.dk
 * Interface for creating asteroids.
 */
public interface AsteroidSPI {

    /**
     * Creates an asteroid entity.
     * @param gameData The game data.
     * @return The asteroid entity.
     */
    Entity createAsteroid(GameData gameData);
}
