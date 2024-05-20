package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import javafx.scene.paint.Color;

public class BulletPlugin implements IGamePluginService, BulletSPI {

    @Override
    public void start(GameData gameData, World world) {}

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Bullet.class) {
                world.removeEntity(e);
            }
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
