package dk.sdu.mmmi.cbse.score;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;
import dk.sdu.mmmi.cbse.playersystem.Player;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rasan22@student.sdu.dk
 * Class: ScoreProcessor
 * Implements: IScoreProcessorService
 * Provided Interfaces: IScoreProcessorService
 * Required Interfaces: GameData, World, Entity, Asteroid, Bullet, Enemy, Player
 */
public class ScoreProcessor implements IScoreProcessorService {
    private final RestTemplate restTemplate = new RestTemplate();
    private int destroyedAsteroids = 0;
    private int destroyedEnemies = 0;

    /**
     * Method: processScore
     * Updates the health of the entities involved in the collision,
     * and increments the destroyedAsteroid or destroyedEnemies counter
     * in gameData if an asteroid or enemy is destroyed.
     * @param gameData - The game data object containing the game state.
     * @param world - The world object containing all entities in the game.
     * @param entity1 - The first entity involved in the collision.
     * @param entity2 - The second entity involved in the collision.
     */

    @Override
    public void processScore(GameData gameData, World world, Entity entity1, Entity entity2) {
        entity1.setHealth(entity1.getHealth() - entity2.getDamage());
        entity2.setHealth(entity2.getHealth() - entity1.getDamage());
        if (entity1.getHealth() <= 0 && entity1 instanceof Asteroid && entity2 instanceof Bullet && ((Bullet) entity2).getOwner() instanceof Player) {
            //gameData.incDestroyedAsteroid(); // non-http way
            setDestroyedAsteroids(getDestroyedAsteroids() + 1);
        }
        if (entity1.getHealth() <= 0 && entity1 instanceof Enemy && entity2 instanceof Bullet && ((Bullet) entity2).getOwner() instanceof Player) {
            //gameData.incDestroyedEnemies(); // non-http way
            setDestroyedEnemies(getDestroyedEnemies() + 1);
        }
        if (entity2.getHealth() <= 0 && entity2 instanceof Enemy && entity1 instanceof Bullet && ((Bullet) entity1).getOwner() instanceof Player) {
            //gameData.incDestroyedEnemies(); // non-http way
            setDestroyedEnemies(getDestroyedEnemies() + 1);
        }
        Map<String, Integer> score = new HashMap<>();
        score.put("destroyedAsteroids", getDestroyedAsteroids());
        score.put("destroyedEnemies", getDestroyedEnemies());
        postScore(score);
    }

    private void postScore(Map<String, Integer> score) {
        restTemplate.postForObject("http://localhost:8080/score", score, Void.class);
    }

    public int getDestroyedAsteroids() {
        return destroyedAsteroids;
    }

    public int getDestroyedEnemies() {
        return destroyedEnemies;
    }

    public void setDestroyedAsteroids(int destroyedAsteroids) {
        this.destroyedAsteroids = destroyedAsteroids;
    }

    public void setDestroyedEnemies(int destroyedEnemies) {
        this.destroyedEnemies = destroyedEnemies;
    }
}
