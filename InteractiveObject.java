public abstract class InteractiveObject {
    private double x;
    private double y;
    private double radius;

    public InteractiveObject(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void setPosition(double x, double y) {this.x = x; this.y = y;}
    public void setRadius(double radius) {
        if (radius > 0) {this.radius = radius;}
        else {System.out.println("Radius must be positive");}
    }

    public double getX() {return this.x;}
    public double getY() {return this.y;}
    public double getRadius() {return this.radius;}
    
    public abstract void setVelocity(double velX, double velY);
    public abstract double getVelX();
    public abstract double getVelY();
    public abstract double getMass();
}
