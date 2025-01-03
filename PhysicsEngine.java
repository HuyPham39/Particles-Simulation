public class PhysicsEngine {
    public boolean checkCollision(InteractiveObject object1, InteractiveObject object2) {
        double[] object2_object1 = {object1.getX() - object2.getX(), object1.getY() - object2.getY()};
        double distance = Vector2DMath.magnitude(object2_object1);
        return distance <= object1.getRadius() + object2.getRadius();
    }

    public double[] collide(InteractiveObject object1, InteractiveObject object2) {
        double[] object2_object1 = {object1.getX() - object2.getX(), object1.getY() - object2.getY()};
        double[] normal = Vector2DMath.normal(object2_object1);
        double distance = object1.getRadius() + object2.getRadius();
        double x = object2.getX() + normal[0] * distance;
        double y = object2.getY() + normal[1] * distance;
        object1.setPosition(x, y);
        return normal;
    }

    public void interactionHandling(InteractiveObject object1, InteractiveObject object2, double elasticity) {
        if (checkCollision(object1, object2)) {
            double[] normal = collide(object1, object2);
            double[] velocity = {object1.getVelX() - object2.getVelY(), object1.getVelY() - object2.getVelY()};
            double[] velPerpendicular = Vector2DMath.projection(velocity, normal);
            double[] velTangent = {object1.getVelX() - velPerpendicular[0], object1.getVelY() - velPerpendicular[1]};
            double vel1X = -velPerpendicular[0] + velTangent[0];
            double vel1Y = -velPerpendicular[1] + velTangent[1];
            double vel2X = object2.getVelX() + (object1.getMass() / object2.getMass()) * (object1.getVelX() - vel1X);
            double vel2Y = object2.getVelY() + (object1.getMass() / object2.getMass()) * (object1.getVelY() - vel1Y);
            object1.setVelocity(vel1X * elasticity, vel1Y * elasticity);
            object2.setVelocity(vel2X * elasticity, vel2Y * elasticity);
        }
    }
}
