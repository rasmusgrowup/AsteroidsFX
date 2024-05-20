import dk.sdu.mmmi.cbse.common.services.IHealthProcessorService;
import dk.sdu.mmmi.cbse.healthprocessor.HealthProcessor;

module HealthProcessor {
    exports dk.sdu.mmmi.cbse.healthprocessor;
    requires Common;
    provides IHealthProcessorService with HealthProcessor;
}