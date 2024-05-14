package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;
import dk.sdu.mmmi.cbse.playersystem.Player;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        Collection<Entity> entities = world.getEntities();

        for (Entity entity1 : entities) {
            for (Entity entity2 : entities) {
                if (entity1.equals(entity2)) {
                    continue;
                }

                if (entity1 instanceof Asteroid && entity2 instanceof Asteroid) {
                    continue;
                }

                if (entity1 instanceof Bullet && entity2.equals(((Bullet) entity1).getOwner())) {
                    continue;
                } else if (entity2 instanceof Bullet && entity1.equals(((Bullet) entity2).getOwner())) {
                    continue;
                }

                if (collides(entity1, entity2)) {
                    for (IScoreProcessorService scoreProcessorService : getIScoreProcessorService()) {
                        scoreProcessorService.processScore(gameData, world, entity1, entity2);
                    }
                }
            }
        }
    }

    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    private Collection<? extends IScoreProcessorService> getIScoreProcessorService() {
        return ServiceLoader.load(IScoreProcessorService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}