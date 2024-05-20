package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * @author rasan22@student.sdu.dk
 * Interface for creating bullets.
 */
public interface BulletSPI {

    /**
     * Creates a bullet entity.
     * @param e The entity that is shooting the bullet.
     * @param gameData The game data.
     * @return The bullet entity.
     */
    Entity createBullet(Entity e, GameData gameData);
}
