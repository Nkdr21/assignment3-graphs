import java.util.*;

public class Graph {
    // adjacency list: each vertex maps to a list of neighbors
    private Map<Integer, List<Integer>> adjList;
    private Map<Integer, Vertex> vertices;

    public Graph() {
        adjList = new HashMap<>();
        vertices = new HashMap<>();
    }

    // Add a vertex to the graph
    public void addVertex(Vertex v) {
        vertices.put(v.getId(), v);
        adjList.putIfAbsent(v.getId(), new ArrayList<>());
    }

    // Add an undirected edge between two vertices
    public void addEdge(int from, int to) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            System.out.println("One or both vertices not found.");
            return;
        }
        adjList.get(from).add(to);
        adjList.get(to).add(from);
    }

    // Print the adjacency list
    public void printGraph() {
        System.out.println("Graph (Adjacency List):");
        for (int id : adjList.keySet()) {
            System.out.print("  " + id + " -> ");
            System.out.println(adjList.get(id));
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

            // Visit all unvisited neighbors
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

    public Map<Integer, List<Integer>> getAdjList() {
        return adjList;
    }
}
