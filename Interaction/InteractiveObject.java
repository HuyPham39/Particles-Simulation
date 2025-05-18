public abstract class InteractiveObject {
    /**
     * This abstract class provides the foundation for interactive 
     * objects in the simulation.
     * 
     * @author Huy Pham
     * @version 1.5
     * @since 2025-5-17
     */

    // Object's properties
    private double x;
    private double y;
    private double radius;

    public InteractiveObject(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    // Methods for setting the object's properties
    public void setPosition(double x, double y) {
        this.x = x; 
        this.y = y;
    }
    public void setRadius(double radius) {
        if (radius > 0) {
            this.radius = radius;
        }
        else {
            System.out.println("Radius must be positive");
        }
    }

    // Methods for accessing the object's properties
    public double getX()  {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public double getRadius() {
        return this.radius;
    }
    
    // Abstract methods for inheriting classes
    public abstract void setVelocity(double velX, double velY);
    public abstract double getVelX();
    public abstract double getVelY();
    public abstract double getMass();
}
