package dk.sdu.mmmi.cbse.common.interfaces;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public interface IMovable {
    void move(Entity entity);
    void rotate(Entity entity);
    void checkBounds(Entity entity, GameData gameData);
}
