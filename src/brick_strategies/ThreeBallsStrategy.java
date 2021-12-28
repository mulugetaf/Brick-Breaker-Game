package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.Ball;

public class ThreeBallsStrategy extends RemoveBrickStrategy {
    private static final float NUM_OF_BALLS = 1;
    private final Renderable ballImage;
    private final Sound collisionSound;
    private GameObjectCollection gameObjects;

    public ThreeBallsStrategy(GameObjectCollection gameObjects, ImageReader imageReader, SoundReader soundReader) {
        super(gameObjects);

        this.gameObjects = gameObjects;
        this.ballImage = imageReader.readImage("assets/mockBall.png", true);
        this.collisionSound = soundReader.readSound("assets/Bubble5_4.wav");
    }

    @Override
    public void onColllision(GameObject thisObj, GameObject otherObj) {
//        gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        for (int i = 0; i < NUM_OF_BALLS; i++) {
            GameObject ball =
                    new Ball(thisObj.getTopLeftCorner(), otherObj.getDimensions(), ballImage, collisionSound);
            ball.setVelocity(new Vector2(otherObj.getTopLeftCorner().x(), otherObj.getTopLeftCorner().y() + i));
            gameObjects.addGameObject(ball);
            //destroy brick after three balls created
            gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);

        }
    }
}
