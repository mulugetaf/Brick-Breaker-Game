package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class GameSpeed extends GameObject {
    private WindowController windowController;
    private Vector2 dimensions;
    private float speed;
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
    public GameSpeed(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, WindowController windowController, float speed) {
        super(topLeftCorner, dimensions, renderable);

        this.windowController = windowController;
        this.dimensions = dimensions;
        this.speed = speed;
    }


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
