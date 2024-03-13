package dk.sdu.mmmi.cbse.common.data;

public class GameData {

    private int displayWidth  = 800 ;
    private int displayHeight = 800;
    private int destroyedAsteroids = 0;
    private int destroyedEnemies = 0;
    private float delta; // Time difference between frames
    private double elapsedTime;
    private final GameKeys keys = new GameKeys();


    public GameKeys getKeys() {
        return keys;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public double getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getDestroyedAsteroids() {
        return destroyedAsteroids;
    }

    public void setDestroyedAsteroids(int destroyedAsteroids) {
        this.destroyedAsteroids = destroyedAsteroids;
    }

    public int getDestroyedEnemies() {
        return destroyedEnemies;
    }

    public void setDestroyedEnemies(int destroyedEnemies) {
        this.destroyedEnemies = destroyedEnemies;
    }

    public void incDestroyedEnemies() {
        this.destroyedEnemies++;
    }

    public void incDestroyedAsteroid() {
        this.destroyedAsteroids++;
    }

    public void decDestroyedEnemies() {
        this.destroyedEnemies--;
    }

    public void decDestroyedAsteroid() {
        this.destroyedEnemies--;
    }
}
