package dk.sdu.mmmi.cbse.splitpackages;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class SplitPackage implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        System.out.println("SplitPackage from asteroid module");
    }
}
