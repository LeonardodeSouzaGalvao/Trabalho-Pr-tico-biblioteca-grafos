package grafo;

import grafo.exceptions.InvalidVertexException;
import grafo.exceptions.EdgeNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Queue;

public abstract class AbstractGraph {

    protected final int numVertices;
    protected final double[] vertexWeights;

    protected AbstractGraph(int numVertices) {
        if (numVertices <= 0) {
            throw new IllegalArgumentException("O número de vértices deve ser positivo.");
        }
        this.numVertices = numVertices;
        this.vertexWeights = new double[numVertices];
    }

    protected void validateVertex(int v) {
        if (v < 0 || v >= numVertices) {
            throw new InvalidVertexException(v, numVertices);
        }
    }

    public abstract int getEdgeCount();

    public abstract boolean hasEdge(int u, int v);

    public abstract void addEdge(int u, int v);

    public abstract void removeEdge(int u, int v);

    public abstract int getVertexInDegree(int u);

    public abstract int getVertexOutDegree(int u);

    public abstract void setEdgeWeight(int u, int v, double w);

    public abstract double getEdgeWeight(int u, int v);

    public int getVertexCount() {
        return numVertices;
    }

    public void setVertexWeight(int v, double w) {
        validateVertex(v);
        vertexWeights[v] = w;
    }

    public double getVertexWeight(int v) {
        validateVertex(v);
        return vertexWeights[v];
    }

    public boolean isSuccessor(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        return hasEdge(u, v);
    }

    public boolean isPredecessor(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        return hasEdge(u, v);
    }

    public boolean isDivergent(int u1, int v1, int u2, int v2) {
        validateVertex(u1); validateVertex(v1);
        validateVertex(u2); validateVertex(v2);
        if (!hasEdge(u1, v1)) throw new EdgeNotFoundException(u1, v1);
        if (!hasEdge(u2, v2)) throw new EdgeNotFoundException(u2, v2);
        return u1 == u2;
    }

    public boolean isConvergent(int u1, int v1, int u2, int v2) {
        validateVertex(u1); validateVertex(v1);
        validateVertex(u2); validateVertex(v2);
        if (!hasEdge(u1, v1)) throw new EdgeNotFoundException(u1, v1);
        if (!hasEdge(u2, v2)) throw new EdgeNotFoundException(u2, v2);
        return v1 == v2;
    }

    public boolean isIncident(int u, int v, int x) {
        validateVertex(u); validateVertex(v); validateVertex(x);
        if (!hasEdge(u, v)) throw new EdgeNotFoundException(u, v);
        return x == u || x == v;
    }

    public boolean isEmptyGraph() {
        return getEdgeCount() == 0;
    }

    public boolean isCompleteGraph() {
        long maxEdges = (long) numVertices * (numVertices - 1);
        return getEdgeCount() == maxEdges;
    }

    public boolean isConnected() {
        if (numVertices <= 1) return true;

        boolean[] visited = new boolean[numVertices];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        visited[0] = true;
        int count = 1;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int neighbor = 0; neighbor < numVertices; neighbor++) {
                if (!visited[neighbor]) {
                    if (hasEdge(current, neighbor) || hasEdge(neighbor, current)) {
                        visited[neighbor] = true;
                        queue.add(neighbor);
                        count++;
                    }
                }
            }
        }
        return count == numVertices;
    }

    public void exportToGEPHI(String path) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<gexf xmlns=\"http://gexf.net/1.3\"");
            pw.println("      xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
            pw.println("      xsi:schemaLocation=\"http://gexf.net/1.3 http://gexf.net/1.3/gexf.xsd\"");
            pw.println("      version=\"1.3\">");
            pw.println("  <graph defaultedgetype=\"directed\">");

            pw.println("    <attributes class=\"node\">");
            pw.println("      <attribute id=\"0\" title=\"weight\" type=\"double\"/>");
            pw.println("    </attributes>");

            pw.println("    <attributes class=\"edge\">");
            pw.println("      <attribute id=\"0\" title=\"weight\" type=\"double\"/>");
            pw.println("    </attributes>");

            pw.println("    <nodes>");
            for (int v = 0; v < numVertices; v++) {
                pw.printf("      <node id=\"%d\" label=\"V%d\">%n", v, v);
                pw.println("        <attvalues>");
                pw.printf("          <attvalue for=\"0\" value=\"%.4f\"/>%n", vertexWeights[v]);
                pw.println("        </attvalues>");
                pw.println("      </node>");
            }
            pw.println("    </nodes>");

            pw.println("    <edges>");
            int edgeId = 0;
            for (int u = 0; u < numVertices; u++) {
                for (int v = 0; v < numVertices; v++) {
                    if (hasEdge(u, v)) {
                        double w = getEdgeWeight(u, v);
                        pw.printf("      <edge id=\"%d\" source=\"%d\" target=\"%d\">%n", edgeId++, u, v);
                        pw.println("        <attvalues>");
                        pw.printf("          <attvalue for=\"0\" value=\"%.4f\"/>%n", w);
                        pw.println("        </attvalues>");
                        pw.println("      </edge>");
                    }
                }
            }
            pw.println("    </edges>");

            pw.println("  </graph>");
            pw.println("</gexf>");

        } catch (IOException e) {
            throw new RuntimeException("Erro ao exportar grafo para GEXF: " + e.getMessage(), e);
        }
    }
}