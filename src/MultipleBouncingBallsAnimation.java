
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.Random;

public class MultipleBouncingBallsAnimation {
    public static void main(String[] args) {
        // Window size
        int width = 800;
        int height = 600;

        // Create GUI
        GUI gui = new GUI("Multiple Bouncing Balls", width, height);
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();

        // Parse ball sizes from args
        int[] sizes = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            sizes[i] = Integer.parseInt(args[i]);
        }

        // Create balls array
        Ball[] balls = new Ball[sizes.length];
        for (int i = 0; i < sizes.length; i++) {
            int r = sizes[i];
            // Random position ensuring ball is fully inside frame
            int x = rand.nextInt(width - 2 * r) + r;
            int y = rand.nextInt(height - 2 * r) + r;
            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            Ball ball = new Ball(x, y, r, color);

            // Speed based on size (larger balls slower)
            double speed;
            if (r > 50) {
                speed = 1;
            } else {
                speed = 100.0 / r; // inverse proportional
            }

            // Random angle
            double angle = rand.nextInt(360);
            ball.setVelocity(Velocity.fromAngleAndSpeed(angle, speed));
            balls[i] = ball;
        }

        // Animation loop
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            for (Ball ball : balls) {
                ball.moveOneStep(0, 0, width, height);
                ball.drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}
