public class Particle extends InteractiveObject {
    private double mass;
    private double velocityX;
    private double velocityY;

    public Particle(double x, double y, double r, double mass) {
        super(x, y, r);
        this.mass = mass;
        this.velocityX = 0.0;
        this.velocityY = 0.0;
    }
    
    public void setMass(double mass) {this.mass = mass;}
    @Override
    public void setVelocity(double vx, double vy) {this.velocityX = vx; this.velocityY = vy;}
    @Override
    public double getMass() {return this.mass;}
    @Override
    public double getVelX() {return this.velocityX;}
    @Override
    public double getVelY() {return this.velocityY;}
}
