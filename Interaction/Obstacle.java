public class Obstacle extends InteractiveObject {
    /**
     * Represents an obstacle in the simulation.
     * 
     * @author Huy Pham
     * @version 1.5
     * @since 2025-5-17
     */

    // Each obstacle has an id so different simulations 
    // can be saved for future studies.
    private int id;

    public Obstacle(int id, double x, double y, double r) {
        super(x, y, r); 
        this.id = id;
    }

    // An obstacle does not have any velocities as 
    // it always stays static. 
    @Override
    public void setVelocity(double velX, double velY) {
    }
    @Override
    public double getVelX() {
        return 0.0;
    }
    @Override
    public double getVelY() {
        return 0.0;
    }
    @Override
    public double getMass() {
        return 1.0;
    }

    // A method for accessing obstacle's ID.
    public int getId() {
        return id;
    }
}
