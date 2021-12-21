import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.Brick;
import gameobjects.CollisionStrategy;
import gameobjects.Paddle;
import movement_strategies.AIMovementStratgey;
import movement_strategies.UserMovmentStratgey;

import java.awt.*;
import java.util.Random;

public class BouncingBallGameManager extends GameManager {

    private final int BORDER_WIDTH = 10;
    private final int PADDLE_HEIGHT = 20;
    private final int PADDLE_WIDTH = 150;
    private final int BRICK_WIDTH = 100;
    private final int BRICK_HEIGHT = 20;
    private final int BALL_RADIUS = 35;
    private final int BALL_SPEED = 200;
    private Ball ball;
    private WindowController windowController;
    private Vector2 windowsDimension;
    private int userLives = 3;


    public BouncingBallGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);

    }

    public static void main(String[] args) {
        new BouncingBallGameManager("BouncingBall", new Vector2(700, 500)).run();

    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener,
                               WindowController windowController) {
        this.windowController = windowController;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        windowController.setTargetFramerate(80);
        //windows dimension
        windowsDimension = windowController.getWindowDimensions();
        //creat ball
        creatBall(imageReader, soundReader, windowController);
        //set borders
        setBorders();
        //creat user paddles
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);
        creatUserPaddle(paddleImage, inputListener, windowsDimension);

        //creat ai paddle
        creatAIPaddle(windowsDimension, paddleImage);
        //creat brick image
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        //creat brick
        creatBrick(brickImage);
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

    private void creatUserLive(Renderable liveImage) {
        float liveWidth = 25;
        GameObject userLive;
        for (int i = 0; i < userLives; i++) {
            userLive = new GameObject(new Vector2((windowsDimension.x() - 100 + (i * liveWidth)),
                    0), new Vector2(20, 20), liveImage);
            gameObjects().addGameObject(userLive);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        chekForGameEnd();
    }

    // check if game end
    private void chekForGameEnd() {
        double ballHeight = ball.getCenter().y();
        String prompt = "";
        if (ballHeight < 0) {
            //we win
            prompt += "You win!";
        }
        if (ballHeight > windowsDimension.y()) {
            userLives--;
            //we lose
            if (userLives == 0) {
                prompt += "You Lose!";
            } else windowController.resetGame();
        }
        if (userLives == 0) {
            prompt += " play again?";
            if (windowController.openYesNoDialog(prompt)) {
                userLives = 3;
                windowController.resetGame();
            } else
                windowController.closeWindow();
        }

    }

    private void setBorders() {
        float xDim = windowsDimension.x(), yDim = windowsDimension.y();

        GameObject[] Borders = {
                //leftBorder
                new GameObject(Vector2.ZERO, new Vector2(BORDER_WIDTH, yDim), new RectangleRenderable(Color.cyan)),
                //rightBorder
                new GameObject(new Vector2(xDim - 10, 0), new Vector2(BORDER_WIDTH, yDim), new RectangleRenderable(Color.cyan))};
        //topBorder
        //  new GameObject(Vector2.ZERO, new Vector2(xDim, BORDER_WIDTH), null),
        //bottomBorder
        // new GameObject(new Vector2(0, yDim), new Vector2(xDim, BORDER_WIDTH), null)};

        //set all borders
        for (GameObject border : Borders)
            gameObjects().addGameObject(border);


    }

    //creat User paddle
    private void creatUserPaddle(Renderable paddleImage, UserInputListener inputListener, Vector2 windowsDimension) {
        // user paddle (Bottom)
        GameObject userPaddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                new UserMovmentStratgey(inputListener, windowsDimension.x()));
        userPaddle.setCenter(new Vector2(windowsDimension.x() / 2, windowsDimension.y() - 30));
        gameObjects().addGameObject(userPaddle);
    }

    //creat AI paddle
    private void creatAIPaddle(Vector2 windowsDimensions, Renderable paddleImage) {
        //ai paddle (Top)
        GameObject aiPaddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                new AIMovementStratgey(ball, windowsDimensions.x()));
        aiPaddle.setCenter(new Vector2(windowsDimensions.x() / 2, 30));

        gameObjects().addGameObject(aiPaddle);
    }

    //creat Ball
    private void creatBall(ImageReader imageReader, SoundReader soundReader, WindowController windowController) {
        //creating ball
        Renderable ballImage =
                imageReader.readImage("assets/ball.png", true);
        //creat sound
        SoundReader sound = new SoundReader(windowController);
        Sound collisionSound = sound.readSound("assets/blop.wav");

        ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
        //set ball center
        Vector2 windowsDimension = windowController.getWindowDimensions();
        ball.setCenter(windowsDimension.mult(0.5F));
        gameObjects().addGameObject(ball);
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= -1;
        if (rand.nextBoolean())
            ballVelY *= -1;
        //make ball move down
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    private void creatBrick(Renderable brickImage) {
        CollisionStrategy collisionStrategy = new CollisionStrategy(gameObjects());
        int Bricks = 49;
        int numOfBricks = 0;
        int numOfRow = 1;
        int bWidth = 50;
        int bheight = 21;
        GameObject brick;
        for (int i = 0; i < Bricks; i++) {
            brick = new Brick(Vector2.ZERO, new Vector2(BRICK_WIDTH, BRICK_HEIGHT), brickImage, collisionStrategy);
            brick.setCenter(new Vector2(numOfBricks * BRICK_WIDTH + bWidth, bWidth + bheight));

            numOfBricks++;
            //  gameObjects().addGameObject(brick);
            if (numOfBricks == 7) {
                numOfBricks = 0;
                bheight += 21;
                numOfRow++;
            }
            gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
        }

    }

}
