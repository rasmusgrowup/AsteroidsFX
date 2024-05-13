package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
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

public class Main extends Application {
    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private Text text1, text2;
    private long startTime = System.nanoTime();

    private static final List<ModuleLayer> allLayers = new ArrayList<>();

    public static void main(String[] args) {
        Path pluginsPath = Paths.get("plugins");
        ModuleFinder finder = ModuleFinder.of(pluginsPath);
        finder.findAll().stream().map(ModuleReference::descriptor).map(ModuleDescriptor::name).map((plugin) ->
                createLayer(pluginsPath.toString(), plugin)).forEach(allLayers::add);
        launch(Main.class);
    }

    private static ModuleLayer createLayer(String from, String... modules) {
        ModuleFinder finder = ModuleFinder.of(Paths.get(from));
        Set<String> moduleNames = new HashSet<>(Arrays.asList(modules));
        Configuration cf = ModuleLayer.boot().configuration().resolve(finder, ModuleFinder.of(), moduleNames);
        ModuleLayer parent = ModuleLayer.boot();
        return parent.defineModulesWithOneLoader(cf, ClassLoader.getSystemClassLoader());
    }

    @Override
    public void start(Stage window) throws Exception {
        text1 = new Text(10, 20, "Destroyed asteroids: 0" + gameData.getDestroyedAsteroids());
        text2 = new Text(10, 50, "Destroyed enemies: 0" + gameData.getDestroyedEnemies());
        text1.setFill(Color.RED);
        text2.setFill(Color.RED);
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(text1);
        gameWindow.getChildren().add(text2);
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

    private Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        List<IPostEntityProcessingService> allIPostEntityProcessingServices = new ArrayList<>();

        ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).forEach(allIPostEntityProcessingServices::add);

        for (ModuleLayer layer : allLayers) {
            allIPostEntityProcessingServices.addAll(ServiceLoader.load(layer, IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList()));
        }

        return allIPostEntityProcessingServices;
    }
}
