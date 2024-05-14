module Core {
    uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IScoreProcessorService;
    requires javafx.graphics;
    requires Common;
    requires spring.web;
    requires com.fasterxml.jackson.databind;
    requires spring.context;
    requires spring.beans;
    requires spring.core;

    opens dk.sdu.mmmi.cbse.main to javafx.graphics, spring.core, com.fasterxml.jackson.databind, spring.beans, spring.context;
    exports dk.sdu.mmmi.cbse.main;
}

