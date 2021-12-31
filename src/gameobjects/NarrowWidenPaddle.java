package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Construct a new GameObject instance.
 * The narrow object will make the paddle smaller when collision on it.
 * The widen object will make the paddle wide when collision on it.
 */
public class NarrowWidenPaddle extends GameObject {
    private final Vector2 dimensions;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public NarrowWidenPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.dimensions = dimensions;
    }

    /**
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (shouldCollideWith(other))
            other.setDimensions(dimensions);
    }

    /**
     * check if this object be allowed to collide the specified other object.
     *
     * @param other The other GameObject.
     * @return true if the objects should collide.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        //super.shouldCollideWith(other);
        return other instanceof Paddle;

    }
}
