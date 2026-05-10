public class Experiment {

    // Run BFS and DFS on a given graph and measure execution time
    public void runTraversals(Graph g, int startVertex) {
        long bfsStart = System.nanoTime();
        g.bfs(startVertex);
        long bfsEnd = System.nanoTime();
        System.out.println("  BFS time: " + (bfsEnd - bfsStart) + " ns");

        long dfsStart = System.nanoTime();
        g.dfs(startVertex);
        long dfsEnd = System.nanoTime();
        System.out.println("  DFS time: " + (dfsEnd - dfsStart) + " ns");
    }

    // Test on graphs of size 10, 30, 100
    public void runMultipleTests() {
        int[] sizes = {10, 30, 100};

        for (int size : sizes) {
            System.out.println("\n Graph size: " + size + " vertices ");
            Graph g = buildGraph(size);

            long bfsStart = System.nanoTime();
            g.bfs(0);
            long bfsEnd = System.nanoTime();

            long dfsStart = System.nanoTime();
            g.dfs(0);
            long dfsEnd = System.nanoTime();

            printResults(size, bfsEnd - bfsStart, dfsEnd - dfsStart);
        }
    }

    // Build a simple connected graph with given number of vertices
    public Graph buildGraph(int size) {
        Graph g = new Graph();

        for (int i = 0; i < size; i++) {
            g.addVertex(new Vertex(i));
        }

        for (int i = 0; i < size - 1; i++) {
            g.addEdge(i, i + 1);
            if (i + 2 < size) {
                g.addEdge(i, i + 2);
            }
        }

        return g;
    }

    // Print timing results
    public void printResults(int size, long bfsTime, long dfsTime) {
        System.out.println("  Results for " + size + " vertices:");
        System.out.println("    BFS execution time: " + bfsTime + " ns");
        System.out.println("    DFS execution time: " + dfsTime + " ns");
    }
}

