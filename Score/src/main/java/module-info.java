import dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;

module Score {
    requires Common;
    requires CommonAsteroid;
    requires CommonBullet;
    requires CommonEnemy;
    requires Player;
    requires spring.web;
    exports dk.sdu.mmmi.cbse.score;
    provides IScoreProcessorService with dk.sdu.mmmi.cbse.score.ScoreProcessor;
}