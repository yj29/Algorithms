package hw53;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Program to find the minimum cost to connect the spies such that no message has to
 * pass through faulty nodes.
 *
 * @author Yash Jain (yj8359)    ----  Section - 05
 *         Soni Pandey (sp4547)  ----  Section - 03
 */
public class Spies {
    static Map<Integer, ArrayList<Node>> graph = new HashMap<Integer, ArrayList<Node>>();
    static int start;
    static int numberOfSpies;
    static int numberOfEdges;
    static int numberOfFalultySpies;
    static ArrayList<Integer> faultySpies = new ArrayList<Integer>();
    static ArrayList<Integer> spies = new ArrayList<Integer>();
    static int[] minDistanceToNodei;
    static int[] parent;
    static int distance = 0;
    static boolean visited[];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        numberOfSpies = scanner.nextInt();
        numberOfEdges = scanner.nextInt();
        numberOfFalultySpies = scanner.nextInt();

        //storing faulty nodes
        for (int i = 0; i < numberOfFalultySpies; i++) {
            faultySpies.add(scanner.nextInt());
        }
        minDistanceToNodei = new int[numberOfSpies];
        parent = new int[numberOfSpies];
        visited = new boolean[numberOfSpies];

        for (int i = 0; i < numberOfSpies; i++) {
            graph.put(i, null);
        }

        //Initializing the graph
        inti();

        //building the graph
        for (int i = 0; i < numberOfEdges; i++) {
            int vertex1 = scanner.nextInt();
            int vertex2 = scanner.nextInt();
            int weight = scanner.nextInt();
            //Edge edge = new Edge(vertex1, vertex2, weight);
            //arbitraryEdgeOrder.add(edge);

            //edge from vertex1 to vertex2
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

            //Since the graph is undirected, store the edge from vertex2 to vertex1 also.
            if (graph.get(vertex2) != null) {
                ArrayList<Node> temp = graph.get(vertex2);
                Node node = new Node(vertex1, weight);
                temp.add(node);
                graph.put(vertex2, temp);
            } else {
                ArrayList<Node> temp = new ArrayList<Node>();
                Node node = new Node(vertex1, weight);
                temp.add(node);
                graph.put(vertex2, temp);
            }
        }

        //Finding the shortest path
        shortestPathToConnectAllSpies();
        //check if faulty nodes is present in the shortest path
        boolean faultySpyPresent = checkIfPathContainsFaultySpies();
        if (faultySpyPresent) {
            System.out.println("NONE");
        } else {
            findSum();
            System.out.println(distance);
        }
    }

    /**
     * Method for initializing the graph to null, distance array to infinity
     * parent array to null.
     */
    private static void inti() {
        for (int i = 0; i < numberOfSpies; i++) {
            graph.put(i, null);
            minDistanceToNodei[i] = 999999;
            parent[i] = -1;
            spies.add(i);
            visited[i] = false;
        }
    }

    /**
     * Method to find the sum of the shortest path
     */
    private static void findSum() {
        distance = 0;
        for (int i = 0; i < numberOfSpies; i++) {
            distance = distance + minDistanceToNodei[i];
        }
    }

    /**
     * Method to check if shortest path contains faulty nodes.
     *
     * @return  boolean
     */
    private static boolean checkIfPathContainsFaultySpies() {
        for (int i = 0; i < numberOfSpies; i++) {
            if (parent[i] == -1 && i != start) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to find the shortest path between the spies
     */
    private static void shortestPathToConnectAllSpies() {
        //Find the first non-faulty nodes
        int start = findFirstNonFaultyNode();
        Spies.start = start;
        //Initialize the parent of first non-faulty node to -1.
        parent[start] = -1;
        //Distance of start vertices set to 0
        minDistanceToNodei[start] = 0;
        //Update the neighbours of starting vertex
        update(start);
        //Find the minimum weight non-faulty node among the remaining nodes
        for (int i = 1; i < numberOfSpies; i++) {
            int u = extractSmallestWeight();
            if (u >= 0) {
                update(u);
            } else {
                return;
            }
        }

    }

    /**
     * Method to find the first non-faulty node in the graph
     *
     * @return  index of non-faulty node
     */
    private static int findFirstNonFaultyNode() {
        for (int i = 0; i < numberOfSpies; i++) {
            if (!faultySpies.contains(i)) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Method to find the minimum smallest weight non-faulty node
     *
     * @return  index of non-faulty node
     */
    private static int extractSmallestWeight() {
        int min = 99999;
        int minIndex = -1;
        for (int i = 0; i < minDistanceToNodei.length; i++) {
            if (min > minDistanceToNodei[i] && spies.contains(i) && !faultySpies.contains(i)) {
                min = minDistanceToNodei[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    /**
     * Method for updating the neighbours.
     *
     * @param u     node which we have to update
     */
    private static void update(int u) {
        ArrayList<Node> temp = graph.get(u);
        if (temp != null) {
            for (Node n : temp) {
                int v = n.vertex;
                int w = n.weight;

                if (spies.contains(v)) {
                    if (minDistanceToNodei[v] > w && u != v) {
                        minDistanceToNodei[v] = w;
                        parent[v] = u;
                        // distance = distance + w;
                    }
                }
            }
        }
        removeFromSpiesSet(u);
    }

    /**
     * Method to remove node from the arraylist when the node has been
     * marked visited.
     *
     * @param u     node to be deleted
     */
    private static void removeFromSpiesSet(int u) {
        for (int i = 0; i < spies.size(); i++) {
            if (spies.get(i) == u) {
                spies.remove(i);
                return;
            }
        }
    }

    /**
     * Method for initializing the graph.
     *
     * @param numberOfVertices  int
     */
    private static void initialize(int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++) {
            ArrayList<Integer> temp = new ArrayList<Integer>();
            temp.add(i);
            graph.put(i, null);
        }
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
