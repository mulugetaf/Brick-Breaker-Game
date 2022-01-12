package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * Remove Brick when ball collides with it.
 */
public class RemoveBrickStrategy implements CollisionStrategy {
    private final GameObjectCollection gameObjects;

    /**
     * Constructor to creat GameObjectCollection.
     *
     * @param gameObjects A container for accumulating/removing instances of GameObject and
     *                    for handling their collisions.
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjects) {

        this.gameObjects = gameObjects;
    }


    /**
     * defining the Collision strategy of the brick
     *
     * @param thisObj        The current collision object.
     * @param otherObj       The GameObject with which a collision occurred.
     * @param numberOfBricks Number of bricks left in the game.
     */
    @Override
    public void onColllision(GameObject thisObj, GameObject otherObj, Counter numberOfBricks) {
        gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        numberOfBricks.decrement();
    }
}
