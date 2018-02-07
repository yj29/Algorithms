package hw61;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Program to count the number of useless edges (if not used int shortest path from any node to any other node)
 * in a graph.
 *
 * @author Yash Jain (yj8359)    ----  Section - 05
 *         Soni Pandey (sp4547)  ----  Section - 03
 */

@SuppressWarnings("unchecked")

public class CountUseless {
    static int numberOfVertices;
    static int numberOfEdges;
    static ArrayList<Node> graph = new ArrayList<Node>();
    static float S[][][];
    static Set<Edge>[][] s;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        numberOfVertices = scanner.nextInt();
        numberOfEdges = scanner.nextInt();
        S = new float[numberOfVertices][numberOfVertices][numberOfVertices + 1];
        s = new HashSet[numberOfVertices][numberOfVertices];

        init();

        for (int i = 0; i < numberOfEdges; i++) {
            int vertex1 = scanner.nextInt();
            int vertex2 = scanner.nextInt();
            float weight = scanner.nextFloat();
            if (graph.get(vertex1) == null) {
                Node node = new Node(vertex2, weight);
                graph.add(vertex1, node);
            } else {
                Node last = returnLastNode(vertex1);
                Node node = new Node(vertex2, weight);
                last.next = node;
            }
        }

        findUselessNodes();
        int ans = count();
        System.out.println(ans);
    }

    /**
     * Method for counting the number of useless edge
     *
     *@return   int     number of useless edges
     */
    private static int count() {
        int count = 0;
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                Set<Edge> temp = s[i][j];
                if (temp != null) {
                    mark(temp);
                }
            }
        }

        for (int i = 0; i < numberOfVertices; i++) {
            Node node = graph.get(i);
            while (node != null) {
                if (node.isUsed) {
                    count++;
                }
                node = node.next;
            }
        }
        return (numberOfEdges - count);
    }

    /**
     * Method to check if edge is already visited.
     *
     * @param temp     Set of type Edge
     */
    private static void mark(Set<Edge> temp) {
        for (Edge e : temp) {
            int v1 = e.v1;
            int v2 = e.v2;
            float w = e.w;
            Node t = graph.get(v1);
            while (t != null) {
                if (t.vertex == v2 && t.weight == w) {
                    t.isUsed = true;
                    break;
                }
                t = t.next;
            }
        }
    }

    /**
     * Method for finding the useless edges(edge which are not present in the shortest path)
     */
    private static void findUselessNodes() {
        initForKZero();
        for (int k = 1; k <= numberOfVertices; k++) {
            for (int i = 0; i < numberOfVertices; i++) {
                for (int j = 0; j < numberOfVertices; j++) {
                    float a = S[i][j][k - 1];
                    float b = S[i][k - 1][k - 1];
                    float c = S[k - 1][j][k - 1];
                    float d = b + c;
                    float t = Math.min(a, d);
                    if (d < a) {
                        Set<Edge> set = new HashSet<Edge>();
                        set.addAll(s[i][k - 1]);
                        set.addAll(s[k - 1][j]);
                        s[i][j] = set;
                    }
                    S[i][j][k] = t;
                }
            }
        }
    }

    /**
     * Method to handle the base cases. All diagonal elements are zero.
     */
    private static void initForKZero() {
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                float w = weightBetweenij(i, j);
                S[i][j][0] = w;
            }
        }
    }

    /**
     * Method for calculating the weight between two vertex
     *
     * @param i     first vertex
     * @param j     second vertex
     *
     * @return      weight of an edge
     */
    private static float weightBetweenij(int i, int j) {
        if (i == j) {
            return 0;
        }
        Node node = graph.get(i);
        if (node != null) {
            while (node != null) {
                if (node.vertex == j) {
                    Edge e = new Edge(i, j, node.weight);
                    Set<Edge> set = new HashSet<Edge>();
                    set.add(e);
                    s[i][j] = set;
                    return node.weight;
                }
                node = node.next;
            }
        }
        return 999999;
    }

    /**
     * Helper function for creating the graph
     *
     * @param vertex1   int
     *
     * @return  Node object
     */
    private static Node returnLastNode(int vertex1) {
        Node start = graph.get(vertex1);
        while (start.next != null) {
            start = start.next;
        }
        return start;
    }

    /**
     * init method for initializing the graph and 2D matrix
     */
    private static void init() {
        for (int i = 0; i < numberOfVertices; i++) {
            graph.add(i, null);
        }

        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                s[i][j] = null;
            }
        }
    }
}

/**
 * Node class for creating the adjacency list
 */
class Node {
    int vertex;
    float weight;
    Node next;
    boolean isUsed;

    public Node(int vertex, float weight) {
        this.vertex = vertex;
        this.weight = weight;
        this.next = null;
    }
}

/**
 * Edge class to store the edge details(start vertex, end vertex and also weight)
 */
class Edge {
    int v1, v2;
    float w;

    public Edge(int v1, int v2, float w) {
        this.v1 = v1;
        this.v2 = v2;
        this.w = w;
    }
}
