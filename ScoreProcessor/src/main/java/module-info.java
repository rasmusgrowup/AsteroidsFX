import dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;

module ScoreProcessor {
    exports dk.sdu.mmmi.cbse.scoreprocessor;
    requires Common;
    requires CommonAsteroid;
    requires CommonBullet;
    requires CommonEnemy;
    requires Player;
    requires CommonPlayer;
    provides IScoreProcessorService with dk.sdu.mmmi.cbse.scoreprocessor.ScoreProcessor;
}