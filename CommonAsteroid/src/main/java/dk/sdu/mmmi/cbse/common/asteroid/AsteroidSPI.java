package dk.sdu.mmmi.cbse.common.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author rasan22
 */
public interface AsteroidSPI {
    Entity createAsteroid(Entity e, GameData gameData);
}
