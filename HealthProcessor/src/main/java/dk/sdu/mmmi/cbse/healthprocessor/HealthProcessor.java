package dk.sdu.mmmi.cbse.healthprocessor;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.services.IHealthProcessorService;


public class HealthProcessor implements IHealthProcessorService {
    @Override
    public void process(Entity entity1, Entity entity2) {
        System.out.println("Processing health");
        entity1.setHealth(entity1.getHealth() - entity2.getDamage());
        entity2.setHealth(entity2.getHealth() - entity1.getDamage());
    }
}
