package gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class UserPaddle extends GameObject {

    private static final float MOVEMENT_SPEED = 400;
    private final double MIN_DISTANCE_FROM_SCREEN_EDGE = 3;
    private UserInputListener inputListener;
    private double windowDimensions_x;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener The inputListener representing the key pressed
     */
    public UserPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, float windowDimensions_x) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions_x = windowDimensions_x;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);

        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }

        //check if paddle outbound top left corner
        if (getTopLeftCorner().x() < MIN_DISTANCE_FROM_SCREEN_EDGE) {
            setTopLeftCorner(new Vector2((float) MIN_DISTANCE_FROM_SCREEN_EDGE, getTopLeftCorner().y()));
        }
        ////check if paddle outbound top right corner
        if (getTopLeftCorner().x() > windowDimensions_x - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x()) {
            float x_dim = (float) (windowDimensions_x - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x());
            setTopLeftCorner(new Vector2(x_dim, getTopLeftCorner().y()));
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}
