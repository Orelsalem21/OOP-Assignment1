import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.Random;

public class MultipleBouncingBallsAnimation {

    // helper: convert radius to speed
    private static double radiusToSpeed(int r) {
        if (r <= 0) {
            return 0;
        }
        if (r >= 50) {
            return 2;
        }
        return 50.0 / r;
    }

    // main method
    public static void main(String[] args) {
        // convert command-line arguments to radii array
        int[] radii = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            radii[i] = Integer.parseInt(args[i]);
        }

        // animation setup
        int width = 200;
        int height = 200;
        GUI gui = new GUI("Multiple Bouncing Balls", width, height);
        DrawSurface d;
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();

        // create balls
        Ball[] balls = new Ball[radii.length];
        for (int i = 0; i < radii.length; i++) {
            int r = radii[i];

            int x = r + rand.nextInt(width - 2 * r);
            int y = r + rand.nextInt(height - 2 * r);

            Ball ball = new Ball(x, y, r, Color.BLACK);

            double speed = radiusToSpeed(r);
            double angle = rand.nextInt(360);
            Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
            ball.setVelocity(v);

            balls[i] = ball;
        }

        // animation loop
        while (true) {
            d = gui.getDrawSurface();

            for (Ball ball : balls) {
                ball.moveOneStep(width, height);
                ball.drawOn(d);
            }

            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}
