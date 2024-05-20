package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.*;
import dk.sdu.mmmi.cbse.common.services.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.stream.Collectors.toList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author rasan22@student.sdu.dk
 * Class: Main
 * Implements: Application
 * Provided Interfaces: none
 * Required Interfaces: IEntityProcessingService, IGamePluginService, IPostEntityProcessingService, IScoreProcessorService
 */
public class Game extends Application {
    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private Text text1, text2, text3, text4;
    private long startTime = System.nanoTime();

    /**
     * Method: start
     * Initializes the game window, the game data, and the world.
     * Starts the game loop and updates the game window.
     * @param window - The JavaFX stage object representing the game window.
     */
    @Override
    public void start(Stage window) throws Exception {
        text1 = new Text(10, 20, "Destroyed asteroids: " + gameData.getScore().getDestroyedAsteroids());
        text2 = new Text(10, 50, "Destroyed enemies: " + gameData.getScore().getDestroyedEnemies());
        text3 = new Text(10, gameData.getDisplayHeight() - 10, "Health: " + gameData.getPlayerHealth());
        text4 = new Text(120, gameData.getDisplayHeight() / 2 + 50, "GAME OVER");
        text1.setFill(Color.RED);
        text2.setFill(Color.RED);
        text3.setFill(Color.RED);
        text4.setFill(Color.RED);
        text4.setStyle("-fx-font-size: 100; -fx-font-weight: bold");
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(text1);
        gameWindow.getChildren().add(text2);
        gameWindow.getChildren().add(text3);
        gameWindow.setBackground(Background.fill(Color.BLACK));

        var scene = getScene(gameWindow, gameData);

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }
        gameData.getScore().clearScore();
        render();
        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();
        window.setResizable(false);
    }

    private static Scene getScene(Pane gameWindow, GameData gameData) {
        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
            if (event.getCode().equals(KeyCode.DOWN)) {
                gameData.getKeys().setKey(GameKeys.DOWN, true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
            if (event.getCode().equals(KeyCode.DOWN)) {
                gameData.getKeys().setKey(GameKeys.DOWN, false);
            }
        });
        return scene;
    }

    /**
     * Method: stop
     * Stops the game loop and releases resources.
     * @throws Exception - Throws an exception if the game loop cannot be stopped.
     */
    @Override
    public void stop() throws Exception {
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.stop(gameData, world);
        }
        gameData.getScore().clearScore();
        super.stop();
    }

    /**
     * Method: render
     * Creates an AnimationTimer that updates the game window.
     */
    private void render() {
        new AnimationTimer() {
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                // Delta time for physics in Entities
                float delta = (float) ((now - lastTime) / 1e9);
                lastTime = now;
                gameData.setDelta(delta);

                // Animation timer
                double elapsedTime = (now - startTime) / 1_000_000_000.0;
                gameData.setElapsedTime(elapsedTime);
                //System.out.println(elapsedTime);

                update(); // Updates the Entities for the world
                draw(); // Draws the Polygons
                gameData.getKeys().update(); // Updates the keys used by the player

                text1.setText("Destroyed asteroids: " + gameData.getScore().getDestroyedAsteroids());
                text2.setText("Destroyed enemies: " + gameData.getScore().getDestroyedEnemies());
                text3.setText("Health: " + gameData.getPlayerHealth());

                if (gameData.isGameOver()) {
                    stop();
                    gameWindow.getChildren().add(text4);
                }
            }

        }.start();
    }

    /**
     * Method: update
     * Updates the Entities in the world.
     * And retrieves the score from the Score Service REST API.
     */
    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    /**
     * Method: draw
     * Draws the Polygons for the Entities in the world.
     */
    private void draw() {
        for (Entity polygonEntity : polygons.keySet()) {
            if(!world.getEntities().contains(polygonEntity)){
                Polygon removedPolygon = polygons.get(polygonEntity);
                polygons.remove(polygonEntity);
                gameWindow.getChildren().remove(removedPolygon);
            }
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }

            polygon.setFill(entity.getFillColor());
            polygon.setStroke(entity.getStrokeColor());
            polygon.setStrokeWidth(2); // Set border width

            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }
    }

    /**
     * Method: getPluginServices
     * Gets all the Game Plugins using ServiceLoader.
     * @return A collection of all the Game Plugins.
     */
    private Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader
                .load(IGamePluginService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
    }

    /**
     * Method: getEntityProcessingServices
     * Gets all the Entity Processing Services using ServiceLoader.
     * @return A collection of all the Entity Processing Services.
     */
    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    /**
     * Method: getPostEntityProcessingServices
     * Gets all the Post Entity Processing Services using ServiceLoader.
     * @return A collection of all the Post Entity Processing Services.
     */
    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

//    /**
//     * Method: getIScoreProcessorService
//     * Gets all the Score Processor Services using ServiceLoader.
//     * @return A collection of all the Score Processor Services.
//     */
//    private Collection<? extends IScoreProcessorService> getIScoreProcessorService() {
//        return ServiceLoader.load(IScoreProcessorService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
//    }
//
//    /**
//     * Method: getIHealthProcessorService
//     * Gets all the Health Processor Services using ServiceLoader.
//     * @return A collection of all the Health Processor Services.
//     */
//    private Collection<? extends IHealthProcessorService> getIHealthProcessorService() {
//        return ServiceLoader.load(IHealthProcessorService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
//    }
}
