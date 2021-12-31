package brick_strategies;

import danogl.GameObject;
import danogl.util.Counter;

/**
 * Add functionality to create brick with multiple strategies.
 */
public class MultipleBehaviorStrategy implements CollisionStrategy {
    private final CollisionStrategy firstBehavior;
    private final CollisionStrategy secondBehavior;

    /**
     * @param firstBehavior  First collision strategy.
     * @param secondBehavior Second collision strategy.
     */
    public MultipleBehaviorStrategy(CollisionStrategy firstBehavior, CollisionStrategy secondBehavior) {

        this.firstBehavior = firstBehavior;
        this.secondBehavior = secondBehavior;
    }

    /**
     * @param thisObj        The current collision object.
     * @param otherObj       The GameObject with which a collision occurred.
     * @param numberOfBricks Number of bricks left in the game.
     */
    @Override
    public void onColllision(GameObject thisObj, GameObject otherObj, Counter numberOfBricks) {
        this.firstBehavior.onColllision(thisObj, otherObj, numberOfBricks);
        this.secondBehavior.onColllision(thisObj, otherObj, numberOfBricks);
        numberOfBricks.increaseBy(1);
    }
}
