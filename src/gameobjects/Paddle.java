package gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import movement_strategies.MovementStrategy;

/**
 * Represents paddle in the game
 */
public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 400;
    private final MovementStrategy movementStrategy;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     * @param movementStrategy A movement direction, of type Vector2.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  MovementStrategy movementStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.movementStrategy = movementStrategy;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = movementStrategy.calcMovementDir(this);
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}
