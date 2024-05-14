package dk.sdu.mmmi.cbse.common.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 * @author rasan22@student.sdu.dk
 * Class: EnemyProcessor
 * Implements: IEntityProcessingService
 * Provided Interfaces: IEntityProcessingService
 * Required Interfaces: BulletSPI
 */
public class Enemy extends Entity {
    private double initialRotation;
    private boolean canShoot;

    /**
     * Method: setInitialRotation
     * Sets the initial rotation of the enemy.
     * @param initialRotation - The initial rotation of the enemy.
     */
    public void setInitialRotation(double initialRotation) {
        this.initialRotation = initialRotation;
    }

    /**
     * Method: getInitialRotation
     * Returns the initial rotation of the enemy.
     * @return initialRotation - The initial rotation of the enemy.
     */
    public double getInitialRotation() {
        return initialRotation;
    }

    /**
     * Method: isCanShoot
     * Returns whether the enemy can shoot.
     * @return canShoot - Whether the enemy can shoot.
     */
    public boolean isCanShoot() {
        return canShoot;
    }

    /**
     * Method: setCanShoot
     * Sets whether the enemy can shoot.
     * @param canShoot - Whether the enemy can shoot.
     */
    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }
}
