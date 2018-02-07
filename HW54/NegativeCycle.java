package HW54;

import java.util.*;

/**
 * Program to detect a negative weight cycle in a graph.
 *
 * @author Yash Jain (yj8359)    ----  Section - 05
 *         Soni Pandey (sp4547)  ----  Section - 03
 */
public class NegativeCycle {
    static Map<Integer, ArrayList<Node>> graph = new HashMap<Integer, ArrayList<Node>>();
    static int start;
    static int numberOfVertices;
    static int numberOfEdges;
    static Set<Integer> vertices = new HashSet<Integer>();
    static int[] minDistanceToNodei;
    static int[] parent;
    static int negStart;
    static int negEnd;
    static int negWeight;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        numberOfVertices = scanner.nextInt();
        numberOfEdges = scanner.nextInt();

        minDistanceToNodei = new int[numberOfVertices];
        parent = new int[numberOfVertices];

        //For the initialization of graph
        inti();

        for (int i = 0; i < numberOfEdges; i++) {
            int vertex1 = scanner.nextInt();
            int vertex2 = scanner.nextInt();
            int weight = scanner.nextInt();

            //Building the adjacency list
            if (graph.get(vertex1) != null) {
                ArrayList<Node> temp = graph.get(vertex1);
                Node node = new Node(vertex2, weight);
                temp.add(node);
                graph.put(vertex1, temp);
            } else {
                ArrayList<Node> temp = new ArrayList<Node>();
                Node node = new Node(vertex2, weight);
                temp.add(node);
                graph.put(vertex1, temp);
            }

            //storing the negative weight edge
            if (weight < 0) {
                negStart = vertex1;
                negEnd = vertex2;
                negWeight = weight;
            }
        }

        //finding the shortest path
        shortestPathToConnectAllVertices();
        //check if it contains a negative cycle
        checkIfNegativeCycle();
    }

    /**
     * Method to check if negative cycle is present in a graph
     */
    private static void checkIfNegativeCycle() {
        if (minDistanceToNodei[negStart] + negWeight < 0) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    /**
     * Method for initializing the graph, distance array and parent array of vertices.
     */
    private static void inti() {
        for (int i = 0; i < numberOfVertices; i++) {
            minDistanceToNodei[i] = 999999;
            parent[i] = -1;
            vertices.add(i);
            graph.put(i, null);
        }
    }

    /**
     * Method to find the shortest path from source to all vertices.
     */
    private static void shortestPathToConnectAllVertices() {
        //start with the end of negative edge
        int start = negEnd;
        //intialize its parent to -1 and distance to 0
        parent[start] = -1;
        minDistanceToNodei[start] = 0;

        //update its neighbour
        update(start);
        for (int i = 1; i < numberOfVertices; i++) {
            int nextNode = extractSmallestWeight();
            if (nextNode >= 0) {
                update(nextNode);
            } else {
                return;
            }
        }
    }


    /**
     * Method to find the smallest weight node among remaining nodes.
     *
     * @return index of smallest weight node
     */
    private static int extractSmallestWeight() {
        int min = 99999;
        int minIndex = -1;
        for (int i = 0; i < minDistanceToNodei.length; i++) {
            if (min > minDistanceToNodei[i] && vertices.contains(i)) {
                min = minDistanceToNodei[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    /**
     * Method for updating the distance of a node to its neighbours.
     *
     * @param u node to be updated
     */
    private static void update(int u) {
        ArrayList<Node> temp = graph.get(u);
        if (temp != null) {
            for (Node n : temp) {
                int v = n.vertex;
                int w = n.weight;

                if (vertices.contains(v)) {
                    //For a edge (u,v), if distance to reach v is greater than sum of distance to reach u
                    //and weight of edge(u,v) then update the distance to reach v and its parent
                    if (minDistanceToNodei[v] > minDistanceToNodei[u] + w && u != v) {
                        minDistanceToNodei[v] = minDistanceToNodei[u] + w;
                        parent[v] = u;
                    }
                }
            }
        }
        //removing the visited node
        removeFromVertexSet(u);
    }

    /**
     * Method for removing the vertex once they are visited.
     *
     * @param u node to be removed
     */
    private static void removeFromVertexSet(int u) {
        vertices.remove(u);
    }
}

/**
 * Node class to store the vertex and weight of each edge
 */
class Node {
    int vertex;
    int weight;

    public Node(int vertex, int weight) {
        this.weight = weight;
        this.vertex = vertex;
    }
}
