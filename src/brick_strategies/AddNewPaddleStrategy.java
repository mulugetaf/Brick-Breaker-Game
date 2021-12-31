package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.MockPaddle;
import movement_strategies.UserMovmentStratgey;

/**
 * Add extra paddle to the game.
 * The object will appear in the center of the screen until another object collides with it.
 */
public class AddNewPaddleStrategy extends RemoveBrickStrategy {
    private final GameObjectCollection gameObjects;
    private final float PADDLE_WIDTH = 150;
    private final float PADDLE_HEIGHT = 20;
    private final GameObject paddle;

    /**
     * @param gameObjects      A container for accumulating/removing instances of GameObject
     *                         and for handling their collisions.
     * @param imageReader      Used to read images from disk or from within a jar.
     * @param inputListener    An interface for reading user input in the current frame.
     * @param windowsDimension dimensions in pixels. can be null to indicate a
     */
    public AddNewPaddleStrategy(GameObjectCollection gameObjects, ImageReader imageReader,
                                UserInputListener inputListener, Vector2 windowsDimension) {
        super(gameObjects);

        this.gameObjects = gameObjects;
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        paddle = new MockPaddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                new UserMovmentStratgey(inputListener, windowsDimension.x()),
                gameObjects);
        paddle.setCenter(new Vector2(windowsDimension.x() / 2, windowsDimension.y() - 90));
    }

    /**
     * defining the Collision strategy of the brick
     *
     * @param thisObj        The current collision object.
     * @param otherObj       The GameObject with which a collision occurred.
     * @param numberOfBricks Number of bricks left in the game.
     */
    @Override
    public void onColllision(GameObject thisObj, GameObject otherObj, Counter numberOfBricks) {
        super.onColllision(thisObj, otherObj, numberOfBricks);
        gameObjects.addGameObject(paddle);
    }
}
