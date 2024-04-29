module Core {
    uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
    requires javafx.graphics;
    requires Common;
    requires spring.context;
    requires spring.beans;
    requires spring.core;

    opens dk.sdu.mmmi.cbse.main to javafx.graphics, spring.core;
    exports dk.sdu.mmmi.cbse.main;
}


