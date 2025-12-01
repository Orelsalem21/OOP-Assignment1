import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TestGameEnvironment {

    public static void main(String[] args) {
        int width = 800;
        int height = 600;

        GUI gui = new GUI("Test GameEnvironment", width, height);
        Sleeper sleeper = new Sleeper();

        GameEnvironment environment = new GameEnvironment();
        List<Block> blocks = new ArrayList<Block>();

        // Create border blocks
        Block topBorder = new Block(new Rectangle(new Point(0, 0), width, 20));
        Block bottomBorder = new Block(new Rectangle(new Point(0, height - 20), width, 20));
        Block leftBorder = new Block(new Rectangle(new Point(0, 0), 20, height));
        Block rightBorder = new Block(new Rectangle(new Point(width - 20, 0), 20, height));

        // Add border blocks to lists
        blocks.add(topBorder);
        blocks.add(bottomBorder);
        blocks.add(leftBorder);
        blocks.add(rightBorder);

        environment.addCollidable(topBorder);
        environment.addCollidable(bottomBorder);
        environment.addCollidable(leftBorder);
        environment.addCollidable(rightBorder);

        // Add an inner block in the middle
        Block middleBlock = new Block(
                new Rectangle(new Point(300, 250), 200, 20)
        );
        blocks.add(middleBlock);
        environment.addCollidable(middleBlock);

        // Create a ball
        Ball ball = new Ball(new Point(200, 150), 10, Color.RED);
        ball.setVelocity(4, 3);
        ball.setGameEnvironment(environment);

        while (true) {
            DrawSurface d = gui.getDrawSurface();

            // Draw background
            d.setColor(Color.WHITE);
            d.fillRectangle(0, 0, width, height);

            // Draw blocks
            d.setColor(Color.GRAY);
            for (Block b : blocks) {
                Rectangle rect = b.getCollisionRectangle();
                Point upperLeft = rect.getUpperLeft();
                int x = (int) upperLeft.getX();
                int y = (int) upperLeft.getY();
                int w = (int) rect.getWidth();
                int h = (int) rect.getHeight();
                d.fillRectangle(x, y, w, h);
            }

            // Draw ball
            ball.drawOn(d);

            gui.show(d);

            // Move ball one step according to game environment
            ball.moveOneStep();

            sleeper.sleepFor(16); // ~60 FPS
        }
    }
}
