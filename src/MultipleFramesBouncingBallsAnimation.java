import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

public class MultipleFramesBouncingBallsAnimation {

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

    public static void main(String[] args) {
        // convert command-line arguments to radii array
        int[] radii = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            radii[i] = Integer.parseInt(args[i]);
        }

        if (radii.length == 0) {
            return;
        }

        // sort radii: smaller go to the smaller frame, larger to the bigger frame
        Arrays.sort(radii);
        int mid = radii.length / 2;

        int width = 800;
        int height = 800;
        GUI gui = new GUI("Multiple Frames Bouncing Balls", width, height);
        DrawSurface d;
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();

        // first frame (grey)
        int frame1Left = 50;
        int frame1Top = 50;
        int frame1Right = 500;
        int frame1Bottom = 500;

        // second frame (yellow)
        int frame2Left = 450;
        int frame2Top = 450;
        int frame2Right = 600;   // adjust if the assignment uses other numbers
        int frame2Bottom = 600;

        // balls in first (grey) frame: smaller radii
        Ball[] smallBalls = new Ball[mid];
        for (int i = 0; i < mid; i++) {
            int r = radii[i];

            int x = frame1Left + r + rand.nextInt((frame1Right - frame1Left) - 2 * r);
            int y = frame1Top + r + rand.nextInt((frame1Bottom - frame1Top) - 2 * r);

            Ball ball = new Ball(x, y, r, Color.BLUE);

            double speed = radiusToSpeed(r);
            double angle = rand.nextInt(360);
            Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
            ball.setVelocity(v);

            smallBalls[i] = ball;
        }

        // balls in second (yellow) frame: larger radii
        int bigCount = radii.length - mid;
        Ball[] bigBalls = new Ball[bigCount];
        for (int i = 0; i < bigCount; i++) {
            int r = radii[mid + i];

            int x = frame2Left + r + rand.nextInt((frame2Right - frame2Left) - 2 * r);
            int y = frame2Top + r + rand.nextInt((frame2Bottom - frame2Top) - 2 * r);

            Ball ball = new Ball(x, y, r, Color.RED);

            double speed = radiusToSpeed(r);
            double angle = rand.nextInt(360);
            Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
            ball.setVelocity(v);

            bigBalls[i] = ball;
        }

        // animation loop
        while (true) {
            d = gui.getDrawSurface();

            // draw frames
            d.setColor(Color.GRAY);
            d.fillRectangle(frame1Left, frame1Top,
                    frame1Right - frame1Left, frame1Bottom - frame1Top);

            d.setColor(Color.YELLOW);
            d.fillRectangle(frame2Left, frame2Top,
                    frame2Right - frame2Left, frame2Bottom - frame2Top);


            // move and draw balls in first frame
            for (Ball ball : smallBalls) {
                ball.moveOneStep(frame1Left, frame1Top, frame1Right, frame1Bottom);
                ball.drawOn(d);
            }

            // move and draw balls in second frame
            for (Ball ball : bigBalls) {
                ball.moveOneStep(frame2Left, frame2Top, frame2Right, frame2Bottom);
                ball.drawOn(d);
            }

            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}
