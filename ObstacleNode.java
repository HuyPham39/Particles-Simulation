public class ObstacleNode {
    private Obstacle obstacle;
    private ObstacleNode nextNode;
    
    public ObstacleNode() {}
    public ObstacleNode(Obstacle obstacle) {this.obstacle = obstacle;}
    
    public Obstacle getObstacle() {return obstacle;}

    public void setObstacle(Obstacle obstacle) {this.obstacle = obstacle;}
    
    public ObstacleNode getNext() {return nextNode;}
    
    public void setNext(ObstacleNode next) {nextNode = next;}
  }
