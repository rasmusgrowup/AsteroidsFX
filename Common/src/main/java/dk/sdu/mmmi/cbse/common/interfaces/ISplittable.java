package dk.sdu.mmmi.cbse.common.interfaces;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * @author rasan22@student.sdu.dk
 * Class: IAsteroidSplitter
 */
public interface ISplittable {
    void split(GameData gameData, World world, Entity asteroid);
}
