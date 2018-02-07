package hw52;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Program to find the length of the longest path in a given directed graph
 *
 * @author Yash Jain (yj8359)    ----  Section - 05
 *         Soni Pandey (sp4547)  ----  Section - 03
 */
public class LongestPathDAG {
    static Map<Integer, ArrayList<Integer>> graph = new HashMap<Integer, ArrayList<Integer>>();
    static Map<Integer, ArrayList<Integer>> reachabbility = new HashMap<Integer, ArrayList<Integer>>();
    static boolean visited[];
    static int finish[];
    static int finishCount = 1;
    static int numberOfVertices;
    static int numberOfEdges;
    static int topologicalOrder[];
    static Map<Integer, Integer> finishOrder = new HashMap<Integer, Integer>();
    static int[] out;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        numberOfVertices = scanner.nextInt();
        numberOfEdges = scanner.nextInt();
        visited = new boolean[numberOfVertices];
        finish = new int[numberOfVertices];
        topologicalOrder = new int[numberOfVertices];
        out = new int[numberOfVertices];

        initialize(numberOfVertices);

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

            if (reachabbility.get(vertex2) != null) {
                ArrayList<Integer> temp = reachabbility.get(vertex2);
                temp.add(vertex1);
                reachabbility.put(vertex2, temp);
            } else {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                temp.add(vertex1);
                reachabbility.put(vertex2, temp);
            }
        }

        for (int i = 0; i < numberOfVertices; i++) {
            perFormDFSToGetFinishOrder(i);
        }

        int maxCount = numberOfVertices;
        findVertexInReverseByFinishTime(maxCount);

        getLongestPath();
        System.out.println(out[numberOfVertices - 1]);
    }

    /**
     * Method to find the longest path in a graph
     */
    private static void getLongestPath() {
        // out[0] = 0;
        for (int i = 0; i < numberOfVertices; i++) {
            int vertex = topologicalOrder[i];
            if (reachabbility.get(vertex) == null) {
                out[i] = 0;
            } else {
                int max = 0;
                ArrayList<Integer> reach = reachabbility.get(topologicalOrder[i]);
                for (Integer j : reach) {
                    int indexOfj = indexOfJ(j);
                    int temp = out[indexOfj] + 1;
                    if (temp > max) {
                        max = temp;
                    }
                }
                out[i] = max;
            }
        }
    }

    /**
     * Method to return the index of vertex j.
     *
     * @param j
     * @return
     */
    private static int indexOfJ(int j) {
        for (int i = 0; i < numberOfVertices; i++) {
            if (topologicalOrder[i] == j) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Method to reverse the finish time order to get the topological order
     * @param maxCount
     */
    private static void findVertexInReverseByFinishTime(int maxCount) {
        for (Map.Entry<Integer, Integer> entry : finishOrder.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            topologicalOrder[maxCount - (value)] = key;
        }
    }

    /**
     * Method to find the finish order of vertices by using the recursive DFS method.
     *
     * @param start source vertex
     */
    private static void perFormDFSToGetFinishOrder(int start) {
        if (!visited[start]) {
            visited[start] = true;
            if (graph.get(start) != null) {
                ArrayList<Integer> adjacencyList = graph.get(start);
                for (Integer j : adjacencyList) {
                    perFormDFSToGetFinishOrder(j);
                }
                finish[start] = finishCount++;
                finishOrder.put(start, finish[start]);
            } else {
                finish[start] = finishCount++;
                finishOrder.put(start, finish[start]);
                return;
            }
        }
    }

    /**
     * Method for initializing the graph
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
