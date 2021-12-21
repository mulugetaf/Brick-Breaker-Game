package movement_strategies;

import danogl.GameObject;
import danogl.util.Vector2;


/**
 * An interface for defining the movement of the paddle.
 */
public interface MovementStrategy {
    /**
     * The main method of the strategy, used by the paddle to determine direction.
     * @param paddle The paddle that owns the strategy.
     * @return A movement direction, of type Vector2, whose y coordinate is zero,
     * and x coordinate is -1,1, or zero.
     */
    Vector2 calcMovementDir(GameObject paddle);

}
