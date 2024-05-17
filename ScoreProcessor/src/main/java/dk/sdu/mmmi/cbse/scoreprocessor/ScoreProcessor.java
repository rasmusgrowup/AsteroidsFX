package dk.sdu.mmmi.cbse.scoreprocessor;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Score;
import dk.sdu.mmmi.cbse.common.player.Player;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;
import org.springframework.stereotype.Component;

/**
 * @author rasan22@student.sdu.dk
 * Class: ScoreProcessor
 * Implements: IScoreProcessorService
 * Provided Interfaces: IScoreProcessorService
 */
@Component
public class ScoreProcessor implements IScoreProcessorService {

    /**
     * Method: processScore
     * Updates the health of the entities involved in the collision,
     * and increments the destroyedAsteroid or destroyedEnemies counter
     * in gameData if an asteroid or enemy is destroyed.
     * @param entity1 - The first entity involved in the collision.
     * @param entity2 - The second entity involved in the collision.
     */

    @Override
    public void processScore(Entity entity1, Entity entity2, Score score) {
        if (entity1.getHealth() <= 0 && entity1 instanceof Asteroid && entity2 instanceof Bullet && ((Bullet) entity2).getOwner() instanceof Player) {
            score.incrementDestroyedAsteroids();
            System.out.println("Destroyed asteroids: " + score.getDestroyedAsteroids());
        }
        if (entity1.getHealth() <= 0 && entity1 instanceof Enemy && entity2 instanceof Bullet && ((Bullet) entity2).getOwner() instanceof Player) {
            score.incrementDestroyedEnemies();
            System.out.println("Destroyed enemies: " + score.getDestroyedEnemies());
        }
        if (entity2.getHealth() <= 0 && entity2 instanceof Enemy && entity1 instanceof Bullet && ((Bullet) entity1).getOwner() instanceof Player) {
            score.incrementDestroyedEnemies();
            System.out.println("Destroyed enemies: " + score.getDestroyedEnemies());
        }
    }
}
