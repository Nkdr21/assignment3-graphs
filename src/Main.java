public class Main {
    public static void main(String[] args) {

        // SMALL GRAPH (10 vertices)
        System.out.println("  SMALL GRAPH (10 vertices)  ");
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

        // PERFORMANCE COMPARISON (10, 30, 100)
        System.out.println("\n  PERFORMANCE COMPARISON  ");
        exp.runMultipleTests();
    }
}
