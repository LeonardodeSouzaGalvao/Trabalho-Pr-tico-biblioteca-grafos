package app;

import grafo.AbstractGraph;
import grafo.AdjacencyListGraph;
import grafo.AdjacencyMatrixGraph;
import grafo.exceptions.EdgeNotFoundException;
import grafo.exceptions.InvalidVertexException;

public class Main {

    public static void main(String[] args) {

        sep("DEMONSTRAÇÃO DA BIBLIOTECA DE GRAFOS");


        // 1. Criação com Matriz de Adjacência
        sec("1. Criando grafo com MATRIZ DE ADJACÊNCIA (5 vértices)");
        AdjacencyMatrixGraph matrixGraph = new AdjacencyMatrixGraph(5);
        System.out.println("Vértices: " + matrixGraph.getVertexCount());
        System.out.println("Arestas iniciais: " + matrixGraph.getEdgeCount());

  
        // 2. Criação com Lista de Adjacência
        sec("2. Criando grafo com LISTA DE ADJACÊNCIA (5 vértices)");
        AdjacencyListGraph listGraph = new AdjacencyListGraph(5);
        System.out.println("Vértices: " + listGraph.getVertexCount());
        System.out.println("Arestas iniciais: " + listGraph.getEdgeCount());


        // 3. Inserção de arestas (mesmas nos dois grafos para comparação)
        sec("3. Inserção de arestas");


        addEdges(matrixGraph);
        addEdges(listGraph);

        System.out.println("\n[Matriz] Após inserção:");
        System.out.println(matrixGraph);

        System.out.println("[Lista] Após inserção:");
        System.out.println(listGraph);


        System.out.println("Idempotência — adicionando (0→1) de novo...");
        matrixGraph.addEdge(0, 1);
        listGraph.addEdge(0, 1);
        System.out.println("[Matriz] Arestas após addEdge repetido: " + matrixGraph.getEdgeCount() + " (deve ser 6)");
        System.out.println("[Lista]  Arestas após addEdge repetido: " + listGraph.getEdgeCount() + " (deve ser 6)");


        // 4. Remoção de arestas
        sec("4. Remoção de arestas");
        System.out.println("Removendo aresta (4→0) de ambos os grafos...");
        matrixGraph.removeEdge(4, 0);
        listGraph.removeEdge(4, 0);
        System.out.println("[Matriz] Arestas: " + matrixGraph.getEdgeCount() + " (deve ser 5)");
        System.out.println("[Lista]  Arestas: " + listGraph.getEdgeCount() + " (deve ser 5)");

        System.out.println("Removendo aresta inexistente (2→4) — sem exceção...");
        matrixGraph.removeEdge(2, 4);
        listGraph.removeEdge(2, 4);
        System.out.println("OK — operação silenciosa conforme esperado.");

        matrixGraph.addEdge(4, 0);
        listGraph.addEdge(4, 0);


        // 5. Sucessores e predecessores
        sec("5. Sucessores e predecessores");
        demoRelations(matrixGraph, "Matriz");
        demoRelations(listGraph, "Lista");


        // 6. Grau de entrada e saída
        sec("6. Grau de entrada e grau de saída");
        demoDegrees(matrixGraph, "Matriz");
        demoDegrees(listGraph, "Lista");

  
        // 7. Pesos de vértices
        sec("7. Definição e consulta de pesos de vértices");
        demoVertexWeights(matrixGraph, "Matriz");
        demoVertexWeights(listGraph, "Lista");


        // 8. Pesos de arestas
        sec("8. Definição e consulta de pesos de arestas");
        demoEdgeWeights(matrixGraph, "Matriz");
        demoEdgeWeights(listGraph, "Lista");


        // 9. Verificação: grafo vazio
        sec("9. Verificação: grafo vazio");
        System.out.println("[Matriz] isEmptyGraph(): " + matrixGraph.isEmptyGraph() + " (deve ser false)");
        System.out.println("[Lista]  isEmptyGraph(): " + listGraph.isEmptyGraph()  + " (deve ser false)");

        AdjacencyMatrixGraph emptyGraph = new AdjacencyMatrixGraph(3);
        System.out.println("[Vazio]  isEmptyGraph(): " + emptyGraph.isEmptyGraph() + " (deve ser true)");

 
        // 10. Verificação: grafo completo
        sec("10. Verificação: grafo completo");
        System.out.println("[Matriz] isCompleteGraph(): " + matrixGraph.isCompleteGraph() + " (deve ser false)");

        AdjacencyMatrixGraph completeGraph = new AdjacencyMatrixGraph(3);
        completeGraph.addEdge(0, 1); completeGraph.addEdge(0, 2);
        completeGraph.addEdge(1, 0); completeGraph.addEdge(1, 2);
        completeGraph.addEdge(2, 0); completeGraph.addEdge(2, 1);
        System.out.println("[Completo] isCompleteGraph(): " + completeGraph.isCompleteGraph() + " (deve ser true)");
        System.out.println("[Completo] Arestas: " + completeGraph.getEdgeCount() + " (deve ser 6 = 3×2)");

        // 11. Verificação: grafo conectado
        sec("11. Verificação: grafo conectado");
        System.out.println("[Matriz] isConnected(): " + matrixGraph.isConnected() + " (deve ser true)");

        AdjacencyMatrixGraph disconnected = new AdjacencyMatrixGraph(4);
        disconnected.addEdge(0, 1);
        disconnected.addEdge(2, 3); 
        System.out.println("[Desconectado] isConnected(): " + disconnected.isConnected() + " (deve ser false)");

   
        // 12. Métodos de relação entre arestas
        sec("12. isDivergent, isConvergent, isIncident");
        System.out.println("[Matriz] isDivergent(0,1, 0,2): " + matrixGraph.isDivergent(0, 1, 0, 2)
                + " (deve ser true — mesma origem 0)");
        System.out.println("[Matriz] isConvergent(1,3, 2,3): " + matrixGraph.isConvergent(1, 3, 2, 3)
                + " (deve ser true — mesmo destino 3)");
        System.out.println("[Matriz] isIncident(0,1, 0):     " + matrixGraph.isIncident(0, 1, 0)
                + " (deve ser true — 0 é extremo)");
        System.out.println("[Matriz] isIncident(0,1, 3):     " + matrixGraph.isIncident(0, 1, 3)
                + " (deve ser false — 3 não é extremo)");

  
        // 13. Tratamento de exceções
        sec("13. Tratamento de exceções");

        System.out.println("Testando vértice inválido (-1)...");
        try {
            matrixGraph.hasEdge(-1, 0);
        } catch (InvalidVertexException e) {
            System.out.println("  ✓ InvalidVertexException: " + e.getMessage());
        }

        System.out.println("Testando vértice inválido (999)...");
        try {
            listGraph.addEdge(0, 999);
        } catch (InvalidVertexException e) {
            System.out.println("  ✓ InvalidVertexException: " + e.getMessage());
        }

        System.out.println("Testando laço (2→2)...");
        try {
            matrixGraph.addEdge(2, 2);
        } catch (IllegalArgumentException e) {
            System.out.println("  ✓ IllegalArgumentException: " + e.getMessage());
        }

        System.out.println("Testando getEdgeWeight de aresta inexistente (0→4)...");
        try {
            matrixGraph.getEdgeWeight(0, 4);
        } catch (EdgeNotFoundException e) {
            System.out.println("  ✓ EdgeNotFoundException: " + e.getMessage());
        }

        System.out.println("Testando setEdgeWeight de aresta inexistente (4→2)...");
        try {
            listGraph.setEdgeWeight(4, 2, 99.0);
        } catch (EdgeNotFoundException e) {
            System.out.println("  ✓ EdgeNotFoundException: " + e.getMessage());
        }

        System.out.println("Testando isDivergent com aresta inexistente (0→4)...");
        try {
            matrixGraph.isDivergent(0, 4, 0, 1);
        } catch (EdgeNotFoundException e) {
            System.out.println("  ✓ EdgeNotFoundException: " + e.getMessage());
        }


        // 14. Exportação para GEPHI (.gexf)
        sec("14. Exportação para GEPHI (.gexf)");
        String matrixPath = "grafo_matriz.gexf";
        String listPath   = "grafo_lista.gexf";

        matrixGraph.exportToGEPHI(matrixPath);
        System.out.println("[Matriz] Exportado para: " + matrixPath);

        listGraph.exportToGEPHI(listPath);
        System.out.println("[Lista]  Exportado para: " + listPath);

        sep("FIM DA DEMONSTRAÇÃO");
    }


