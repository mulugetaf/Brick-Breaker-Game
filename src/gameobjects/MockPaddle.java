package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import movement_strategies.MovementStrategy;

/**
 *
 */
public class MockPaddle extends Paddle {
    private final GameObjectCollection gameObjects;
    public int numOfCollisions;

    /**
     * Construct a new paddle instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     * @param movementStrategy A movement direction, of type Vector2.
     */
    public MockPaddle(Vector2 topLeftCorner, Vector2 dimensions,
                      Renderable renderable, MovementStrategy movementStrategy,
                      GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable, movementStrategy);
        this.numOfCollisions = 3;
        this.gameObjects = gameObjects;
    }

    /**
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.numOfCollisions--;
        if (this.numOfCollisions <= 0)
            gameObjects.removeGameObject(this);
    }
}
