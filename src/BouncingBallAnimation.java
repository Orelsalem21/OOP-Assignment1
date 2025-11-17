import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

public class BouncingBallAnimation {

    // draw animation with one moving ball
    private static void drawAnimation(Point start, double dx, double dy) {
        GUI gui = new GUI("title", 200, 200);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball((int) start.getX(), (int) start.getY(), 30, java.awt.Color.BLACK);
        ball.setVelocity(dx, dy);

        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);
        }
    }

    public static void main(String[] args) {
        Point start = new Point(100, 100);
        drawAnimation(start, 2, 2);
    }
}
