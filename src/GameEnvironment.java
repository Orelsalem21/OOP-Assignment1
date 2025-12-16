import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {

    private List<Collidable> collidables;

    /**
     * Create a new empty game environment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * Add the given collidable to the environment.
     *
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Remove a collidable from the environment.
     *
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null.
     * Else, return the information about the closest collision
     * that is going to occur.
     *
     * @param trajectory the line representing the object's path
     * @return CollisionInfo of the closest collision, or null if none
     */
    public CollisionInfo getClosestCollision(Line trajectory) {

        Point closestPoint = null;
        Collidable closestObject = null;
        double minDistance = Double.POSITIVE_INFINITY;

        for (Collidable c : this.collidables) {
            Rectangle rect = c.getCollisionRectangle();
            Point intersection = trajectory.closestIntersectionToStartOfLine(rect);

            if (intersection != null) {
                double distance = trajectory.start().distance(intersection);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint = intersection;
                    closestObject = c;
                }
            }
        }

        if (closestPoint == null) {
            return null;
        }

        return new CollisionInfo(closestPoint, closestObject);
    }
}
