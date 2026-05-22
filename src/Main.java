public class Main {
    public static void main(String[] args) {
        //  SMALL GRAPH (10 vertices) — BFS & DFS
        System.out.println("   SMALL GRAPH (10 vertices)          ");

        Graph small = new Graph();
        for (int i = 0; i < 10; i++) {
            small.addVertex(new Vertex(i));
        }
        small.addEdge(0, 1);
        small.addEdge(0, 2);
        small.addEdge(1, 3);
        small.addEdge(1, 4);
        small.addEdge(2, 5);
        small.addEdge(3, 6);
        small.addEdge(4, 7);
        small.addEdge(5, 8);
        small.addEdge(6, 9);

        small.printGraph();
        System.out.println();

        Experiment exp = new Experiment();
        exp.runTraversals(small, 0);

        //  PERFORMANCE COMPARISON (10, 30, 100)
        System.out.println("   PERFORMANCE COMPARISON             ");
        exp.runMultipleTests();


        //  BONUS: Dijkstra's Algorithm Demo
        System.out.println("   BONUS: Dijkstra Shortest Path      ");
        Graph dijkstraGraph = new Graph();
        for (int i = 0; i < 9; i++) {
            dijkstraGraph.addVertex(new Vertex(i));
        }

        dijkstraGraph.addWeightedEdge(0, 1, 4);
        dijkstraGraph.addWeightedEdge(0, 7, 8);
        dijkstraGraph.addWeightedEdge(1, 2, 8);
        dijkstraGraph.addWeightedEdge(1, 7, 11);
        dijkstraGraph.addWeightedEdge(2, 3, 7);
        dijkstraGraph.addWeightedEdge(2, 5, 4);
        dijkstraGraph.addWeightedEdge(2, 8, 2);
        dijkstraGraph.addWeightedEdge(3, 4, 9);
        dijkstraGraph.addWeightedEdge(3, 5, 14);
        dijkstraGraph.addWeightedEdge(4, 5, 10);
        dijkstraGraph.addWeightedEdge(5, 6, 2);
        dijkstraGraph.addWeightedEdge(6, 7, 1);
        dijkstraGraph.addWeightedEdge(6, 8, 6);
        dijkstraGraph.addWeightedEdge(7, 8, 7);

        System.out.println();
        dijkstraGraph.printWeightedGraph();
        System.out.println();
        dijkstraGraph.dijkstra(0);
    }
}