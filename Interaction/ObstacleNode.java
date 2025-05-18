public class ObstacleNode {
    /**
     * This class provides a linked list data structure for 
     * obstacles in the simulation.
     * 
     * @author Huy Pham
     * @version 1.5
     * @since 2025-5-17
     */

    // Properties of a node
    private Obstacle obstacle;
    private ObstacleNode nextNode;

    // Constructor for an empty linked list
    public ObstacleNode() {}

    public ObstacleNode(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    // Getter methods
    public Obstacle getObstacle() {
        return obstacle;
    }
    public ObstacleNode getNext() {
        return nextNode;
    }

    // Setter methods
    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }
    public void setNext(ObstacleNode next) {
        nextNode = next;
    }
}