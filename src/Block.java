import biuoop.DrawSurface;
import java.awt.Color;

public class Block implements Collidable, Sprite {

    private Rectangle rectangle;
    private Color color;

    /**
     * Create a new block using the given rectangle.
     * The block will use a default color.
     *
     * @param rectangle the rectangle that defines the block shape
     */
    public Block(Rectangle rectangle) {
        this(rectangle, Color.GRAY);
    }

    /**
     * Create a new block using the given rectangle and color.
     *
     * @param rectangle the rectangle that defines the block shape
     * @param color     the color of the block
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    /**
     * Return the collision rectangle of the block.
     *
     * @return the rectangle of this block
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Calculate the new velocity after hitting this block.
     *
     * If the collision point is on a vertical edge,
     * reverse the horizontal direction.
     *
     * If the collision point is on a horizontal edge,
     * reverse the vertical direction.
     *
     * @param collisionPoint  the point where the collision occurs
     * @param currentVelocity the velocity before the hit
     * @return a new velocity after the collision
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {

        double newDx = currentVelocity.getDx();
        double newDy = currentVelocity.getDy();

        double xLeft = rectangle.getUpperLeft().getX();
        double yTop = rectangle.getUpperLeft().getY();
        double xRight = xLeft + rectangle.getWidth();
        double yBottom = yTop + rectangle.getHeight();

        double px = collisionPoint.getX();
        double py = collisionPoint.getY();

        // Vertical edges
        if (px == xLeft || px == xRight) {
            newDx = -newDx;
        }

        // Horizontal edges
        if (py == yTop || py == yBottom) {
            newDy = -newDy;
        }

        return new Velocity(newDx, newDy);
    }

    /**
     * Draw the block on the given DrawSurface.
     *
     * @param d the surface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        // Fill the block with its color
        d.setColor(this.color);
        Point upperLeft = this.rectangle.getUpperLeft();
        int x = (int) upperLeft.getX();
        int y = (int) upperLeft.getY();
        int width = (int) this.rectangle.getWidth();
        int height = (int) this.rectangle.getHeight();
        d.fillRectangle(x, y, width, height);

        // Draw a black frame (stroke) around the block
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, width, height);
    }

    /**
     * Notify the block that time has passed.
     * Currently, blocks do not change over time.
     */
    @Override
    public void timePassed() {
        // no behavior for now
    }
}