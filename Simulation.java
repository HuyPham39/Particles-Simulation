public class Simulation {
    /**
     * Represents a simulation of interactions between objects like particles 
     * and obstacles.
     * 
     * @author Huy Pham
     * @version 1.0
     * @since 2024-11-15
     */

    private Particle[] particles = new Particle[50];
    private ObstacleNode obstacleList = ObstacleAPI.getObstacles();
    private double elasticity = 0.6;
    private int numParticles = 0;
    private double gravity = -1.0;
    private PhysicsEngine physicsEngine = new PhysicsEngine();


    public void setElasticity(double e) {this.elasticity = e;}
    public void setGravity(double g) {this.gravity = g;}

    public Particle[] getParticles() {return this.particles;}
    public ObstacleNode getObstacles() {return this.obstacleList;}
    public double getElasticity() {return this.elasticity;}
    public int getNumParticles() {return this.numParticles;}
    public double getGravity() {return this.gravity;}


    /**
     * Adds particles into the simulation.
     * 
     * @param amount The amount of particles being added.
     */
    public void addParticles(int amount) {
        if ((numParticles + amount) > particles.length) {
            Particle[] newarr = new Particle[2 * particles.length];
            for (int i = 0; i < particles.length; i++) {
                newarr[i] = particles[i];
            }
            particles = newarr;
            newarr = null;
        }
        int pAdded = 0;
        for (int i = numParticles; (i < particles.length) && (pAdded < amount); i++) {
            particles[i] = new Particle(0.5, (pAdded / 15.0) + 1.2, 0.025, 1.0);
            if (pAdded % 2 == 0) {
                particles[i].setVelocity(pAdded / 100.0, pAdded / 100.0);
            } else {
                particles[i].setVelocity(-pAdded / 100.0, pAdded / 100.0);
            }
            ++pAdded;
        }
        numParticles += amount;
    }
    
    /**
     * Removes particles from the simulation.
     * 
     * @param amount The amount of particles being removed.
     */
    public void removeParticles(int amount) {
        if (amount > numParticles) {
            System.out.println("No particle to remove");
            return;
        }
        for (int i = 0; i < amount; i++) {
            particles[numParticles - 1] = null;
            --numParticles;

        }
    }

    /**
     * Add an obstacle into the simulation.
     * 
     * @param x The x coordinate of the new obstacle being added.
     * @param y The y coordinate of the new obstacle being added.
     */
    public void addObstacle(double x, double y) {
        if (obstacleList == null) {
            Obstacle obstacle = new Obstacle(0, x, y, 0.02);
            ObstacleNode newNode = new ObstacleNode(obstacle);
            obstacleList = newNode;
            ObstacleAPI.add(obstacle);
        }
        else {
            ObstacleNode head = obstacleList;
            while (head.getNext() != null) {
                head = head.getNext();
            }
            Obstacle obstacle = new Obstacle(head.getObstacle().getId() + 1, x, y, 0.02);
            ObstacleNode newNode = new ObstacleNode(obstacle);
            head.setNext(newNode);
            ObstacleAPI.add(obstacle);
        }
    }

    /**
     * Removes an obstacle from the simulation by searching for an obstacle near the cursor to remove.
     * If not, all obstacles are removed. 
     * 
     * @param x The x coordinate of the cursor.
     * @param y The y coordinate of the cursor.
     */
    public void removeObstacle(double x, double y) {
        ObstacleNode head = obstacleList;
        if (head == null) {return;}
        if (head.getNext() == null) {
            ObstacleAPI.remove(head.getObstacle().getId());
            obstacleList = null;
        }
        while (head.getNext() != null) {
            if ((Math.abs(head.getObstacle().getX() - x) <= 0.05) && 
                (Math.abs(head.getObstacle().getY() - y) <= 0.05)) {
                ObstacleAPI.remove(head.getObstacle().getId());
                obstacleList = obstacleList.getNext();
                return;
            }
            else if ((Math.abs(head.getNext().getObstacle().getX() - x) <= 0.05) && 
                (Math.abs(head.getNext().getObstacle().getY() - y) <= 0.05)) {
                ObstacleNode nodeToLink = head.getNext().getNext();
                ObstacleAPI.remove(head.getNext().getObstacle().getId());
                head.getNext().setNext(null);
                head.setNext(nodeToLink);
                return;
            }
            head = head.getNext();
        }  
        return;
    }

    /**
     * Updates the simulation's attributes by an interval of dt.
     * 
     * @param dt The interval between each updates
     */
    public void update(double dt) {
        for (int i = 0; i < numParticles; i++) {
            Particle particle = particles[i];
            ObstacleNode head = obstacleList;
            // Deal with collisions
            while (head != null) {
                physicsEngine.interactionHandling(particle, head.getObstacle(), elasticity);
                head = head.getNext();
            }
            for (int j = 0; j < numParticles; j++) {
                Particle referencingParticle = particles[j];
                if (particle == referencingParticle) {continue;}
                physicsEngine.interactionHandling(particle, referencingParticle, elasticity);
            }
            // Update particle's attributes
            double x = particle.getX() + particle.getVelX() * dt;
            double y = particle.getY() + particle.getVelY() * dt + 0.5 * gravity * dt * dt;
            double velX = particle.getVelX();
            double velY = particle.getVelY() + gravity * dt;
            particle.setPosition(x, y);
            particle.setVelocity(velX, velY);
            if (particle.getY() <= particle.getRadius()) {
                particle.setPosition(x, particle.getRadius());
                particle.setVelocity(velX, -1.0 * velY * elasticity);
            }
        }
    }
}  
