import java.util.*;

public class Graph {
    // Adjacency list for unweighted traversal (BFS/DFS)
    private Map<Integer, List<Integer>> adjList;

    // Weighted adjacency list for Dijkstra: vertex -> list of [neighbor, weight]
    private Map<Integer, List<int[]>> weightedAdjList;

    private Map<Integer, Vertex> vertices;

    public Graph() {
        adjList = new HashMap<>();
        weightedAdjList = new HashMap<>();
        vertices = new HashMap<>();
    }

    // Add a vertex to the graph
    public void addVertex(Vertex v) {
        vertices.put(v.getId(), v);
        adjList.putIfAbsent(v.getId(), new ArrayList<>());
        weightedAdjList.putIfAbsent(v.getId(), new ArrayList<>());
    }

    // Add an undirected unweighted edge (used by BFS/DFS and Experiment)
    public void addEdge(int from, int to) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            System.out.println("One or both vertices not found.");
            return;
        }
        adjList.get(from).add(to);
        adjList.get(to).add(from);
    }

    // Add an undirected weighted edge (used by Dijkstra)
    public void addWeightedEdge(int from, int to, int weight) {
        if (!weightedAdjList.containsKey(from) || !weightedAdjList.containsKey(to)) {
            System.out.println("One or both vertices not found.");
            return;
        }
        // Store as [neighborId, weight]
        weightedAdjList.get(from).add(new int[]{to, weight});
        weightedAdjList.get(to).add(new int[]{from, weight});
    }

    // Print the adjacency list (unweighted)
    public void printGraph() {
        System.out.println("Graph (Adjacency List):");
        for (int id : adjList.keySet()) {
            System.out.print("  " + id + " -> ");
            System.out.println(adjList.get(id));
        }
    }

    // Print the weighted adjacency list
    public void printWeightedGraph() {
        System.out.println("Weighted Graph (Adjacency List):");
        for (int id : weightedAdjList.keySet()) {
            System.out.print("  " + id + " -> ");
            List<int[]> neighbors = weightedAdjList.get(id);
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < neighbors.size(); i++) {
                sb.append(neighbors.get(i)[0]).append("(w=").append(neighbors.get(i)[1]).append(")");
                if (i < neighbors.size() - 1) sb.append(", ");
            }
            sb.append("]");
            System.out.println(sb);
        }
    }

    // BFS traversal — uses a queue, visits level by level
    public void bfs(int start) {
        if (!adjList.containsKey(start)) {
            System.out.println("Start vertex not found.");
            return;
        }

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        System.out.print("BFS from " + start + ": ");

        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print(current + " ");

            for (int neighbor : adjList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    // DFS traversal — uses recursion, goes deep first
    public void dfs(int start) {
        if (!adjList.containsKey(start)) {
            System.out.println("Start vertex not found.");
            return;
        }

        Set<Integer> visited = new HashSet<>();
        System.out.print("DFS from " + start + ": ");
        dfsHelper(start, visited);
        System.out.println();
    }

    // Recursive helper for DFS
    private void dfsHelper(int current, Set<Integer> visited) {
        visited.add(current);
        System.out.print(current + " ");

        for (int neighbor : adjList.get(current)) {
            if (!visited.contains(neighbor)) {
                dfsHelper(neighbor, visited);
            }
        }
    }

    // -------------------------------------------------------
    // BONUS: Dijkstra's Algorithm — Shortest Path
    // -------------------------------------------------------
    // Finds the shortest distance from 'start' to all other vertices.
    // Uses arrays and simple loops (no priority queue) as allowed.
    // -------------------------------------------------------
    public void dijkstra(int start) {
        if (!weightedAdjList.containsKey(start)) {
            System.out.println("Start vertex not found.");
            return;
        }

        int n = weightedAdjList.size();

        // Map vertex ids to array indices (handles non-consecutive ids)
        List<Integer> ids = new ArrayList<>(weightedAdjList.keySet());
        Collections.sort(ids);
        Map<Integer, Integer> idToIndex = new HashMap<>();
        for (int i = 0; i < ids.size(); i++) {
            idToIndex.put(ids.get(i), i);
        }

        int startIndex = idToIndex.get(start);

        // dist[i] = shortest known distance from start to vertex with index i
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[startIndex] = 0;

        // visited[i] = true if vertex i has been finalized
        boolean[] visited = new boolean[n];

        // prev[i] = index of previous vertex on the shortest path (for path reconstruction)
        int[] prev = new int[n];
        Arrays.fill(prev, -1);

        // Relax edges n times — each iteration finalizes one vertex
        for (int step = 0; step < n; step++) {

            // Find the unvisited vertex with the smallest distance
            int u = -1;
            for (int i = 0; i < n; i++) {
                if (!visited[i] && (u == -1 || dist[i] < dist[u])) {
                    u = i;
                }
            }

            // If smallest distance is still infinity, remaining vertices are unreachable
            if (dist[u] == Integer.MAX_VALUE) break;

            visited[u] = true;
            int uId = ids.get(u);

            // Relax all neighbors of u
            for (int[] neighborInfo : weightedAdjList.get(uId)) {
                int neighborId = neighborInfo[0];
                int weight = neighborInfo[1];
                int v = idToIndex.get(neighborId);

                if (!visited[v] && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    prev[v] = u;
                }
            }
        }

        // Print results
        System.out.println("Dijkstra shortest paths from vertex " + start + ":");
        for (int i = 0; i < n; i++) {
            int targetId = ids.get(i);
            System.out.print("  To vertex " + targetId + ": ");
            if (dist[i] == Integer.MAX_VALUE) {
                System.out.print("UNREACHABLE");
            } else {
                System.out.print("distance = " + dist[i] + "  |  path = ");
                System.out.print(buildPath(prev, i, ids));
            }
            System.out.println();
        }
    }

    // Reconstructs and returns the path string from start to vertex at index 'end'
    private String buildPath(int[] prev, int end, List<Integer> ids) {
        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = prev[at]) {
            path.add(ids.get(at));
        }
        Collections.reverse(path);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i));
            if (i < path.size() - 1) sb.append(" -> ");
        }
        return sb.toString();
    }

    public Map<Integer, List<Integer>> getAdjList() {
        return adjList;
    }

    public Map<Integer, List<int[]>> getWeightedAdjList() {
        return weightedAdjList;
    }
}