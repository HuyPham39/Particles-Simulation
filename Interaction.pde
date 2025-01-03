Simulation simulation = new Simulation();
double dt = 0.01;

void setup() {
  size(500, 500);
  ellipseMode(RADIUS);
}

void draw() {
  // Convert mouse position to the program basis
  double convertedMouseX = mouseX / (double) width;
  double convertedMouseY = 1.0 - (mouseY / (double) height);

  // set background to grey
  background(100, 100, 100);
  
  // draw Obstacles
  fill(255,255,255);
  ObstacleNode head = simulation.getObstacles();
  while (head != null) {
    circle((int) (head.getObstacle().getX() * width), (int) (height - head.getObstacle().getY() * height), (int) (head.getObstacle().getRadius() * width));
    head = head.getNext();
  }
  
  // draw particles and deal with collisions
  fill(0,0,255);
  head = simulation.getObstacles();
  Particle[] particles = simulation.getParticles();
  for (int i = 0; i < simulation.getNumParticles(); i++) {
    head = simulation.getObstacles();
    circle((int) (particles[i].getX() * width), (int) (height - particles[i].getY() * height), (int) (particles[i].getRadius() * width));
  }
  simulation.update(dt);
}

void keyPressed() {
  if (key == '+') {
    simulation.addParticles(50);
  }
  else if (key == '-') {
    simulation.removeParticles(50);
  }
}

void mousePressed() {
  if (mouseButton == LEFT) {
    simulation.addObstacle(mouseX / (double) width, 1.0 - (mouseY / (double) height));
  }
  else if (mouseButton == RIGHT) {
    simulation.removeObstacle(mouseX / (double) width, 1.0 - (mouseY / (double) height));
  }
}
    
