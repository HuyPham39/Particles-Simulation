import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

public class ObstacleAPI {
    /**
     * This class handles the communication between the database API 
     * and the simulation. It can load in the obstacles saved in the 
     * database and updates the database synchroniously as the 
     * simulation adds or substract the obstacles.
     * 
     * @author Huy Pham
     * @version 1.5
     * @since 2025-5-17
     */

    /**
     * Retrieves the obstacles in the database and turn it into a 
     * linked list.
     */
    public static ObstacleNode getObstacles() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            // Set up the http request, note that the ip address is 
            // defautly set to the localhost and the port is set to 
            // 6000 for http communication with the database API 
            Request request = new Request.Builder()
                                         .url("http://127.0.0.1:6000/obstacles")
                                         .get()
                                         .build();
            Response response = client.newCall(request).execute();
            // Retrieve the body of the response which contains the 
            // list of obstacles saved in the database
            String responseBody = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            // Create the linked list of obstacles
            ObstacleNode obstacleHeadNode = new ObstacleNode();
            ObstacleNode currNode = obstacleHeadNode;
            for (int i = 0; i < jsonNode.size(); i++) {
                int id = jsonNode.get(i)
                                 .get("id")
                                 .asInt();
                double x = jsonNode.get(i)
                                   .get("x")
                                   .asDouble();
                double y = jsonNode.get(i)
                                   .get("y")
                                   .asDouble();
                double r = jsonNode.get(i)
                                   .get("r")
                                   .asDouble();
                if (i == 0) {
                    currNode.setObstacle(new Obstacle(id, x, y, r));
                }
                else {
                    Obstacle obstacle = new Obstacle(id, x, y, r);
                    ObstacleNode newNode = new ObstacleNode(obstacle);
                    currNode.setNext(newNode);
                    currNode = currNode.getNext();
                }
            }
            if (obstacleHeadNode.getObstacle() == null) {
                return null;
            }
            return obstacleHeadNode;
        }
        catch(Exception ex) {
            System.out.println("There was an error.");
            return new ObstacleNode();
        }
    }

    /**
     * Adds an obstacle to the database.
     * 
     * @param obs The obstacle being added to the database.
     */
    public static void add(Obstacle obs) {
        try {
            int id = obs.getId();
            double x = obs.getX();
            double y = obs.getY();
            double r = obs.getRadius();
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            String jsonBody = String.format("{\n" +
                                            "    \"id\": %d,\n" +
                                            "    \"x\": %f,\n" +
                                            "    \"y\": %f,\n" +
                                            "    \"r\": %f\n" +
                                            "}", id, x, y, r);
            RequestBody body = RequestBody.create(mediaType, jsonBody);
            // Construct the http request, with the localhost and port 6000
            Request request = new Request.Builder()
                                         .url("http://127.0.0.1:6000/obstacle")
                                         .method("POST", body)
                                         .addHeader("Content-Type", "application/json")
                                         .build();
            Response response = client.newCall(request).execute();
        }
        catch(Exception ex) {
            System.out.println("There was an error.");
        }
    }

    /**
     * Removes an obstacle from the database.
     * 
     * @param id The id of the obstacle being removed from the database.
     */
    public static void remove(int id) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "[" + id + "]");
            // Construct the http request, with the localhost and port 6000
            Request request = new Request.Builder()
                                         .url("http://127.0.0.1:6000/obstacle")
                                         .method("DELETE", body)
                                         .addHeader("Content-Type", "application/json")
                                         .build();
            Response response = client.newCall(request).execute();
        }
        catch(Exception ex) {
            System.out.println("There was an error.");
        }
    }
}
