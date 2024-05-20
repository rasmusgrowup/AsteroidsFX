package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.interfaces.IOwnable;

/**
 * @author rasan22@student.sdu.dk
 * Class: Bullet
 * Extends: Entity
 * Provides: OwnerSPI
 */
public class Bullet extends Entity implements IOwnable {
    private Entity owner;

    /**
     * Constructor: Bullet
     * Initializes the owner of the bullet.
     * @param owner - The owner of the bullet.
     */
    public Bullet(Entity owner) {
        this.owner = owner;
    }
    /**
     * Method: getOwner
     * Returns the owner of the bullet.
     * @return owner - The owner of the bullet.
     */
    public Entity getOwner() {
        return owner;
    }

    /**
     * Method: setOwner
     * Sets the owner of the bullet.
     * @param owner - The owner of the bullet.
     */
    public void setOwner(Entity owner) {
        this.owner = owner;
    }
}
