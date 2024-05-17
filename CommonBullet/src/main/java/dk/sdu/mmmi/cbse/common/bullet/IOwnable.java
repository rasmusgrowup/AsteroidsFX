package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;

public interface IOwnable {
    Entity getOwner();
    void setOwner(Entity owner);
}
