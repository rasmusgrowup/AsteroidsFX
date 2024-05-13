import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidProcessor;
import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.splitpackages.SplitPackage;

module Asteroid {
    requires Common;
    requires CommonAsteroid;
    requires javafx.graphics;
    provides IGamePluginService with dk.sdu.mmmi.cbse.asteroidsystem.AsteroidPlugin;
    provides IEntityProcessingService with AsteroidProcessor;
    provides IPostEntityProcessingService with SplitPackage;
}