package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.ObjectSpeed;

import java.util.Random;

/**
 * Add speed to the game when one of the objects collides with a paddle.
 */
public class AddSpeedStrategy extends RemoveBrickStrategy {
    private final int IMG_WIDTH = 35;
    private final int IMG_HEIGHT = 35;
    private final float slowSpeed = 0.9f;
    private final float fastSpeed = 1.1f;
    private final Renderable quickenImg;
    private final Renderable slowImg;
    private final WindowController windowController;
    private final GameObjectCollection gameObjects;


    /**
     * @param gameObjects      A container for accumulating/removing instances of GameObject and
     *                         for handling their collisions.
     * @param windowController Provides control and info regarding the game.
     * @param imageReader      Used to read images from disk or from within a jar.
     */
    public AddSpeedStrategy(GameObjectCollection gameObjects, WindowController windowController,
                            ImageReader imageReader) {
        super(gameObjects);

        quickenImg = imageReader.readImage("assets/quicken.png", true);
        slowImg = imageReader.readImage("assets/slow.png", true);
        this.windowController = windowController;
        this.gameObjects = gameObjects;
    }

    /**
     * @param thisObj        The current collision object.
     * @param otherObj       The GameObject with which a collision occurred.
     * @param numberOfBricks Number of bricks left in the game.
     */
    @Override
    public void onColllision(GameObject thisObj, GameObject otherObj, Counter numberOfBricks) {
        super.onColllision(thisObj, otherObj, numberOfBricks);
        Random random = new Random();
        GameObject quickenObj = new ObjectSpeed(otherObj.getTopLeftCorner(), new Vector2(IMG_WIDTH, IMG_HEIGHT),
                quickenImg, windowController, fastSpeed);
        GameObject slowObj = new ObjectSpeed(otherObj.getTopLeftCorner(), new Vector2(IMG_WIDTH, IMG_HEIGHT),
                slowImg, windowController, slowSpeed);
        quickenObj.setVelocity(new Vector2(0, 35));
        slowObj.setVelocity(new Vector2(0, 35));
        if (random.nextBoolean())
            gameObjects.addGameObject(quickenObj);
        else
            gameObjects.addGameObject(slowObj);
    }
}
