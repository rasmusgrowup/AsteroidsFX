package dk.sdu.mmmi.cbse.common.interfaces;

import dk.sdu.mmmi.cbse.common.data.Entity;

public interface IOwnable {
    Entity getOwner();
    void setOwner(Entity owner);
}
