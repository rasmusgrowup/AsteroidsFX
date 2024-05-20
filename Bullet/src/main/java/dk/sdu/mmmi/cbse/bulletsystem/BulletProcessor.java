package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.interfaces.IDamageable;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

/**
 * @author rasan22@student.sdu.dk
 * The BulletControlSystem class controls the behavior of bullets in the game.
 * The class implements the IEntityProcessingService and BulletSPI interfaces
 * Provided Interfaces: IEntityProcessingService
 * Required Interfaces: None
 */
public class BulletProcessor implements IEntityProcessingService, IDamageable {

    /**
     * The method processes the game data and world objects, in order to control the behavior of bullets in the game.
     * The method loops through all bullet entities in the world and updates their position.
     * If a bullet entity has no health left, the method removes the bullet entity from the world.
     * @param gameData - The gameData object containing the game data.
     * @param world - The world object containing the game world.
     */
    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + changeX * 8);
            bullet.setY(bullet.getY() + changeY * 8);
            processHealthChanges(bullet, world, gameData);
        }
    }

    /**
     * The method updates the health of a bullet entity.
     * If the health of the bullet entity is less than or equal to 0, the method removes the bullet entity from the world.
     * @param bullet - The bullet entity to update the health of.
     * @param world - The world object containing the game world.
     * @param gameData - The gameData object containing the game data.
     */
    @Override
    public void processHealthChanges(Entity bullet, World world, GameData gameData) {
        if (bullet.getHealth() <= 0) {
            world.removeEntity(bullet);
        }
    }
}
