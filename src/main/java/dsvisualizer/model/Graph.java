package dsvisualizer.model;

import java.util.*;

/**
 * Graph data structure using adjacency list.
 */
public class Graph {
    private Map<Integer, List<Integer>> adjacencyList;
    private boolean isDirected;
    private int vertexCount;

    public Graph(boolean isDirected) {
        this.adjacencyList = new HashMap<>();
        this.isDirected = isDirected;
        this.vertexCount = 0;
    }

    /**
     * Add vertex to graph.
     */
    public void addVertex(int vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new ArrayList<>());
            vertexCount++;
        }
    }

    /**
     * Add edge between two vertices.
     */
    public void addEdge(int from, int to) {
        if (!adjacencyList.containsKey(from)) {
            addVertex(from);
        }
        if (!adjacencyList.containsKey(to)) {
            addVertex(to);
        }
        
        if (!adjacencyList.get(from).contains(to)) {
            adjacencyList.get(from).add(to);
        }
        
        if (!isDirected && !adjacencyList.get(to).contains(from)) {
            adjacencyList.get(to).add(from);
        }
    }

    /**
     * Remove vertex from graph.
     */
    public void removeVertex(int vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            return;
        }
        
        for (List<Integer> neighbors : adjacencyList.values()) {
            neighbors.remove(Integer.valueOf(vertex));
        }
        
        adjacencyList.remove(vertex);
        vertexCount--;
    }

    /**
     * Remove edge between two vertices.
     */
    public void removeEdge(int from, int to) {
        if (adjacencyList.containsKey(from)) {
            adjacencyList.get(from).remove(Integer.valueOf(to));
        }
        if (!isDirected && adjacencyList.containsKey(to)) {
            adjacencyList.get(to).remove(Integer.valueOf(from));
        }
    }

    /**
     * Breadth-First Search from a starting vertex.
     */
    public List<Integer> bfs(int start) {
        List<Integer> result = new ArrayList<>();
        if (!adjacencyList.containsKey(start)) {
            return result;
        }
        
        Set<Integer> visited = new HashSet<>();
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();
        
        queue.add(start);
        visited.add(start);
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            result.add(vertex);
            
            for (int neighbor : adjacencyList.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        
        return result;
    }

    /**
     * Depth-First Search from a starting vertex.
     */
    public List<Integer> dfs(int start) {
        List<Integer> result = new ArrayList<>();
        if (!adjacencyList.containsKey(start)) {
            return result;
        }
        
        Set<Integer> visited = new HashSet<>();
        dfsRecursive(start, visited, result);
        
        return result;
    }

    /**
     * Recursive DFS helper.
     */
    private void dfsRecursive(int vertex, Set<Integer> visited, List<Integer> result) {
        visited.add(vertex);
        result.add(vertex);
        
        for (int neighbor : adjacencyList.get(vertex)) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited, result);
            }
        }
    }

    /**
     * Get all vertices.
     */
    public Set<Integer> getVertices() {
        return new HashSet<>(adjacencyList.keySet());
    }

    /**
     * Get neighbors of a vertex.
     */
    public List<Integer> getNeighbors(int vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(adjacencyList.get(vertex));
    }

    /**
     * Check if vertex exists.
     */
    public boolean hasVertex(int vertex) {
        return adjacencyList.containsKey(vertex);
    }

    /**
     * Check if edge exists.
     */
    public boolean hasEdge(int from, int to) {
        return adjacencyList.containsKey(from) && adjacencyList.get(from).contains(to);
    }

    /**
     * Get vertex count.
     */
    public int getVertexCount() {
        return vertexCount;
    }

    /**
     * Check if graph is empty.
     */
    public boolean isEmpty() {
        return vertexCount == 0;
    }

    /**
     * Clear the graph.
     */
    public void clear() {
        adjacencyList.clear();
        vertexCount = 0;
    }

    /**
     * Check if graph is directed.
     */
    public boolean isDirected() {
        return isDirected;
    }
}
