from flask import Flask, redirect, url_for, request
from flask import jsonify
from csv import DictReader, DictWriter

app = Flask(__name__)

# ******************************************
# Particle Simulation
# ******************************************

# load from CSV
with open('obstacleList.csv') as file:
    obstacles = list(DictReader(f=file))

@app.route('/obstacle', methods=['POST'])
def add_obstacle():
    # add to the obstacles (request.json)
    # save obstacles to csv
    obstacle = request.json
    obstacles.append(obstacle)
    with open('obstacleList.csv', 'w') as file:
        fieldNames = ["id", "x", "y", "r"]
        writer = DictWriter(f=file, fieldnames=fieldNames)
        writer.writeheader()
        writer.writerows(obstacles)
    return "Success"

@app.route('/obstacle', methods=['DELETE'])
def delete_obstacle():
    # delete obstacle from obstacles (perhaps use an id)
    # save obstacles to csv
    id = request.json[0]
    for idx in range(len(obstacles)):
        if int(obstacles[idx]["id"]) == id:
            obstacles.pop(idx)
            with open('obstacleList.csv', 'w') as file:
                fieldNames = ["id", "x", "y", "r"]
                writer = DictWriter(f=file, fieldnames=fieldNames)
                writer.writeheader()
                writer.writerows(obstacles)
            return "Success"
    return "Failed"

@app.route('/obstacles', methods=['GET'])
def get_obstacles():
    return jsonify(obstacles)

# ******************************************
# Mouse Clicks from Processing
# ******************************************

mouse_clicks = []

@app.route('/mouse_click', methods=['POST'])
def add_mouse_click():
    mouse_click = request.json
    mouse_clicks.append(mouse_click)
    return "Success"

@app.route('/mouse_clicks', methods=['GET'])
def get_mouse_clicks():
    return jsonify(mouse_clicks)

# ******************************************
# Route API
# ******************************************

routeArray = ["Route 1", "Route 2", "Route 3"]

@app.route('/route', methods=['post'])
def add_route():
    route = request.json
    print(route["name"])
    routeArray.append(route["name"])
    return "Successs"

@app.route('/route', methods=['DELETE'])
def delete_route():
    routeArray.remove(request.json["name"])
    return "Successs"

@app.route('/routes', methods=['GET','POST'])
def routes():
    return jsonify(routeArray)

# ******************************************
# Simple Call
# ******************************************

@app.route('/', methods=['GET'])
def hello_world():
    return 'Hello World'

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)