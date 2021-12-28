package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.MockPaddle;
import movement_strategies.UserMovmentStratgey;

public class AddNewPaddleStrategy extends RemoveBrickStrategy {
    private final Renderable paddleImage;
    private final UserInputListener inputListener;
    private final Vector2 windowsDimension;
    private final GameObjectCollection gameObjects;
    private final float PADDLE_WIDTH = 150;
    private final float PADDLE_HEIGHT = 20;
    private GameObject paddle;


    public AddNewPaddleStrategy(GameObjectCollection gameObjects, ImageReader imageReader,
                                UserInputListener inputListener, Vector2 windowsDimension) {
        super(gameObjects);
        this.gameObjects = gameObjects;
        this.paddleImage = imageReader.readImage("assets/paddle.png", true);
        this.inputListener = inputListener;
        this.windowsDimension = windowsDimension;
        paddle = new MockPaddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                new UserMovmentStratgey(inputListener, windowsDimension.x()),
                gameObjects);
        paddle.setCenter(new Vector2(windowsDimension.x() / 2, windowsDimension.y() - 90));
    }

    @Override
    public void onColllision(GameObject thisObj, GameObject otherObj) {
        super.onColllision(thisObj, otherObj);
        // user paddle (Bottom)
        gameObjects.addGameObject(paddle);

    }
}
