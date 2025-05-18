public class Particle extends InteractiveObject {
    /**
     * This class represents a particle in the simulation.
     * 
     * @author Huy Pham
     * @version 1.5
     * @since 2025-5-17
     */

    // The particle's attributes
    private double mass;
    private double velocityX;
    private double velocityY;

    public Particle(double x, double y, double r, double mass) {
        super(x, y, r);
        this.mass = mass;
        this.velocityX = 0.0;
        this.velocityY = 0.0;
    }
    
    // Setters
    public void setMass(double mass) {
        this.mass = mass;
    }
    @Override
    public void setVelocity(double vx, double vy) {
        this.velocityX = vx; 
        this.velocityY = vy;
    }
    
    // Getters
    @Override
    public double getMass() {
        return this.mass;
    }
    @Override
    public double getVelX() {
        return this.velocityX;
    }
    @Override
    public double getVelY() {
        return this.velocityY;
    }
}
