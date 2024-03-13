import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    uses dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
    requires Common;
    requires CommonAsteroid;
    requires CommonBullet;
    requires CommonEnemy;
    exports dk.sdu.mmmi.cbse.collisionsystem;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;
}