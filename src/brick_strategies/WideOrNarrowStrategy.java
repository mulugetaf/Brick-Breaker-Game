package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.NarrowWidenPaddle;

import java.util.Random;

public class WideOrNarrowStrategy extends RemoveBrickStrategy {
    private static final int NARROW_PADDLE_WIDTH = 80;
    private static final int NARROW_PADDLE_HEIGHT = 20;
    private static final int WIDE_PADDLE_WIDTH = 200;
    private static final int WIDE_PADDLE_HEIGHT = 20;
    private GameObject buffWidenObj;
    private GameObject buffNarrowObj;
    private GameObjectCollection gameObjects;
    private Renderable narrowPaddle;
    private Renderable widenPaddle;

    public WideOrNarrowStrategy(GameObjectCollection gameObjects, ImageReader imageReader) {
        super(gameObjects);
        this.gameObjects = gameObjects;

        narrowPaddle = imageReader.readImage("assets/buffNarrow.png", true);
        widenPaddle = imageReader.readImage("assets/buffWiden.png", true);

    }

    @Override
    public void onColllision(GameObject thisObj, GameObject otherObj) {
        super.onColllision(thisObj, otherObj);
        buffWidenObj =
                new NarrowWidenPaddle(otherObj.getTopLeftCorner(), new Vector2(WIDE_PADDLE_WIDTH, WIDE_PADDLE_HEIGHT), widenPaddle);
        buffNarrowObj =
                new NarrowWidenPaddle(otherObj.getTopLeftCorner(), new Vector2(NARROW_PADDLE_WIDTH, NARROW_PADDLE_HEIGHT), narrowPaddle);
        buffNarrowObj.setVelocity(new Vector2(0, 35));
        buffWidenObj.setVelocity(new Vector2(0, 35));


        Random rand = new Random();
        if (rand.nextBoolean()) {
            gameObjects.addGameObject(buffNarrowObj);
        } else
            gameObjects.addGameObject(buffWidenObj);

    }

}
