module Core {
    uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;
    uses dk.sdu.mmmi.cbse.common.services.IHealthProcessorService;
    requires javafx.graphics;
    requires Common;
    requires ScoreProcessor;
    requires HealthProcessor;

    opens dk.sdu.mmmi.cbse.main to javafx.graphics;
    exports dk.sdu.mmmi.cbse.main;
}

