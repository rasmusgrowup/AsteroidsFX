package dk.sdu.mmmi.cbse.common.data;

public class Score {
    private static Score instance;
    private int destroyedAsteroids;
    private int destroyedEnemies;

    private Score() {
        this.destroyedAsteroids = 0;
        this.destroyedEnemies = 0;
    }

    public static Score getInstance() {
        if (instance == null) {
            instance = new Score();
        }
        return instance;
    }

    public static void setInstance(Score instance) {
        Score.instance = instance;
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

    public void incrementDestroyedAsteroids() {
        this.destroyedAsteroids++;
    }

    public void incrementDestroyedEnemies() {
        this.destroyedEnemies++;
    }

    public void clearScore() {
        this.destroyedAsteroids = 0;
        this.destroyedEnemies = 0;
    }
}
