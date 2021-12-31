package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.NarrowWidenPaddle;

import java.util.Random;

/**
 * Add wide and narrow object.
 */
public class WideOrNarrowStrategy extends RemoveBrickStrategy {
    private static final int NARROW_PADDLE_WIDTH = 80;
    private static final int NARROW_PADDLE_HEIGHT = 20;
    private static final int WIDE_PADDLE_WIDTH = 200;
    private static final int WIDE_PADDLE_HEIGHT = 20;
    private final GameObjectCollection gameObjects;
    private final Renderable narrowPaddle;
    private final Renderable widenPaddle;


    public WideOrNarrowStrategy(GameObjectCollection gameObjects, ImageReader imageReader) {
        super(gameObjects);

        this.gameObjects = gameObjects;

        narrowPaddle = imageReader.readImage("assets/buffNarrow.png", true);
        widenPaddle = imageReader.readImage("assets/buffWiden.png", true);

    }

    /**
     * @param thisObj        The current collision object.
     * @param otherObj       The GameObject with which a collision occurred.
     * @param numberOfBricks Number of bricks left in the game.
     */
    @Override
    public void onColllision(GameObject thisObj, GameObject otherObj, Counter numberOfBricks) {
        super.onColllision(thisObj, otherObj, numberOfBricks);

        GameObject buffWidenObj = new NarrowWidenPaddle(otherObj.getTopLeftCorner(),
                new Vector2(WIDE_PADDLE_WIDTH, WIDE_PADDLE_HEIGHT), widenPaddle);

        GameObject buffNarrowObj = new NarrowWidenPaddle(otherObj.getTopLeftCorner(),
                new Vector2(NARROW_PADDLE_WIDTH, NARROW_PADDLE_HEIGHT), narrowPaddle);

        buffNarrowObj.setVelocity(new Vector2(0, 35));
        buffWidenObj.setVelocity(new Vector2(0, 35));

        Random rand = new Random();
        if (rand.nextBoolean()) {
            gameObjects.addGameObject(buffNarrowObj);
        } else
            gameObjects.addGameObject(buffWidenObj);
    }

}
