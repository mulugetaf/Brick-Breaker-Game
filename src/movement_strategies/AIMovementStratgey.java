package movement_strategies;

import danogl.GameObject;
import danogl.util.Vector2;

public class AIMovementStratgey implements MovementStrategy {
    private static final float MIN_DISTANCE_FROM_SCREEN_EDGE = 3;
    private GameObject objectToFollow;
    private float windowDimensions_x;

    public AIMovementStratgey(GameObject objectToFollow, float windowDimensions_x) {
        this.objectToFollow = objectToFollow;
        this.windowDimensions_x = windowDimensions_x;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Vector2 calcMovementDir(GameObject paddle) {
        Vector2 movementDir = Vector2.ZERO;
        float epsilon = 20;
        if (objectToFollow.getCenter().x() < paddle.getCenter().x() + epsilon
                && objectToFollow.getCenter().x() < paddle.getCenter().x() - epsilon)
            movementDir = Vector2.LEFT;
        if (objectToFollow.getCenter().x() + epsilon > paddle.getCenter().x()
                && objectToFollow.getCenter().x() - epsilon > paddle.getCenter().x())
            movementDir = Vector2.RIGHT;
        //check if paddle outbound top left corner
        if (paddle.getTopLeftCorner().x() < MIN_DISTANCE_FROM_SCREEN_EDGE) {
            paddle.setTopLeftCorner(new Vector2(MIN_DISTANCE_FROM_SCREEN_EDGE, paddle.getTopLeftCorner().y()));
        }
        //check if paddle outbound top right corner
        if (paddle.getTopLeftCorner().x() > windowDimensions_x - MIN_DISTANCE_FROM_SCREEN_EDGE - paddle.getDimensions().x()) {
            float x_dim = (windowDimensions_x - MIN_DISTANCE_FROM_SCREEN_EDGE - paddle.getDimensions().x());
            paddle.setTopLeftCorner(new Vector2(x_dim, paddle.getTopLeftCorner().y()));
        }

        return movementDir;
    }
}
