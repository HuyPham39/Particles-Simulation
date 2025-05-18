public class PhysicsEngine {
    /**
     * This class handles all the physical interactions inside the simulation.
     * 
     * @author Huy Pham
     * @version 1.5
     * @since 2025-5-17
     */

    /**
     * Check for collisions between 2 objects by considering the distance from 
     * each other centers. If the distance is less or equal to the combined 
     * radius of the 2 objects, then its a collision. 
     * 
     * @param object1 The first object.
     * @param object2 The second object.
     */
    public boolean checkCollision(InteractiveObject object1, InteractiveObject object2) {
        double[] object2_object1 = {object1.getX() - object2.getX(), object1.getY() - object2.getY()};
        double distance = Vector2DMath.magnitude(object2_object1);
        return distance <= object1.getRadius() + object2.getRadius();
    }

    /**
     * Set the position of 2 colliding objects so that they no longer collides. 
     * First it calculates the vector from object 2 to object 1 and normalize 
     * that vector. Then, it scales back that vector such that the magnitude is 
     * the combined radius of the 2 objects. Finally, the first object is set to 
     * the destination of where that vector points to.   
     * 
     * @param object1 The first object.
     * @param object2 The second object.
     */
    public double[] collide(InteractiveObject object1, InteractiveObject object2) {
        double[] object2_object1 = {object1.getX() - object2.getX(), object1.getY() - object2.getY()};
        double[] normal = Vector2DMath.normal(object2_object1);
        double distance = object1.getRadius() + object2.getRadius();
        double x = object2.getX() + normal[0] * distance;
        double y = object2.getY() + normal[1] * distance;
        object1.setPosition(x, y);
        return normal;
    }

    /**
     * Handles the collision between 2 objects.  
     * 
     * @param object1 The first object.
     * @param object2 The second object.
     * @param elasticity The constant which determines the elasticity of the collision.
     */
    public void interactionHandling(InteractiveObject object1, InteractiveObject object2, double e) {
        if (checkCollision(object1, object2)) {
            // Set the colliding objects' positions to be no longer colliding as well as 
            // providing a vector which is perpendicular to the plane of contact
            double[] normal = collide(object1, object2);
            
            // Get the initial velocities and decompose them into 2 components: 
            // perpendicular or tangent to the plane of contact
            double[] u1 = {object1.getVelX(), object1.getVelY()};
            double[] u1_perpendicular = Vector2DMath.projection(u1, normal);
            double[] u1_tangent = {u1[0] - u1_perpendicular[0], u1[1] - u1_perpendicular[1]}; 

            double[] u2 = {object2.getVelX(), object2.getVelY()};
            double[] u2_perpendicular = Vector2DMath.projection(u2, normal);
            double[] u2_tangent = {u2[0] - u2_perpendicular[0], u2[1] - u2_perpendicular[1]};

            // Set up the variables for the solver. Note that only the perpendicular components 
            // of the velocities are accounted and that the solver was derived from the 
            // conservation of momentum and the restitution relation 
            double m1 = object1.getMass();
            double u1X = u1_perpendicular[0];
            double u1Y = u1_perpendicular[1];

            double m2 = object2.getMass();
            double u2X = u2_perpendicular[0];
            double u2Y = u2_perpendicular[1];

            double v1X = (m1 - e*m2) / (m1 + m2) * u1X + ((1+e) * m2) / (m1 + m2) * u2X;
            double v1Y = (m1 - e*m2) / (m1 + m2) * u1Y + ((1+e) * m2) / (m1 + m2) * u2Y;

            double v2X = ((1+e) * m1) / (m1 + m2) * u1X + (m2 - e*m1) / (m1 + m2) * u2X;
            double v2Y = ((1+e) * m1) / (m1 + m2) * u1Y + (m2 - e*m1) / (m1 + m2) * u2Y;

            // Make sure to add the tangent components afterwards
            object1.setVelocity(v1X + u1_tangent[0], v1Y + u1_tangent[1]);
            object2.setVelocity(v2X + u2_tangent[0], v2Y + u2_tangent[1]);
        }
    }
}
