package grafo.exceptions;

public class InvalidVertexException extends RuntimeException {
    public InvalidVertexException(int vertex, int numVertices) {
        super("Vértice inválido: " + vertex + ". O grafo possui " + numVertices
                + " vértices (índices de 0 a " + (numVertices - 1) + ").");
    }
}
