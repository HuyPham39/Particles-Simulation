import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

public class ObstacleAPI {
    public static ObstacleNode getObstacles() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
            Request request = new Request.Builder()
                .url("http://0.0.0.0:8000/obstacles")
                .get()
                .build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            ObstacleNode obstacleHeadNode = new ObstacleNode();
            ObstacleNode currNode = obstacleHeadNode;
            for (int i = 0; i < jsonNode.size(); i++) {
                int id = jsonNode.get(i).get("id").asInt();
                double x = jsonNode.get(i).get("x").asDouble();
                double y = jsonNode.get(i).get("y").asDouble();
                double r = jsonNode.get(i).get("r").asDouble();
                if (i == 0) {currNode.setObstacle(new Obstacle(id, x, y, r));}
                else {
                    Obstacle obstacle = new Obstacle(id, x, y, r);
                    ObstacleNode newNode = new ObstacleNode(obstacle);
                    currNode.setNext(newNode);
                    currNode = currNode.getNext();
                }
            }
            if (obstacleHeadNode.getObstacle() == null) {return null;}
            return obstacleHeadNode;
        }
        catch(Exception ex) {
            System.out.println("There was an error.");
            return new ObstacleNode();
        }
    }

    public static void add(Obstacle obs) {
        try {
            int id = obs.getId();
            double x = obs.getX();
            double y = obs.getY();
            double r = obs.getRadius();
            OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"id\" : " + id + ",\r\n    \"x\" : " + x + ",\r\n    \"y\" : " + y + ",\r\n    \"r\" : " + r + "\r\n}");
            Request request = new Request.Builder()
                .url("http://0.0.0.0:8000/obstacle")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
            Response response = client.newCall(request).execute();
        }
        catch(Exception ex) {
            System.out.println("There was an error.");
        }
    }

    public static void remove(int id) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "[" + id + "]");
            Request request = new Request.Builder()
                .url("http://0.0.0.0:8000/obstacle")
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
