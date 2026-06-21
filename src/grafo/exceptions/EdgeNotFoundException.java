package grafo.exceptions;

public class EdgeNotFoundException extends RuntimeException {
    public EdgeNotFoundException(int u, int v) {
        super("Aresta inexistente: (" + u + ", " + v + ").");
    }
}
