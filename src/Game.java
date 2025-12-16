import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

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

    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Initialize a new game: create the GUI, blocks, ball and paddle,
     * and add them to the game.
     */
    public void initialize() {
        // Create GUI 800x600
        this.gui = new GUI("Arkanoid Game", 800, 600);
        KeyboardSensor keyboard = this.gui.getKeyboardSensor();

        // [ASS3 - Step 11] Create a listener instance (one for all blocks)
        PrintingHitListener printListener = new PrintingHitListener();

        // --- Create Borders ---
        int width = 800;
        int height = 600;
        int borderSize = 20;

        // Top border
        Block top = new Block(
                new Rectangle(new Point(0, 0), width, borderSize),
                Color.GRAY);
        top.addHitListener(printListener);

        // Bottom border (in future assignments this might be the "death region")
        Block bottom = new Block(
                new Rectangle(new Point(0, height - borderSize), width, borderSize),
                Color.GRAY);
        bottom.addHitListener(printListener);

        // Left border
        Block left = new Block(
                new Rectangle(new Point(0, 0), borderSize, height),
                Color.GRAY);
        left.addHitListener(printListener);

        // Right border
        Block right = new Block(
                new Rectangle(new Point(width - borderSize, 0), borderSize, height),
                Color.GRAY);
        right.addHitListener(printListener);

        // Add all borders to the game
        addBlock(top);
        addBlock(bottom);
        addBlock(left);
        addBlock(right);

        // --- Create Game Blocks (The Pattern) ---
        // Colors for each row according to the assignment image
        Color[] colors = {Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE, Color.PINK, Color.GREEN};

        int blockWidth = 50;
        int blockHeight = 25;

        // Create 6 rows of blocks
        for (int i = 0; i < 6; i++) {
            Color rowColor = colors[i];

            // Create a "staircase" effect: row 0 has 12 blocks, row 1 has 11, etc.
            for (int j = 0; j < 12 - i; j++) {
                // Position calculation:
                // Start from the right edge (width - borderSize) and go left
                double x = (width - borderSize) - (j + 1) * blockWidth;
                // Start from Y=100 and go down
                double y = 100 + i * blockHeight;

                Rectangle rect = new Rectangle(new Point(x, y), blockWidth, blockHeight);
                Block block = new Block(rect, rowColor);

                // [ASS3 - Step 11] Register listener on every game block
                block.addHitListener(printListener);

                addBlock(block);
            }
        }

        // --- Create Balls ---
        // Ball 1
        Ball ball = new Ball(new Point(400, 300), 5, Color.WHITE);
        ball.setVelocity(4, 4);
        ball.setGameEnvironment(this.environment);
        this.addSprite(ball);

        // Ball 2
        Ball ball2 = new Ball(new Point(300, 300), 5, Color.WHITE);
        ball2.setVelocity(-4, 4);
        ball2.setGameEnvironment(this.environment);
        this.addSprite(ball2);

        // --- Create Paddle ---
        // Paddle position: centered horizontally, near the bottom
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

            // Draw background (optional, makes it look nicer)
            d.setColor(new Color(0, 0, 100)); // Dark Blue background
            d.fillRectangle(0, 0, 800, 600);

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