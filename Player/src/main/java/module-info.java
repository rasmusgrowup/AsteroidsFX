
import dk.sdu.mmmi.cbse.common.interfaces.IAccelerate;
import dk.sdu.mmmi.cbse.common.interfaces.IDamageable;
import dk.sdu.mmmi.cbse.common.interfaces.IMovable;
import dk.sdu.mmmi.cbse.common.player.PlayerSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Player {
    exports dk.sdu.mmmi.cbse.playersystem;
    requires Common;
    requires CommonBullet;
    requires javafx.graphics;
    requires CommonPlayer;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    provides IAccelerate with dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
    provides IMovable with dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
    provides IDamageable with dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
    provides IGamePluginService with dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
    provides PlayerSPI with dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
}
