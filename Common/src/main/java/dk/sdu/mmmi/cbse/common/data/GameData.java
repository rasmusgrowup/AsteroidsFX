package dk.sdu.mmmi.cbse.common.data;

/**
 * The GameData class defines the window size,
 * the player health, the game over state,
 * the time difference between frames
 * and the elapsed time.
 * It also holds the keys pressed by the player.
 */
public class GameData {
    private int displayWidth  = 800 ;
    private int displayHeight = 800;
    private int playerHealth;
    private boolean gameOver;
    private float delta;
    private double elapsedTime;
    private final GameKeys keys = new GameKeys();
    private Score score = Score.getInstance();

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

    public int getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }

    public void decPlayerHealth() {
        this.playerHealth--;
    }

    public void incPlayerHealth() {
        this.playerHealth++;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameover(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Score getScore() {
        return score;
    }
}
