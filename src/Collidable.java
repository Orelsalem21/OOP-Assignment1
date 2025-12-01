public interface Collidable {

    /**
     * Return the "collision shape" of the object.
     *
     * @return the collision rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint
     * with a given velocity.
     * The return value is the new velocity expected after the hit.
     *
     * @param collisionPoint  the point where the collision occurs
     * @param currentVelocity the velocity before the hit
     * @return new velocity after the collision
     */
    Velocity hit(Point collisionPoint, Velocity currentVelocity);
}
