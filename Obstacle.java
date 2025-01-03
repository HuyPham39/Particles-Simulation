public class Obstacle extends InteractiveObject {
    private int id;

    public Obstacle(int id, double x, double y, double r) {super(x, y, r); this.id = id;}

    @Override
    public void setVelocity(double velX, double velY) {}
    @Override
    public double getVelX() {return 0.0;}
    @Override
    public double getVelY() {return 0.0;}
    @Override
    public double getMass() {return 1.0;}

    public int getId() {return id;}
}
