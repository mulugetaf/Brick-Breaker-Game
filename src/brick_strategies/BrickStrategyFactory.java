package brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Brick strategy factory (Factory pattern), create object without exposing the creation logic.
 */
public class BrickStrategyFactory {

    private final GameObjectCollection gameObjects;
    private final WindowController windowController;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final Vector2 windowsDimension;
    private final UserInputListener inputListener;
    private Map<Integer, Double> distribution;
    private double distSum;

    /**
     * @param gameObjects      A container for accumulating/removing instances of GameObject
     *                         and for handling their collisions.
     * @param windowController Provides control and info regarding the game.
     * @param imageReader      Used to read images from disk or from within a jar.
     * @param soundReader      Class for reading Sounds from the disk or from within a jar.
     * @param windowsDimension dimensions in pixels. can be null to indicate a
*                         full-screen window whose size in pixels is the main screen's resolution.
     * @param inputListener    An interface for reading user input in the current frame.
     */
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


        distribution = new HashMap<>();
    }

    /**
     * Returns a strategy from the list, the strategy is randomly selected.
     *
     * @return Random strategy from strategies.
     */
    public CollisionStrategy getStrategy() {
        Random random = new Random();
        // arraylist to store all strategies
        ArrayList<CollisionStrategy> strategies = new ArrayList<>();

        /*
         creat multiple behavior strategy
         select 5 elements from this array and the element with high weight values has higher
         probability to be selected,the first strategy in the arraylist is RemoveBrickStrategy
         so it has higher probability to be selected.
         */
        addNumber(0, 0.075);
        addNumber(1, 0.7);//Adds the numerical value 1 represent index , with a probability of 0.7
        addNumber(2, 0.075);
        addNumber(3, 0.075);
        addNumber(4, 0.075);


        // creat AddBallStrategy - 0
        CollisionStrategy collisionStrategy = new AddBallStrategy(gameObjects, imageReader, soundReader);
        strategies.add(collisionStrategy);
        // creat remove brick strategies - 1
        collisionStrategy = new RemoveBrickStrategy(gameObjects);
        strategies.add(collisionStrategy);
        // creat add new paddle strategies - 2
        collisionStrategy = new AddNewPaddleStrategy(gameObjects, imageReader, inputListener, windowsDimension);
        strategies.add(collisionStrategy);
        // creat wide or narrow  strategies
        collisionStrategy = new WideOrNarrowStrategy(gameObjects, imageReader);
        strategies.add(collisionStrategy);
        //  creat game speed strategies
        collisionStrategy = new AddSpeedStrategy(gameObjects, windowController, imageReader);
        strategies.add(collisionStrategy);
        // creat MultipleBehaviorStrategy
        collisionStrategy = new MultipleBehaviorStrategy(strategies.get(random.nextInt(strategies.size())),
                strategies.get(random.nextInt(strategies.size())));
        strategies.add(collisionStrategy);

        int rand = getDistributedRandomNumber();
        return strategies.get(rand);
    }

    /**
     * Helper function to get random index from the list
     *
     * @return Random index value.
     */
    public int getDistributedRandomNumber() {
        double rand = Math.random();
        double ratio = 1.0f / distSum;
        double tempDist = 0;
        for (Integer i : distribution.keySet()) {
            tempDist += distribution.get(i);
            if (rand / ratio <= tempDist) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Helper function to add key,value to map and calculate sum of distribution.
     *
     * @param value        index of the strategy from list.
     * @param distribution probability to be selected.
     */
    public void addNumber(int value, double distribution) {
        if (this.distribution.get(value) != null) {
            distSum -= this.distribution.get(value);
        }
        this.distribution.put(value, distribution);
        distSum += distribution;
    }

}

