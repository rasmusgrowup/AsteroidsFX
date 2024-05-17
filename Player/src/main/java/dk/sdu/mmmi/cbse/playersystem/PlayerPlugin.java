package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.player.Player;
import dk.sdu.mmmi.cbse.common.player.PlayerSPI;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import javafx.scene.paint.Color;

/**
 * @author rasan22@student.sdu.dk
 * Class: PlayerPlugin
 * Implements: IGamePluginService
 * Provided Interfaces: IGamePluginService
 * Required Interfaces: none
 */
public class PlayerPlugin implements IGamePluginService, PlayerSPI {

    private Entity player;

    /**
     * Method: start
     * Creates a new instance of Player and adds it to the world.
     * @param gameData - The game data object containing the game state.
     * @param world - The world object containing all entities in the game.
     */
    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    /**
     * Method: createPlayerShip
     * Creates a new instance of Player with the specified properties.
     * The properties must be set, in order for the player to be drawn correctly.
     * @param gameData - The game data object containing the game state.
     * @return playerShip - The player entity that was created.
     */
    @Override
    public Entity createPlayer(GameData gameData) {
        Entity playerShip = new Player();
        playerShip.setFillColor(Color.BLACK);
        playerShip.setStrokeColor(Color.WHITE);
        playerShip.setPolygonCoordinates(-15,-15,25,0,-15,15);
        playerShip.setX(gameData.getDisplayHeight()/2);
        playerShip.setY(gameData.getDisplayWidth()/2);
        playerShip.setSize(40);
        playerShip.setRadius(15);
        playerShip.setVelocityX(0);
        playerShip.setVelocityY(0);
        playerShip.setHealth(3);
        playerShip.setDamage(3);
        gameData.setPlayerHealth(playerShip.getHealth());
        return playerShip;
    }

    /**
     * Method: stop
     * Removes the player entity from the world.
     * @param gameData - The game data object containing the game state.
     * @param world - The world object containing all entities in the game.
     */
    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }
}
