import dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;

module ScoreProcessor {
    requires Common;
    requires CommonAsteroid;
    requires CommonBullet;
    requires CommonEnemy;
    requires Player;
    requires spring.context;
    provides IScoreProcessorService with dk.sdu.mmmi.cbse.scoreprocessor.ScoreProcessor;
}