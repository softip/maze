
import java.awt.Point;
import java.util.ArrayList;
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

// Java program to find if there is path 
// from top left to right bottom 
class GFG 
{ 
    private List<Point> points = new ArrayList<>();
    // to find the path from 
    // top left to bottom right 
    static boolean isPath(int arr[][]) 
    { 
        // set arr[0][0] = 1 
        arr[0][0] = 1; 
  
        // Mark reachable (from top left) nodes 
        // in first row and first column. 
        for (int i = 1; i < 5; i++) 
            if (arr[0][i] != -1) 
                arr[0][i] = arr[0][i - 1]; 
        
        for (int j = 1; j < 5; j++) 
            if (arr[j][0] != -1) 
                arr[j][0] = arr[j - 1][0]; 
  
        // Mark reachable nodes in 
        //  remaining matrix. 
        for (int i = 1; i < 5; i++) 
            for (int j = 1; j < 5; j++) 
                if (arr[i][j] != -1) 
                    arr[i][j] = Math.max(arr[i][j - 1], 
                                        arr[i - 1][j]); 
  
        // return yes if right  
        // bottom index is 1 
        return (arr[5 - 1][5 - 1] == 1); 
    } 
       
    //Driver code  
    public static void main(String[] args) 
    { 
        // Given array 
        int arr[][] = { { 0, 0, -1, 0, 0 }, 
                        { 0, 0, -1, 0, 0 }, 
                        { -1, 0, 0, -1, 0 }, 
                        { 0, -1, 0, 0, -1 }, 
                        { 0, 0, 0, -1, 0 } }; 
  
        // path from arr[0][0]  
        // to arr[row][col] 
        if (isPath(arr)) 
            System.out.println("Yes"); 
        else
            System.out.println("No"); 
        
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(" | " + arr[i][j]);
            }
            System.out.println("");
        }
    } 
}
