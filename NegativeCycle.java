
import java.util.*;

/**
 * Created by YASH on 4/23/16.
 */
public class NegativeCycle {
    static Map<Integer, ArrayList<Node>> graph = new HashMap<Integer, ArrayList<Node>>();
    static int start;
    static int numberOfSpies;
    static int numberOfEdges;
    static Set<Integer> spies = new HashSet<Integer>();
    static int[] minDistanceToNodei;
    static int[] parent;
    static int distance = 0;
    static boolean visited[];
    static boolean res = false;
    static int negStart;
    static int negEnd;
    static int negWeight;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        numberOfSpies = scanner.nextInt();
        numberOfEdges = scanner.nextInt();

        minDistanceToNodei = new int[numberOfSpies];
        parent = new int[numberOfSpies];
        visited = new boolean[numberOfSpies];

        inti();

        for (int i = 0; i < numberOfEdges; i++) {
            int vertex1 = scanner.nextInt();
            int vertex2 = scanner.nextInt();
            int weight = scanner.nextInt();
            if (weight < 0) {
                negStart = vertex1;
                negEnd = vertex2;
                negWeight = weight;
            }

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
        }

        shortestPathToConnectAllSpies();
        checkIfNegativeCycle();
    }

    private static void checkIfNegativeCycle() {
        if (minDistanceToNodei[negStart] + negWeight < 0) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    private static void inti() {
        for (int i = 0; i < numberOfSpies; i++) {
            minDistanceToNodei[i] = 999999;
            parent[i] = -1;
            spies.add(i);
            visited[i] = false;
            graph.put(i, null);
        }
    }

    private static void shortestPathToConnectAllSpies() {
        int start = negEnd;
        parent[start] = -1;
        minDistanceToNodei[start] = 0;
        update(start);
        for (int i = 1; i < numberOfSpies; i++) {
            int u = extractSmallestWeight();
            if (u >= 0) {
                update(u);
            } else {
                return;
            }
        }

    }


    private static int extractSmallestWeight() {
        int min = 99999;
        int minIndex = -1;
        for (int i = 0; i < minDistanceToNodei.length; i++) {
            if (min > minDistanceToNodei[i] && spies.contains(i)) {
                min = minDistanceToNodei[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private static void update(int u) {
        ArrayList<Node> temp = graph.get(u);
        if (temp != null) {
            for (Node n : temp) {
                int v = n.vertex;
                int w = n.weight;

                if (spies.contains(v)) {
                    if (minDistanceToNodei[v] > minDistanceToNodei[u] + w && u != v) {
                        minDistanceToNodei[v] = minDistanceToNodei[u] + w;
                        parent[v] = u;
                    }
                } /*else {
                    if (minDistanceToNodei[v] > minDistanceToNodei[u] + w && u != v) {
                        minDistanceToNodei[v] = minDistanceToNodei[u] + w;
                        parent[v] = u;
                        if (v != negStart) {
                            res = true;
                            return;
                        }
                    }
                }*/
            }
        }
        removeFromSpiesSet(u);
    }


    private static void removeFromSpiesSet(int u) {
        spies.remove(u);
       /* for (int i = 0; i < spies.size(); i++) {
            if (spies.get(i) == u) {
                spies.remove(i);
                return;
            }
        }*/
    }
}


class Node {
    int vertex;
    int weight;

    public Node(int vertex, int weight) {
        this.weight = weight;
        this.vertex = vertex;
    }
}
