package brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class BrickStrategyFactory {

    private CollisionStrategy collisionStrategy;
    private GameObjectCollection gameObjects;
    private WindowController windowController;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private Vector2 windowsDimension;
    private UserInputListener inputListener;


    public BrickStrategyFactory(GameObjectCollection gameObjects, WindowController windowController,
                                ImageReader imageReader, SoundReader soundReader,
                                Vector2 windowsDimension,
                                UserInputListener inputListener) {
        this.gameObjects = gameObjects;
        this.windowController = windowController;

        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.windowsDimension = windowsDimension;
        this.inputListener = inputListener;
    }

    public CollisionStrategy getStrategy() {
//        System.out.println(rand);
        Random random = new Random();
        ArrayList<CollisionStrategy> strategies = new ArrayList<>();
        collisionStrategy = new ThreeBallsStrategy(gameObjects, imageReader, soundReader);
        strategies.add(collisionStrategy);
        collisionStrategy = new RemoveBrickStrategy(gameObjects);
        strategies.add(collisionStrategy);

        collisionStrategy =
                new AddNewPaddleStrategy(gameObjects,
                        imageReader,
                        inputListener,
                        windowsDimension);
        strategies.add(collisionStrategy);
        collisionStrategy = new WideOrNarrowStrategy(gameObjects,
                imageReader);
        strategies.add(collisionStrategy);
        //creat game speed strategies
        collisionStrategy = new AddSpeedStrategy(gameObjects,
                windowController,
                imageReader);
        strategies.add(collisionStrategy);
        return strategies.get(random.nextInt(strategies.size()));
//        if(rand) {
//            collisionStrategy = new ThreeBallsStrategy(gameObjects, imageReader, soundReader);
//            return collisionStrategy;
//        }
//        else return new RemoveBrickStrategy(gameObjects);
        //choose randomly between the possible brick strategies

        //    narrowPaddle = imageReader.readImage("assets/buffNarrow.png",true);
        //        widenPaddle = imageReader.readImage("assets/buffWiden.png",true);

    }
}
