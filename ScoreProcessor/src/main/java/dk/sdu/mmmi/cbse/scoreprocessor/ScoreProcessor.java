package dk.sdu.mmmi.cbse.scoreprocessor;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.player.Player;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author rasan22@student.sdu.dk
 * Class: ScoreProcessor
 * Implements: IScoreProcessorService
 * Provided Interfaces: IScoreProcessorService
 * Required Interfaces: GameData, World, Entity, Asteroid, Bullet, Enemy, Player
 */
@Component
public class ScoreProcessor implements IScoreProcessorService {
    private int destroyedAsteroid = 0;
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
            //gameData.incDestroyedAsteroid();
            setDestroyedAsteroids(getDestroyedAsteroids() + 1);
            addToScore("incDestroyedAsteroids");
        }
        if (entity1.getHealth() <= 0 && entity1 instanceof Enemy && entity2 instanceof Bullet && ((Bullet) entity2).getOwner() instanceof Player) {
            //gameData.incDestroyedEnemies();
            setDestroyedEnemies(getDestroyedEnemies() + 1);
            addToScore("incDestroyedEnemies");
        }
        if (entity2.getHealth() <= 0 && entity2 instanceof Enemy && entity1 instanceof Bullet && ((Bullet) entity1).getOwner() instanceof Player) {
            //gameData.incDestroyedEnemies();
            setDestroyedEnemies(getDestroyedEnemies() + 1);
            addToScore("incDestroyedEnemies");
        }
    }

    public int getDestroyedAsteroids() {
        return destroyedAsteroid;
    }

    public int getDestroyedEnemies() {
        return destroyedEnemies;
    }

    public void setDestroyedAsteroids(int destroyedAsteroid) {
        this.destroyedAsteroid = destroyedAsteroid;
    }

    public void setDestroyedEnemies(int destroyedEnemies) {
        this.destroyedEnemies = destroyedEnemies;
    }

    private void addToScore(String endpoint){
        URL url = null;
        try {
            url = new URL("http://localhost:8080/" + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.getResponseCode();
            connection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
