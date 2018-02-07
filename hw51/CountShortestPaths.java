package hw51;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Program to find the number of shortest path from source to destination
 *
 * @author Yash Jain (yj8359)    ----  Section - 05
 *         Soni Pandey (sp4547)  ----  Section - 03
 */
public class CountShortestPaths {
    static Map<Integer, ArrayList<Integer>> graph = new HashMap<Integer, ArrayList<Integer>>();
    static boolean visited[];
    static int numberOfVertices;
    static int numberOfEdges;
    static int source, destination;
    static int depth = 0;
    static Node visitOrder[];
    static ArrayList<Integer> ans = new ArrayList<Integer>();
    static ArrayList<Integer> lastVisit = new ArrayList<Integer>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        numberOfVertices = scanner.nextInt();
        numberOfEdges = scanner.nextInt();
        source = scanner.nextInt();
        destination = scanner.nextInt();
        visitOrder = new Node[numberOfVertices];

        visited = new boolean[numberOfVertices];

        //initializing the graph
        initialize(numberOfVertices);

        //building the adjacency list
        for (int i = 0; i < numberOfEdges; i++) {
            int vertex1 = scanner.nextInt();
            int vertex2 = scanner.nextInt();
            if (graph.get(vertex1) != null) {
                ArrayList<Integer> temp = graph.get(vertex1);
                temp.add(vertex2);
                graph.put(vertex1, temp);
            } else {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                temp.add(vertex2);
                graph.put(vertex1, temp);
            }

            if (graph.get(vertex2) != null) {
                ArrayList<Integer> temp = graph.get(vertex2);
                temp.add(vertex1);
                graph.put(vertex2, temp);
            } else {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                temp.add(vertex1);
                graph.put(vertex2, temp);
            }
        }

        //finding depth
        calculateDepth(source);
        //find the number of shortest path
        int result = findAns();
        System.out.println(result);
    }

    /**
     * Function that finds number of shortest paths in from source to destination
     *
     * @return  number of shortest path
     */
    private static int findAns() {
        int min = 99999;
        int minCount = 0;
        for (int i = 0; i < ans.size(); i++) {
            if (ans.get(i) < min) {
                min = ans.get(i);
                minCount = 1;
            } else if (ans.get(i) == min) {
                minCount++;
            }
        }
        return minCount;
    }


    /**
     * Calculates depth
     *
     * @param s     Vertex
     */
    private static void calculateDepth(int s) {
        lastVisit.add(s);
        depth++;
        ArrayList<Integer> temp = graph.get(s);
        if (temp == null) {
            return;
        }
        if (temp.contains(destination)) {
            ans.add(depth);
            lastVisit.remove(lastVisit.size() - 1);
            depth--;
            return;
        } else {
            for (Integer j : temp) {
                if (!lastVisit.contains(j)) {
                    calculateDepth(j);
                }
            }
            depth--;
            lastVisit.remove(lastVisit.size() - 1);
        }
    }

    /**
     * Initialize all the parameters
     *
     * @param numberOfVertices  number of vertices
     */
    private static void initialize(int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++) {
            ArrayList<Integer> temp = new ArrayList<Integer>();
            temp.add(i);
            graph.put(i, null);
            visited[i] = false;
        }
    }
}

/**
 * Node class to store vertex details
 */
class Node {
    int vertex;
    int depth;
}
