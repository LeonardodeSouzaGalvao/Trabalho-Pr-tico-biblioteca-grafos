package grafo;

import grafo.exceptions.EdgeNotFoundException;
//import grafo.exceptions.InvalidVertexException;

public class AdjacencyMatrixGraph extends AbstractGraph {

    private final boolean[][] adjacency;

    private final double[][] edgeWeights;

    private int edgeCount;

    public AdjacencyMatrixGraph(int numVertices) {
        super(numVertices);
        this.adjacency   = new boolean[numVertices][numVertices];
        this.edgeWeights = new double[numVertices][numVertices];
        this.edgeCount   = 0;
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public boolean hasEdge(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        return adjacency[u][v];
    }


    @Override
    public void addEdge(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        if (u == v) {
            throw new IllegalArgumentException("Laços não são permitidos: vértice " + u + ".");
        }
        if (!adjacency[u][v]) {
            adjacency[u][v] = true;
            edgeWeights[u][v] = 0.0;
            edgeCount++;
        }

    }

    @Override
    public void removeEdge(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        if (adjacency[u][v]) {
            adjacency[u][v] = false;
            edgeWeights[u][v] = 0.0;
            edgeCount--;
        }
    }

    @Override
    public int getVertexInDegree(int u) {
        validateVertex(u);
        int degree = 0;
        for (int i = 0; i < numVertices; i++) {
            if (adjacency[i][u]) degree++;
        }
        return degree;
    }


    @Override
    public int getVertexOutDegree(int u) {
        validateVertex(u);
        int degree = 0;
        for (int j = 0; j < numVertices; j++) {
            if (adjacency[u][j]) degree++;
        }
        return degree;
    }


    @Override
    public void setEdgeWeight(int u, int v, double w) {
        validateVertex(u);
        validateVertex(v);
        if (!adjacency[u][v]) throw new EdgeNotFoundException(u, v);
        edgeWeights[u][v] = w;
    }


    @Override
    public double getEdgeWeight(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        if (!adjacency[u][v]) throw new EdgeNotFoundException(u, v);
        return edgeWeights[u][v];
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdjacencyMatrixGraph [").append(numVertices).append(" vértices, ")
          .append(edgeCount).append(" arestas]\n");
        sb.append("   ");
        for (int j = 0; j < numVertices; j++) sb.append(String.format("%4d", j));
        sb.append("\n");
        for (int i = 0; i < numVertices; i++) {
            sb.append(String.format("%3d", i));
            for (int j = 0; j < numVertices; j++) {
                sb.append(adjacency[i][j] ? "   1" : "   0");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
