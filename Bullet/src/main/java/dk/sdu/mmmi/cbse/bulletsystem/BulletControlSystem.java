package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import javafx.scene.paint.Color;

/**
 * @author rasan22@student.sdu.dk
 * The BulletControlSystem class controls the behavior of bullets in the game.
 * The class implements the IEntityProcessingService and BulletSPI interfaces
 * Provided Interfaces: IEntityProcessingService, BulletSPI
 * Required Interfaces: None
 */
public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

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
            updateHealth(bullet, world);
        }
    }

    /**
     * The method updates the health of a bullet entity.
     * If the health of the bullet entity is less than or equal to 0, the method removes the bullet entity from the world.
     * @param bullet - The bullet entity to update the health of.
     * @param world - The world object containing the game world.
     */
    private void updateHealth(Entity bullet, World world) {
        if (bullet.getHealth() <= 0) {
            world.removeEntity(bullet);
        }
    }

    /**
     * The method creates a bullet entity.
     * The method creates a bullet entity with a specific owner, color, polygon coordinates, position, rotation, health, and damage.
     * @param shooter - The entity that shoots the bullet.
     * @param gameData - The gameData object containing the game data.
     * @return Entity - The bullet entity created.
     */
    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Bullet bullet = new Bullet(shooter);
        bullet.setFillColor(Color.WHITE);
        bullet.setStrokeColor(Color.WHITE);
        bullet.setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        bullet.setRotation(shooter.getRotation());
        bullet.setHealth(1);
        bullet.setDamage(1);
        return bullet;
    }
}
