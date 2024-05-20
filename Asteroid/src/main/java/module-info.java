import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidProcessor;
import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidPlugin;
import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.interfaces.IDamageable;
import dk.sdu.mmmi.cbse.common.interfaces.IMovable;
import dk.sdu.mmmi.cbse.common.interfaces.ISplittable;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Asteroid {
    requires Common;
    requires CommonAsteroid;
    requires javafx.graphics;
    provides IGamePluginService with AsteroidPlugin;
    provides AsteroidSPI with AsteroidPlugin;
    provides IEntityProcessingService with AsteroidProcessor;
    provides ISplittable with AsteroidProcessor;
    provides IMovable with AsteroidProcessor;
    provides IDamageable with AsteroidProcessor;
}