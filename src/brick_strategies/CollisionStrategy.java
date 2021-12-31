package brick_strategies;

import danogl.GameObject;
import danogl.util.Counter;

/**
 * An interface for defining the Collision strategy of the brick.
 */
public interface CollisionStrategy {
    /**
     * @param thisObj  The current collision object.
     * @param otherObj The GameObject with which a collision occurred.
     * @param numberOfBricks Number of bricks left in the game.
     */
    void onColllision(GameObject thisObj, GameObject otherObj, Counter numberOfBricks);

}
