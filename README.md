# Particles Simulation

### Project Owner: 

Huy Pham

### Description: 

A simulation application that aims to visualize physical interactions between particles and obstacles using Processing.

* Gravity is set to default as -1.0 m/s^2
* Elasticity is a variable that can be changed, defaultly as 0.6
* Obstacles' positions are constantly updated to the database 

### How to run the program

* Install Processing
* Pull the repository and open it in Processing.exe
* Add all of the dependencies inside folder "lib" into your Processing sketchbook by navigating to Sketch/Add File..
* Run api.py prior to using the program, otherwise it will crash
  + You can either run it on your local machine or on a Virtual Machine by initating the Docker image
  + Make sure you have the required dependency, i.e. Flask
* Click Run or Ctrl + R