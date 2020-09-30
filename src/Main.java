/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ivan-
 */
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

// queue node used in BFS
class Node {
    // (x, y) representa coordenadas de uma célula na matriz
    int x, y;

    // manter um nó pai para imprimir o caminho final
    Node parent;

    Node(int x, int y, Node parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }
}

class Main {
    // N x N matrix
    private static int N;

    // As matrizes abaixo detalham todos os 4 movimentos possíveis de uma célula
    private static int[] row = {-1, 0, 0, 1};
    private static int[] col = {0, -1, 1, 0};

    // A função retorna false se ponto não for uma posição válida
    private static boolean isValid(int x, int y) {
        return (x >= 0 && x < N) && (y >= 0 && y < N);
    }

    // Encontre a rota mais curta na matriz da célula de origem (x, y) para
    // a celula destino (N - 1, N - 1)
    public static Node findPath(int matrix[][], int x, int y) {
        // crie uma fila e enfileire o primeiro nó
        Queue<Node> q = new ArrayDeque<>();
        Node src = new Node(x, y, null);
        q.add(src);

        // definido para verificar se a célula da matriz é visitada antes ou não
        Set<String> visited = new HashSet<>();

        String key = src.x + "," + src.y;
        visited.add(key);

        // executar até a fila não estar vazia
        while (!q.isEmpty()) {
            // pool - remove o primeiro item da fila - pop nó frontal da fila e processá-lo -- 
            Node curr = q.poll();
            int i = curr.x;
            int j = curr.y;

            // retornar se o destino for encontrado
            if (i == N - 1 && j == N - 1) {
                return curr;
            }

            // valor da célula atual
            int n = matrix[i][j];

            //verifique todos os 4 movimentos possíveis da célula atual 
            //e se repita para cada movimento válido
            for (int k = 0; k < 4; k++) {
                // obter coordenadas da próxima posição usando o valor da célula atual
                x = i + row[k] * n;
                y = j + col[k] * n;

                
                //verifique se é possível ir para a próxima 
                //posição a partir da posição atual
                if (isValid(x, y)) {
                    // construct next cell node
                    Node next = new Node(x, y, curr);

                    key = next.x + "," + next.y;

                    // if it not visited yet
                    if (!visited.contains(key)) {
                        // empurre-o para a fila e marque-o como visitado
                        q.add(next);
                        visited.add(key);
                    }
                }
            }
        }

        // retornar nulo se o caminho não for possível
        return null;
    }

    // Função utilitária para imprimir o caminho da origem ao destino
    private static int printPath(Node node) {
        if (node == null) {
            return 0;
        }
        int len = printPath(node.parent);
        System.out.print(node + " ");
        return len + 1;
    }

    public static void main(String[] args) {
        int[][] matrix
                = {
                    {1, 0, 6, 5, 5, 1, 1, 1, 7, 4},
                    {1, 0, 2, 4, 6, 5, 7, 2, 6, 6},
                    {1, 0, 6, 1, 1, 1, 7, 1, 4, 5},
                    {1, 0, 6, 3, 1, 3, 3, 1, 1, 7},
                    {1, 0, 6, 4, 7, 2, 6, 5, 4, 4},
                    {1, 0, 5, 1, 2, 5, 1, 2, 3, 4},
                    {1, 0, 2, 2, 5, 2, 3, 7, 7, 3},
                    {1, 0, 4, 3, 5, 2, 2, 3, 6, 3},
                    {1, 0, 4, 2, 6, 4, 6, 7, 3, 7},
                    {1, 3, 1, 1, 1, 1, 1, 1, 1, 1}
                };

        N = matrix.length;

        // Find a route in the matrix from source cell (0, 0) to
        // destination cell (N - 1, N - 1)
        Node node = findPath(matrix, 0, 0);

        if (node != null) {
            System.out.print("Shortest path is: ");
            int len = printPath(node) - 1;
            System.out.println("\nShortest path length is " + len);
        } else {
            System.out.println("Destination not found");
        }
    }
}
