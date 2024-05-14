package dk.sdu.mmmi.cbse.score;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;
import dk.sdu.mmmi.cbse.playersystem.Player;

/**
 * Author: rasan22
 *
 */
public class ScoreProcessor implements IScoreProcessorService {

    @Override
    public void processScore(GameData gameData, World world, Entity entity1, Entity entity2) {
        entity1.setHealth(entity1.getHealth() - entity2.getDamage());
        entity2.setHealth(entity2.getHealth() - entity1.getDamage());
        if (entity1.getHealth() <= 0 && entity1 instanceof Asteroid && entity2 instanceof Bullet && ((Bullet) entity2).getOwner() instanceof Player) {
            gameData.incDestroyedAsteroid();
        }
        if (entity1.getHealth() <= 0 && entity1 instanceof Enemy && entity2 instanceof Bullet && ((Bullet) entity2).getOwner() instanceof Player) {
            gameData.incDestroyedEnemies();
        }
//        if (entity2.getHealth() <= 0 && entity2 instanceof Asteroid && entity1 instanceof Bullet && ((Bullet) entity1).getOwner() instanceof Player) {
//            gameData.incDestroyedAsteroid();
//        }
        if (entity2.getHealth() <= 0 && entity2 instanceof Enemy && entity1 instanceof Bullet && ((Bullet) entity1).getOwner() instanceof Player) {
            gameData.incDestroyedEnemies();
        }
    }
}
