import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import biuoop.KeyboardSensor;

import java.awt.Color;


/**
 * The main game class: holds sprites and collidables and runs the animation.
 */
public class Game {

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;

    /**
     * Create a new empty game.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
    }

    /**
     * Add a collidable to the game environment.
     *
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add a sprite to the game.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initialize a new game: create the GUI, blocks, ball and paddle,
     * and add them to the game.
     */
    public void initialize() {
        // create GUI 800x600
        this.gui = new GUI("Test Game", 800, 600);
        KeyboardSensor keyboard = this.gui.getKeyboardSensor();

        // create borders as blocks
        int width = 800;
        int height = 600;
        int borderSize = 20;

        // top
        Block top = new Block(
                new Rectangle(new Point(0, 0), width, borderSize),
                Color.GRAY);
        // bottom
        Block bottom = new Block(
                new Rectangle(new Point(0, height - borderSize), width, borderSize),
                Color.GRAY);
        // left
        Block left = new Block(
                new Rectangle(new Point(0, 0), borderSize, height),
                Color.GRAY);
        // right
        Block right = new Block(
                new Rectangle(new Point(width - borderSize, 0), borderSize, height),
                Color.GRAY);

        addBlock(top);
        addBlock(bottom);
        addBlock(left);
        addBlock(right);

        // a block in the middle
        Block middle = new Block(
                new Rectangle(new Point(300, 250), 200, 20),
                Color.DARK_GRAY);
        addBlock(middle);

        // create a ball
        Ball ball = new Ball(new Point(400, 300), 7, Color.RED);
        ball.setVelocity(3, 3);
        ball.setGameEnvironment(this.environment);
        this.addSprite(ball);

        // create a paddle near the bottom
        Rectangle paddleRect = new Rectangle(new Point(350, 560), 100, 20);
        Paddle paddle = new Paddle(paddleRect, Color.ORANGE, keyboard, 8);
        paddle.addToGame(this);
    }

    /**
     * Helper: add a block as both sprite and collidable.
     *
     * @param block the block to add
     */
    private void addBlock(Block block) {
        this.addCollidable(block);
        this.addSprite(block);
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = this.gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            this.gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Temporary main for testing Game.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
