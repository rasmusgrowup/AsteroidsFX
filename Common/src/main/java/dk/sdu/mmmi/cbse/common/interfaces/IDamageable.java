package dk.sdu.mmmi.cbse.common.interfaces;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * @author rasan22@student.sdu.sdk
 * Interface for damageable entities.
 */
public interface IDamageable {
    void processHealthChanges(Entity entity, World world, GameData gameData);
}
