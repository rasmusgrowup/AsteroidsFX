package dk.sdu.mmmi.cbse.common.interfaces;

import dk.sdu.mmmi.cbse.common.data.Entity;

public interface IAccelerate {
    void accelerate(Entity entity, float deltaTime);
    void decelerate(Entity entity, double decelerationRate, float deltaTime);
}