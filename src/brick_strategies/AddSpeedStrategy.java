package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.GameSpeed;

import java.util.Random;

public class AddSpeedStrategy extends RemoveBrickStrategy {
    private final int IMG_WIDTH = 35;
    private final int IMG_HEIGHT = 35;
    private final float slowSpeed = 0.9f;
    private final float fastSpeed = 1.1f;
    private final Renderable quickenImg;
    private final Renderable slowImg;
    private GameObject quickenObj;
    private GameObject slowObj;
    private WindowController windowController;
    private GameObjectCollection gameObjects;


    public AddSpeedStrategy(GameObjectCollection gameObjects, WindowController windowController,
                            ImageReader imageReader) {
        super(gameObjects);
        quickenImg = imageReader.readImage("assets/quicken.png", true);
        slowImg = imageReader.readImage("assets/slow.png", true);
        this.windowController = windowController;
        this.gameObjects = gameObjects;
    }

    @Override
    public void onColllision(GameObject thisObj, GameObject otherObj) {
        super.onColllision(thisObj, otherObj);
        Random random = new Random();
        quickenObj = new GameSpeed(otherObj.getTopLeftCorner(), new Vector2(IMG_WIDTH, IMG_HEIGHT), quickenImg,
                windowController, fastSpeed);
        slowObj = new GameSpeed(otherObj.getTopLeftCorner(), new Vector2(IMG_WIDTH, IMG_HEIGHT), slowImg,
                windowController, slowSpeed);
        quickenObj.setVelocity(new Vector2(0, 35));
        slowObj.setVelocity(new Vector2(0, 35));
        if (random.nextBoolean())
            gameObjects.addGameObject(quickenObj);
        else
            gameObjects.addGameObject(slowObj);
    }
}
