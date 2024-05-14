package dk.sdu.mmmi.cbse.main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

public class Game {
    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private Text text1, text2, text3, text4;
    private long startTime = System.nanoTime();
    private final RestTemplate restTemplate = new RestTemplate();
    private int destroyedAsteroids;
    private int destroyedEnemies;
    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServices;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;

    Game(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServices, List<IPostEntityProcessingService> postEntityProcessingServices) {
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServices = entityProcessingServices;
        this.postEntityProcessingServices = postEntityProcessingServices;
    }

    /**
     * Method: start
     * Initializes the game window, the game data, and the world.
     * Starts the game loop and updates the game window.
     * @param window - The JavaFX stage object representing the game window.
     */
    public void start(Stage window) throws Exception {
        text1 = new Text(10, 20, "Destroyed asteroids: " + destroyedAsteroids);
        text2 = new Text(10, 50, "Destroyed enemies: " + destroyedEnemies);
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

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }

        render();

        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();
        window.setResizable(false);
    }

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

                text1.setText("Destroyed asteroids: " + gameData.getDestroyedAsteroids());
                text2.setText("Destroyed enemies: " + gameData.getDestroyedEnemies());
                text3.setText("Health: " + gameData.getPlayerHealth());

                if (gameData.isGameOver()) {
                    stop();
                    gameWindow.getChildren().add(text4);
                }
            }

        }.start();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }

        getScore();
    }

    private void getScore() {
        String response = restTemplate.getForObject("http://localhost:8080/score", String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response);
            destroyedAsteroids = root.path("destroyedAsteroids").asInt();
            destroyedEnemies = root.path("destroyedEnemies").asInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
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

    /**
     * Method: getIScoreProcessorService
     * Gets all the Score Processor Services using ServiceLoader.
     * @return A collection of all the Score Processor Services.
     */
    private Collection<? extends IScoreProcessorService> getIScoreProcessorService() {
        return ServiceLoader.load(IScoreProcessorService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}