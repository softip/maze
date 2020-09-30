package teste;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ivan-
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShortestPath {

    public static void main(String args[]) {
        char[][] matrix = {
            {'S', '0', '1', '1'},
            {'1', '1', '0', '1'},
            {'0', '1', '1', '1'},
            {'1', '0', 'D', '1'}
        };

        int path = pathExists(matrix);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(" | " + matrix[i][j]);
            }
            System.out.println("");
        }
        
        System.out.println(path);
    }

    private static int pathExists(char[][] matrix) {

        Node source = new Node(0, 0, 0);
        Queue<Node> queue = new LinkedList<Node>();

        queue.add(source);

        while (!queue.isEmpty()) {
            Node poped = queue.poll();

            if (matrix[poped.x][poped.y] == 'D') {
                return poped.distanceFromSource;
            } else {
                matrix[poped.x][poped.y] = '0';

                List<Node> neighbourList = addNeighbours(poped, matrix);
                queue.addAll(neighbourList);
            }
        }
        return -1;
    }

    private static List<Node> addNeighbours(Node poped, char[][] matrix) {

        List<Node> list = new LinkedList<Node>();

        if ((poped.x - 1 > 0 && poped.x - 1 < matrix.length) && matrix[poped.x - 1][poped.y] != '0') {
            list.add(new Node(poped.x - 1, poped.y, poped.distanceFromSource + 1));
        }
        if ((poped.x + 1 > 0 && poped.x + 1 < matrix.length) && matrix[poped.x + 1][poped.y] != '0') {
            list.add(new Node(poped.x + 1, poped.y, poped.distanceFromSource + 1));
        }
        if ((poped.y - 1 > 0 && poped.y - 1 < matrix.length) && matrix[poped.x][poped.y - 1] != '0') {
            list.add(new Node(poped.x, poped.y - 1, poped.distanceFromSource + 1));
        }
        if ((poped.y + 1 > 0 && poped.y + 1 < matrix.length) && matrix[poped.x][poped.y + 1] != '0') {
            list.add(new Node(poped.x, poped.y + 1, poped.distanceFromSource + 1));
        }
        return list;
    }
}

 class Node {

    int x;
    int y;
    int distanceFromSource;

    Node(int x, int y, int dis) {
        this.x = x;
        this.y = y;
        this.distanceFromSource = dis;
    }
}
