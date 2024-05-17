package dk.sdu.mmmi.cbse.common.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * @author rasan22@student.sdu.dk
 * Class: IAsteroidSplitter
 */
public interface IAsteroidSplitter {
    public void splitAsteroid(GameData gameData, World world, Entity asteroid);
}
