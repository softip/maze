package pronto;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author ivan-
 */
public class MyBFS {

    private int[][] matriz;
    private int sizeMatriz;
    private List<Point> directions = new ArrayList<>();

    public MyBFS(int[][] matriz) {
        this.matriz = matriz;
        this.sizeMatriz = matriz.length;
        directions.add(new Point(0, -1)); //acima
        directions.add(new Point(0, 1)); //abaixo
        directions.add(new Point(-1, 0)); //esquerda
        directions.add(new Point(1, 0)); //direita
        
        directions.add(new Point(1, -1)); //diagonal superior direita
        directions.add(new Point(-1, -1)); //diagonal superior direita
        directions.add(new Point(-1, 1)); //diagonal inferior esquerda
        directions.add(new Point(1, 1)); //diagonal inferior direita
    }

    private boolean isValid(int x, int y) {
        return (x >= 0 && x < sizeMatriz) && (y >= 0 && y < sizeMatriz) && (matriz[x][y] != 1);
    }

    private Point getOrigin() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if (matriz[i][j] == 2) { // é o robo
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    private Node findPathBySource() {

        Point origem = getOrigin();

        if (origem != null) {
            Queue<Node> fila = new ArrayDeque<>();
            Node noInicial = new Node(origem.x, origem.y, null);
            fila.add(noInicial);

            Set<String> nosVisitados = new HashSet<>();
            nosVisitados.add(noInicial.toString());

            while (!fila.isEmpty()) {
                Node noAtual = fila.poll();

                if (matriz[noAtual.row][noAtual.col] == 3) {
                    return noAtual;
                }

                for (Point direction : directions) {
                    int nextX = noAtual.row + direction.x;
                    int nextY = noAtual.col + direction.y;

                    if (isValid(nextX, nextY)) {
                        Node proximoNo = new Node(nextX, nextY, noAtual);

                        if (!nosVisitados.contains(proximoNo.toString())) {
                            fila.add(proximoNo);
                            nosVisitados.add(proximoNo.toString());
                        }
                    }
                }
            }
        }
        return null;
    }

    public List<Point> getPathListPoints(){
        Node node = findPathBySource();
        List<Point> pontos = new ArrayList();
        getPathPoints(node, pontos);
        return pontos;
    }
    
    private void getPathPoints(Node node, List<Point> listPoint){
        if (node != null){
            getPathPoints(node.parent, listPoint);
            listPoint.add(new Point(node.row, node.col));
        }
    }
    
    public void printPath(Node node) {
        if (node != null) {
            printPath(node.parent);
            System.out.println(node.toString() + " --> " + matriz[node.row][node.col]);
        }
    }

    public static void main(String[] args) {

        int[][] matrix = {
            {2, 1, 1},
            {0, 1, 0},
            {0, 0, 3},};

        MyBFS obj = new MyBFS(matrix);
        Node node = obj.findPathBySource();
        obj.printPath(node);
    }

    static class Node {

        public int row;
        public int col;
        public Node parent;

        public Node(int row, int col, Node parent) {
            this.row = row;
            this.col = col;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "(" + row + "," + col + ")";
        }
    }

}
