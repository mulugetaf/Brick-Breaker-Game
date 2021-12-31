import brick_strategies.BrickStrategyFactory;
import brick_strategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.Brick;
import gameobjects.Paddle;
import movement_strategies.AIMovementStratgey;
import movement_strategies.UserMovmentStratgey;

import java.awt.*;

/**
 * Game manager responsible to creat and run the game
 *
 * @author Mulugeta Fanta
 */
public class BouncingBallGameManager extends GameManager {

    private final int BORDER_WIDTH = 10;
    private final int PADDLE_HEIGHT = 20;
    private final int PADDLE_WIDTH = 150;
    private final int BRICK_WIDTH = 100;
    private final int BRICK_HEIGHT = 20;
    private final int BALL_RADIUS = 35;
    private final int BALL_SPEED = 200;
    private final int NUM_OF_BRICKS_PER_ROW = 7;
    private final int NUM_OF_BRICKS_PER_COLUMN = 11;
    private Ball ball;
    private WindowController windowController;
    private Vector2 windowsDimension;
    private int userLives = 3;
    private Counter numberOfBricks;

    /**
     * Constructor to new Game.
     *
     * @param windowTitle      can be null to indicate the usage of the default window title.
     * @param windowDimensions dimensions in pixels. can be null to indicate a full-screen
     *                         window whose size in pixels is the main screen's resolution.
     */
    public BouncingBallGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);

    }

    /**
     * main function, creat new {@link BouncingBallGameManager} and run.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        new BouncingBallGameManager("BouncingBall", new Vector2(750, 500)).run();

    }

    /**
     * initializeGame - The method will be called once when a GameGUIComponent is created,
     * and again after every invocation of windowController.resetGame().
     * Params:
     *
     * @param imageReader      – Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      – Contains a single method: readSound, which reads a wav file from disk.
     *                         See its documentation for help.
     * @param inputListener    – Contains a single method: isKeyPressed, which returns whether a given key is currently pressed by the user or not.
     *                         See its documentation.
     * @param windowController – Contains an array of helpful, self explanatory methods concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener,
                               WindowController windowController) {
        this.windowController = windowController;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        numberOfBricks = new Counter(NUM_OF_BRICKS_PER_ROW * NUM_OF_BRICKS_PER_COLUMN);
        windowController.setTargetFramerate(80);
        //windows dimension
        windowsDimension = windowController.getWindowDimensions();
        //creat ball
        Renderable ballImage =
                imageReader.readImage("assets/ball.png", true);
        creatBall(ballImage);
        //set borders
        setBorders();
        //creat user paddles
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);
        creatUserPaddle(windowsDimension, paddleImage, inputListener);
        //creat ai paddle
        creatAIPaddle(windowsDimension, paddleImage);
        //creat brick
        creatBrick(imageReader, soundReader, inputListener);
        //creat live image
        Renderable liveImage = imageReader.readImage("assets/heart.png", false);
        //creat user live
        creatUserLive(liveImage);
        //creat background
        GameObject background = new GameObject(
                Vector2.ZERO,
                windowController.getWindowDimensions(),
                imageReader.readImage("assets/DARK_BG2_small.jpeg", false));

        //add background
        gameObjects().addGameObject(background, Layer.BACKGROUND);

    }

    /**
     * Creat user live
     *
     * @param liveImage Renderable image of a live.
     */
    private void creatUserLive(Renderable liveImage) {
        float liveWidth = 25;
        GameObject userLive;
        for (int i = 0; i < userLives; i++) {
            //set three live images top right corner of the screen.
            userLive = new GameObject(new Vector2((windowsDimension.x() - 100 + (i * liveWidth)),
                    0), new Vector2(20, 20), liveImage);
            gameObjects().addGameObject(userLive);
        }
    }

    /**
     * Called once per frame. Any logic is put here. Rendering, on the other hand,
     * should only be done within 'render'.
     *
     * @param deltaTime The time, in seconds, that passed since the last invocation of this method
     *                  (i.e., since the last frame)
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        chekForGameEnd();
    }

    /**
     * Check if the game end
     */
    private void chekForGameEnd() {
        double ballHeight = ball.getCenter().y();
        String prompt = "";
        if (ballHeight < 0 || numberOfBricks.value() == 0) {
            //user win
            prompt += "You win!";
        }
        if (ballHeight > windowsDimension.y()) {
            userLives--;
            //user lose
            if (userLives == 0) {
                prompt += "You Lose!";
            } else windowController.resetGame();
        }
        if (userLives == 0 || prompt.equals("You win!")) {
            prompt += " play again?";
            if (windowController.openYesNoDialog(prompt)) {
                userLives = 3;
                windowController.resetGame();
            } else
                windowController.closeWindow();
        }

    }

    /**
     * Set borders
     */
    private void setBorders() {
        float xDim = windowsDimension.x(), yDim = windowsDimension.y();
        //leftBorder
        GameObject leftBorder = new GameObject(
                Vector2.ZERO, new Vector2(BORDER_WIDTH, yDim), new RectangleRenderable(Color.cyan));
        //rightBorder
        GameObject rightBorder = new GameObject(
                new Vector2(xDim - 10, 0), new Vector2(BORDER_WIDTH+10, yDim), new RectangleRenderable(Color.cyan));
        //set all borders
        gameObjects().addGameObject(leftBorder);
        gameObjects().addGameObject(rightBorder);

    }

    /**
     * creat User paddle
     *
     * @param windowsDimension dimensions in pixels. can be null to indicate a
     *                         full-screen window whose size in pixels is the main screen's resolution
     * @param paddleImage      Paddle image
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not.
     */
    private void creatUserPaddle(Vector2 windowsDimension, Renderable paddleImage, UserInputListener inputListener) {
        // user paddle (Bottom)
        GameObject userPaddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                new UserMovmentStratgey(inputListener, windowsDimension.x()));
        userPaddle.setCenter(new Vector2(windowsDimension.x() / 2, windowsDimension.y() - 30));
        gameObjects().addGameObject(userPaddle);
    }

    /**
     * creat AI paddle
     *
     * @param windowsDimension dimensions in pixels. can be null to indicate a
     *                         full-screen window whose size in pixels is the main screen's resolution
     * @param paddleImage      Paddle image
     */
    private void creatAIPaddle(Vector2 windowsDimension, Renderable paddleImage) {
        //ai paddle (Top)
        GameObject aiPaddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                new AIMovementStratgey(ball, windowsDimension.x()));
        aiPaddle.setCenter(new Vector2(windowsDimension.x() / 2, 30));

        gameObjects().addGameObject(aiPaddle);
    }

    /**
     * Creat Ball
     *
     * @param ballImage Ball image
     */
    private void creatBall(Renderable ballImage) {
        //creat sound
        SoundReader sound = new SoundReader(windowController);
        Sound collisionSound = sound.readSound("assets/blop.wav");
        //creating ball
        ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
        //set ball center
        Vector2 windowsDimension = windowController.getWindowDimensions();
        ball.setCenter(windowsDimension.mult(0.5F));
        gameObjects().addGameObject(ball);
        //set ball speed
        ball.setVelocity(new Vector2(BALL_SPEED, BALL_SPEED));
    }

    /**
     * @param imageReader   Add image to brick
     * @param soundReader   Make sound when the ball collisions in the brick
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not.
     */
    private void creatBrick(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener) {
        // creat brick_strategies so that each brick has its own behavior out of all behaviors.
        BrickStrategyFactory brickStrategy = new BrickStrategyFactory(gameObjects(),
                windowController, imageReader, soundReader,
                windowController.getWindowDimensions(), inputListener);
        //creat brick image
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);

        CollisionStrategy collisionStrategy = brickStrategy.getStrategy();
        int bricksCounter = 0;
        int bWidth = 77;
        int bheight = 21;
        GameObject brick;
        for (int i = 0; i < numberOfBricks.value(); i++) {

            brickStrategy = new BrickStrategyFactory(gameObjects(), windowController, imageReader, soundReader, windowController.getWindowDimensions(), inputListener);
            collisionStrategy = brickStrategy.getStrategy();
            brick = new Brick(Vector2.ZERO, new Vector2(BRICK_WIDTH, BRICK_HEIGHT), brickImage, collisionStrategy, numberOfBricks);
            brick.setCenter(new Vector2(bricksCounter * BRICK_WIDTH + numberOfBricks.value(), numberOfBricks.value() / 2 + bheight));

            bricksCounter++;
            if (bricksCounter == 7) {
                bricksCounter = 0;
                bheight += 21;
            }
            gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
        }

    }
}
