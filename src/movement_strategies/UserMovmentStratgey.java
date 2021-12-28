package movement_strategies;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class UserMovmentStratgey implements MovementStrategy {
    private static final float MIN_DISTANCE_FROM_SCREEN_EDGE = 3;
    private UserInputListener inputListener;
    private float windowDimensions_x;

    public UserMovmentStratgey(UserInputListener inputListener, float windowDimensions_x) {

        this.inputListener = inputListener;
        this.windowDimensions_x = windowDimensions_x;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Vector2 calcMovementDir(GameObject paddle) {
        Vector2 movementDir = Vector2.ZERO;

        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);

        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
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
