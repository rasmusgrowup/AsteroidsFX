import dk.sdu.mmmi.cbse.common.interfaces.IOwnable;
import dk.sdu.mmmi.cbse.common.services.ICollisionProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;
import dk.sdu.mmmi.cbse.common.services.IHealthProcessorService;

module Collision {
    uses IScoreProcessorService;
    uses IHealthProcessorService;
    uses IOwnable;
    requires Common;
    exports dk.sdu.mmmi.cbse.collisionsystem;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;
    provides ICollisionProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;
}