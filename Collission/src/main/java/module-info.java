import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    uses dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
    uses dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;
    uses dk.sdu.mmmi.cbse.common.services.IHealthProcessorService;
    uses dk.sdu.mmmi.cbse.common.bullet.IOwnable;
    requires Common;
    requires CommonEnemy;
    requires CommonBullet;
    exports dk.sdu.mmmi.cbse.collisionsystem;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;
}