    // Métodos auxiliares de demonstração

    private static void addEdges(AbstractGraph g) {
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 0);
        System.out.println("[" + g.getClass().getSimpleName() + "] " + g.getEdgeCount() + " arestas adicionadas.");
    }

    private static void demoRelations(AbstractGraph g, String label) {
        System.out.printf("[%s] isSuccessor(0, 1):   %b (deve ser true)%n",  label, g.isSuccessor(0, 1));
        System.out.printf("[%s] isSuccessor(0, 4):   %b (deve ser false)%n", label, g.isSuccessor(0, 4));
        System.out.printf("[%s] isPredecessor(0, 4): %b (deve ser true)%n",  label, g.isPredecessor(0, 4));
        System.out.printf("[%s] isPredecessor(0, 1): %b (deve ser false)%n", label, g.isPredecessor(0, 1));
    }

    private static void demoDegrees(AbstractGraph g, String label) {
        for (int v = 0; v < g.getVertexCount(); v++) {
            System.out.printf("[%s] V%d — in-degree: %d, out-degree: %d%n",
                    label, v, g.getVertexInDegree(v), g.getVertexOutDegree(v));
        }
    }

    private static void demoVertexWeights(AbstractGraph g, String label) {
        g.setVertexWeight(0, 10.0);
        g.setVertexWeight(1, 20.5);
        g.setVertexWeight(2, 5.75);
        System.out.printf("[%s] Peso V0=%.2f, V1=%.2f, V2=%.2f%n",
                label, g.getVertexWeight(0), g.getVertexWeight(1), g.getVertexWeight(2));
    }

    private static void demoEdgeWeights(AbstractGraph g, String label) {
        g.setEdgeWeight(0, 1, 2.5);
        g.setEdgeWeight(0, 2, 1.0);
        g.setEdgeWeight(1, 3, 4.0);
        g.setEdgeWeight(2, 3, 3.0);
        g.setEdgeWeight(3, 4, 5.0);
        g.setEdgeWeight(4, 0, 0.5);
        System.out.printf("[%s] Peso (0→1)=%.1f, (1→3)=%.1f, (3→4)=%.1f%n",
                label, g.getEdgeWeight(0, 1), g.getEdgeWeight(1, 3), g.getEdgeWeight(3, 4));
    }

    private static void sec(String title) {
        System.out.println("\n--- " + title + " ---");
    }

    private static void sep(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  " + title);
        System.out.println("=".repeat(60));
    }
}
