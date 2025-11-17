import biuoop.DrawSurface;
import java.awt.Color;

public class Ball {
    // data members
    private Point center;
    private int r;
    private Color color;
    private Velocity velocity;

    // constructor
    public Ball(Point center, int r, Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.velocity = new Velocity(0, 0); // default velocity
    }

    // additional constructor for (x,y,r,color) - used in BallsTest1
    public Ball(int x, int y, int r, Color color) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
        this.velocity = new Velocity(0, 0); // default velocity
    }

    // accessors
    public int getX() {
        return (int) this.center.getX();
    }

    public int getY() {
        return (int) this.center.getY();
    }

    public int getSize() {
        return this.r;
    }

    public Color getColor() {
        return this.color;
    }

    // set velocity by object
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    // set velocity by dx, dy
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    // get current velocity
    public Velocity getVelocity() {
        return this.velocity;
    }
    // move the ball one step, bouncing inside a 200x200 frame
    public void moveOneStep() {
        moveOneStep(200, 200);
    }

    // move the ball one step, bouncing inside a given frame
    public void moveOneStep(int width, int height) {
        double nextX = this.center.getX() + this.velocity.getDx();
        double nextY = this.center.getY() + this.velocity.getDy();

        // check horizontal borders (left / right)
        if (nextX - this.r < 0 || nextX + this.r > width) {
            this.velocity = new Velocity(-this.velocity.getDx(), this.velocity.getDy());
        }

        // check vertical borders (top / bottom)
        if (nextY - this.r < 0 || nextY + this.r > height) {
            this.velocity = new Velocity(this.velocity.getDx(), -this.velocity.getDy());
        }

        this.center = this.velocity.applyToPoint(this.center);
    }
    // move the ball one step, bouncing inside a rectangle [left,right] x [top,bottom]
    public void moveOneStep(int left, int top, int right, int bottom) {
        double nextX = this.center.getX() + this.velocity.getDx();
        double nextY = this.center.getY() + this.velocity.getDy();

        if (nextX - this.r < left || nextX + this.r > right) {
            this.velocity = new Velocity(-this.velocity.getDx(), this.velocity.getDy());
        }

        if (nextY - this.r < top || nextY + this.r > bottom) {
            this.velocity = new Velocity(this.velocity.getDx(), -this.velocity.getDy());
        }

        this.center = this.velocity.applyToPoint(this.center);
    }

    // draw the ball on the given DrawSurface
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.r);
    }
}
