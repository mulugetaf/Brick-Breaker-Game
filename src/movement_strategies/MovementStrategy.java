package movement_strategies;

import danogl.GameObject;
import danogl.util.Vector2;
import gameobjects.Paddle;

public interface MovementStrategy {
    Vector2 calcMovementDir(GameObject paddle);

}
