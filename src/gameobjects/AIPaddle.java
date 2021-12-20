package gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class AIPaddle extends GameObject {
    private static final float PADDLE_SPEED = 400;
    private static final float MIN_DISTANCE_FROM_SCREEN_EDGE = 3;
    private GameObject objectToFollow;
    private float windowDimensions_x;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public AIPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                    GameObject objectToFollow,float windowDimensions_x) {
        super(topLeftCorner, dimensions, renderable);
        this.objectToFollow = objectToFollow;
        this.windowDimensions_x = windowDimensions_x;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(objectToFollow.getCenter().x()<getCenter().x())
            movementDir = Vector2.LEFT;
        if(objectToFollow.getCenter().x()>getCenter().x())
            movementDir = Vector2.RIGHT;
        //check if paddle outbound top left corner
        if (getTopLeftCorner().x() < MIN_DISTANCE_FROM_SCREEN_EDGE) {
            setTopLeftCorner(new Vector2((float) MIN_DISTANCE_FROM_SCREEN_EDGE, getTopLeftCorner().y()));
        }
        ////check if paddle outbound top right corner
        if (getTopLeftCorner().x() > windowDimensions_x - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x()) {
            float x_dim = (float) (windowDimensions_x - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x());
            setTopLeftCorner(new Vector2(x_dim, getTopLeftCorner().y()));
        }
        setVelocity(movementDir.mult(PADDLE_SPEED));
        setVelocity(movementDir.mult(PADDLE_SPEED));
    }
}
