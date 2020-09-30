/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import pronto.CreatorMaze;

/**
 *
 * @author ivan-
 */
public class SearchPathDepthFirst {
    
    private List<Point> directions =  new ArrayList<>();
    private List<Point> path =  new ArrayList<>();
    
    private int maze[][];
    
    public SearchPathDepthFirst(int maze[][]){
        this.maze = maze.clone();
        directions.add(new Point(0, -1)); //acima
        directions.add(new Point(0, 1)); //abaixo
        directions.add(new Point(-1, 0)); //esquerda
        directions.add(new Point(1, 0)); //direita
    }
            
    public boolean searchPath(int[][] maze, int x, int y, List<Point> path){        
        //se encontrou o destino
        if (maze[x][y] == CreatorMaze.DESTINO){
            path.add(new Point(x, y));
            return true;
        }        
        
        if (maze[x][y] == CreatorMaze.VAZIO){
            maze[x][y] = 9; //marca como visitado, para não visitar novamente            
            //tenta mover para todas as direções
            for (Point direction : directions) {
                  //faz chamadas recursivas para encontrar o caminho
                  if(searchPath(maze, x+direction.x, y+direction.y, path)){
                      path.add(new Point(x, y));
                      return true;
                  }
            }            
        }
        return false;                        
    }
    
    public boolean hasPath(){
        Point origin = getCoordinateFrom(CreatorMaze.ROBO); 
        maze[origin.x][origin.y] = CreatorMaze.VAZIO;
        return searchPath(maze, origin.x, origin.y, path);
    }
    
    public Point getCoordinateFrom(int local){
        for (int linha = 0; linha < maze.length; linha++) {
            for (int coluna = 0; coluna < maze[0].length; coluna++) {
                if (maze[linha][coluna] == local){
                    return new Point(linha, coluna);
                }
            }
        }
        return null;
    }

    public List<Point> getPath() {
        return path;
    }                  
}
