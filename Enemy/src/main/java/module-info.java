import dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.enemysystem.EnemyProcessor;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Enemy {
    requires CommonEnemy;
    requires Common;
    requires javafx.graphics;
    requires CommonBullet;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    provides IGamePluginService with dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;
    provides IEntityProcessingService with EnemyProcessor;
    provides EnemySPI with dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;
}