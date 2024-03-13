module Core {
    uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
    requires javafx.graphics;
    requires Common;

    opens dk.sdu.mmmi.cbse.main to javafx.graphics;
    exports dk.sdu.mmmi.cbse.main;
}


