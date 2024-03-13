package dk.sdu.mmmi.cbse.common.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Enemy extends Entity {
    private double initialRotation;
    private boolean canShoot;

    public void setInitialRotation(double initialRotation) {
        this.initialRotation = initialRotation;
    }

    public double getInitialRotation() {
        return initialRotation;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }
}
