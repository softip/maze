
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ivan-
 */
public class AlgoritmoBusca {
    
    //if path was found, this metodo will return true
    //and the path list will be filled
    //like this (xn, yn ...., x2,y2
    //so the order is inverted
    //x and y are the start searchin position
    
    public static boolean searchPath(int[][] maze, int x, int y, List<Integer> path){
        
        //verifica se chegou ao alvo
        if (maze[y][x] == 9){
            path.add(x);
            path.add(y);
            return true;
        }
        
        //whe the currente position x and y is
        //a not visited node [0] the lets
        //mark it as visited [2]
        
        if(maze[y][x] == 0){
            maze[y][x] = 2;
            
            //now lets visite all neigbor node recursively
            //if path was found, lets fill the path list
            //will current position
            int dx = -1;
            int dy = 0;
            if (searchPath(maze, x+dx, y+dy, path))
            {
                path.add(x);
                path.add(y);
                return true;
            }
            
            dx = 1;
            dy = 0;
            if (searchPath(maze, x+dx, y+dy, path))
            {
                path.add(x);
                path.add(y);
                return true;
            }
            
            dx = 0;
            dy = -1;
            if (searchPath(maze, x+dx, y+dy, path))
            {
                path.add(x);
                path.add(y);
                return true;
            }
            
            dx = 0;
            dy = 1;
            if (searchPath(maze, x+dx, y+dy, path))
            {
                path.add(x);
                path.add(y);
                return true;
            }
        }
        return false;
    }
}
