package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.Ball;

/**
 * Add extra ball to the game.
 */
public class AddBallStrategy extends RemoveBrickStrategy {
    private final Renderable ballImage;
    private final Sound collisionSound;
    private final GameObjectCollection gameObjects;

    /**
     * @param gameObjects A container for accumulating/removing instances of GameObject and
     *                    for handling their collisions.
     * @param imageReader Used to read images from disk or from within a jar.
     * @param soundReader Class for reading Sounds from the disk or from within a jar.
     */
    public AddBallStrategy(GameObjectCollection gameObjects, ImageReader imageReader, SoundReader soundReader) {
        super(gameObjects);

        this.gameObjects = gameObjects;
        this.ballImage = imageReader.readImage("assets/mockBall.png", true);
        this.collisionSound = soundReader.readSound("assets/Bubble5_4.wav");
    }

    /**
     * @param thisObj         The current collision object.
     * @param otherObj       The GameObject with which a collision occurred.
     * @param numberOfBricks Number of bricks left in the game.
     */
    @Override
    public void onColllision(GameObject thisObj, GameObject otherObj, Counter numberOfBricks) {
        super.onColllision(thisObj,otherObj,numberOfBricks);
        GameObject ball =
                new Ball(thisObj.getTopLeftCorner(), otherObj.getDimensions(), ballImage, collisionSound);
        ball.setVelocity(new Vector2(otherObj.getTopLeftCorner().x(), otherObj.getTopLeftCorner().y()));
        gameObjects.addGameObject(ball);
        //destroy brick after  ball created
        gameObjects.removeGameObject(otherObj, Layer.STATIC_OBJECTS);

    }
}
