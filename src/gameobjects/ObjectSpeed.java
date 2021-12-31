package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents objects of game speed
 */
public class ObjectSpeed extends GameObject {
    private final WindowController windowController;
    private final float speed;
    private Vector2 dimensions;
    private boolean addQuickSpeed = true;
    private boolean addSlowSpeed = true;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public ObjectSpeed(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, WindowController windowController, float speed) {
        super(topLeftCorner, dimensions, renderable);

        this.windowController = windowController;
        this.dimensions = dimensions;
        this.speed = speed;
    }

    /**
     * Called on the first frame of a collision.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (shouldCollideWith(other)) {
            if (speed == 0.9f && addSlowSpeed) {
                windowController.setTimeScale(speed);
                addSlowSpeed = false;
                addQuickSpeed = true;
            } else if (speed == 1.1f && addQuickSpeed) {
                windowController.setTimeScale(speed);
                addQuickSpeed = false;
                addSlowSpeed = true;
            }
        }

    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof Paddle;
    }
}
