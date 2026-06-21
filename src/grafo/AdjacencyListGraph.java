package grafo;

import grafo.exceptions.EdgeNotFoundException;
//import grafo.exceptions.InvalidVertexException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraph extends AbstractGraph {

    private final List<Map<Integer, Double>> adjacencyList;

    private int edgeCount;

    public AdjacencyListGraph(int numVertices) {
        super(numVertices);
        this.adjacencyList = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            adjacencyList.add(new HashMap<>());
        }
        this.edgeCount = 0;
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public boolean hasEdge(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        return adjacencyList.get(u).containsKey(v);
    }

    @Override
    public void addEdge(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        if (u == v) {
            throw new IllegalArgumentException("Laços não são permitidos: vértice " + u + ".");
        }
        if (!adjacencyList.get(u).containsKey(v)) {
            adjacencyList.get(u).put(v, 0.0);
            edgeCount++;
        }

    }

  
    @Override
    public void removeEdge(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        if (adjacencyList.get(u).remove(v) != null) {
            edgeCount--;
        }
    }


    @Override
    public int getVertexInDegree(int u) {
        validateVertex(u);
        int degree = 0;
        for (int i = 0; i < numVertices; i++) {
            if (i != u && adjacencyList.get(i).containsKey(u)) {
                degree++;
            }
        }
        return degree;
    }

    @Override
    public int getVertexOutDegree(int u) {
        validateVertex(u);
        return adjacencyList.get(u).size();
    }

 
    @Override
    public void setEdgeWeight(int u, int v, double w) {
        validateVertex(u);
        validateVertex(v);
        if (!adjacencyList.get(u).containsKey(v)) throw new EdgeNotFoundException(u, v);
        adjacencyList.get(u).put(v, w);
    }


    @Override
    public double getEdgeWeight(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        Double w = adjacencyList.get(u).get(v);
        if (w == null) throw new EdgeNotFoundException(u, v);
        return w;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdjacencyListGraph [").append(numVertices).append(" vértices, ")
          .append(edgeCount).append(" arestas]\n");
        for (int u = 0; u < numVertices; u++) {
            sb.append(String.format("  V%d → ", u));
            Map<Integer, Double> neighbors = adjacencyList.get(u);
            if (neighbors.isEmpty()) {
                sb.append("(sem vizinhos)");
            } else {
                neighbors.forEach((v, w) -> sb.append(String.format("V%d(%.2f)  ", v, w)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
