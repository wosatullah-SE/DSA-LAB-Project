import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class RouteGraph {
    private HashMap<String, ArrayList<Edge>> graph;

    public RouteGraph() {
        graph = new HashMap<String, ArrayList<Edge>>();
    }

    public void addLocation(String location) {
        if (!graph.containsKey(location)) {
            graph.put(location, new ArrayList<Edge>());
        }
    }

    public void addRoute(String source, String destination, double distance) {
        addLocation(source);
        addLocation(destination);
        graph.get(source).add(new Edge(destination, distance));
        graph.get(destination).add(new Edge(source, distance));
    }

    public void loadDefaultCityMap() {
        addRoute("Ambulance Base", "Main Road", 3);
        addRoute("Main Road", "City Hospital", 4);
        addRoute("Main Road", "Canal Road", 5);
        addRoute("Canal Road", "General Hospital", 6);
        addRoute("Canal Road", "University Gate", 3);
        addRoute("University Gate", "Emergency Spot 1", 2);
        addRoute("Emergency Spot 1", "Ring Road", 4);
        addRoute("Ring Road", "Life Care Clinic", 5);
        addRoute("Ring Road", "Emergency Spot 2", 3);
        addRoute("Emergency Spot 2", "General Hospital", 4);
        addRoute("City Hospital", "Central Blood Bank", 3);
        addRoute("General Hospital", "North Blood Bank", 4);
        addRoute("Life Care Clinic", "North Blood Bank", 5);
    }

    public RouteResult findShortestPathDijkstra(String source, String destination) {
        if (!graph.containsKey(source) || !graph.containsKey(destination)) {
            return new RouteResult(false, 0, new String[0], "Source or destination does not exist in graph.");
        }

        ArrayList<String> nodes = new ArrayList<String>(graph.keySet());
        HashMap<String, Integer> indexMap = new HashMap<String, Integer>();
        for (int i = 0; i < nodes.size(); i++) {
            indexMap.put(nodes.get(i), i);
        }

        double[] distance = new double[nodes.size()];
        boolean[] visited = new boolean[nodes.size()];
        int[] parent = new int[nodes.size()];

        for (int i = 0; i < nodes.size(); i++) {
            distance[i] = Double.MAX_VALUE;
            parent[i] = -1;
        }

        int sourceIndex = indexMap.get(source);
        distance[sourceIndex] = 0;

        for (int step = 0; step < nodes.size(); step++) {
            int currentIndex = getMinimumUnvisitedIndex(distance, visited);
            if (currentIndex == -1) {
                break;
            }
            visited[currentIndex] = true;

            String currentNode = nodes.get(currentIndex);
            ArrayList<Edge> neighbors = graph.get(currentNode);
            for (Edge edge : neighbors) {
                int neighborIndex = indexMap.get(edge.destination);
                if (!visited[neighborIndex] && distance[currentIndex] + edge.distance < distance[neighborIndex]) {
                    distance[neighborIndex] = distance[currentIndex] + edge.distance;
                    parent[neighborIndex] = currentIndex;
                }
            }
        }

        int destinationIndex = indexMap.get(destination);
        if (distance[destinationIndex] == Double.MAX_VALUE) {
            return new RouteResult(false, 0, new String[0], "No route found.");
        }

        String[] path = buildPathArray(nodes, parent, destinationIndex);
        return new RouteResult(true, distance[destinationIndex], path, "Shortest weighted route found using Dijkstra.");
    }

    public RouteResult findPathBFS(String source, String destination) {
        if (!graph.containsKey(source) || !graph.containsKey(destination)) {
            return new RouteResult(false, 0, new String[0], "Source or destination does not exist in graph.");
        }

        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
        HashMap<String, String> parent = new HashMap<String, String>();
        Queue<String> queue = new LinkedList<String>();

        for (String node : graph.keySet()) {
            visited.put(node, false);
            parent.put(node, null);
        }

        visited.put(source, true);
        queue.add(source);

        while (!queue.isEmpty()) {
            String current = queue.remove();
            if (current.equals(destination)) {
                break;
            }
            for (Edge edge : graph.get(current)) {
                String neighbor = edge.destination;
                if (!visited.get(neighbor)) {
                    visited.put(neighbor, true);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        if (!visited.get(destination)) {
            return new RouteResult(false, 0, new String[0], "No path found by BFS.");
        }

        ArrayList<String> pathList = new ArrayList<String>();
        String current = destination;
        while (current != null) {
            pathList.add(0, current);
            current = parent.get(current);
        }

        String[] path = new String[pathList.size()];
        for (int i = 0; i < pathList.size(); i++) {
            path[i] = pathList.get(i);
        }
        return new RouteResult(true, path.length - 1, path, "Route found using BFS by minimum number of edges.");
    }

    private int getMinimumUnvisitedIndex(double[] distance, boolean[] visited) {
        double min = Double.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < distance.length; i++) {
            if (!visited[i] && distance[i] < min) {
                min = distance[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private String[] buildPathArray(ArrayList<String> nodes, int[] parent, int destinationIndex) {
        ArrayList<String> reverse = new ArrayList<String>();
        int current = destinationIndex;
        while (current != -1) {
            reverse.add(0, nodes.get(current));
            current = parent[current];
        }
        String[] path = new String[reverse.size()];
        for (int i = 0; i < reverse.size(); i++) {
            path[i] = reverse.get(i);
        }
        return path;
    }

    public String[] getLocationsArray() {
        String[] locations = new String[graph.size()];
        int index = 0;
        for (String location : graph.keySet()) {
            locations[index] = location;
            index++;
        }
        return locations;
    }

    public String displayGraph() {
        StringBuilder builder = new StringBuilder();
        builder.append("Graph Adjacency List:\n");
        for (Map.Entry<String, ArrayList<Edge>> entry : graph.entrySet()) {
            builder.append(entry.getKey()).append(" -> ");
            ArrayList<Edge> edges = entry.getValue();
            for (int i = 0; i < edges.size(); i++) {
                builder.append(edges.get(i).destination).append("(").append(edges.get(i).distance).append(" km)");
                if (i < edges.size() - 1) {
                    builder.append(", ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private static class Edge {
        String destination;
        double distance;

        Edge(String destination, double distance) {
            this.destination = destination;
            this.distance = distance;
        }
    }

    public static class RouteResult {
        private boolean found;
        private double distance;
        private String[] path;
        private String message;

        public RouteResult(boolean found, double distance, String[] path, String message) {
            this.found = found;
            this.distance = distance;
            this.path = path;
            this.message = message;
        }

        public boolean isFound() {
            return found;
        }

        public double getDistance() {
            return distance;
        }

        public String[] getPath() {
            return path;
        }

        public String getMessage() {
            return message;
        }

        public String pathToString() {
            if (path.length == 0) {
                return "No path";
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < path.length; i++) {
                builder.append(path[i]);
                if (i < path.length - 1) {
                    builder.append(" -> ");
                }
            }
            return builder.toString();
        }
    }
}
