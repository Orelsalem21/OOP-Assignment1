public class Block implements Collidable {

    private Rectangle rectangle;

    /**
     * Create a new block using the given rectangle.
     *
     * @param rectangle the rectangle that defines the block shape
     */
    public Block(Rectangle rectangle) {
        this.rectangle = rectangle;
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
}
