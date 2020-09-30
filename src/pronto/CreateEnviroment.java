package pronto;


import java.awt.Rectangle;
import java.util.List;
import javax.sound.midi.SysexMessage;
import ws3dproxy.CommandExecException;
import ws3dproxy.CommandUtility;
import ws3dproxy.WS3DProxy;
import ws3dproxy.model.Creature;
import ws3dproxy.model.Thing;
import ws3dproxy.model.World;
import ws3dproxy.util.Constants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ivan-
 */
public class CreateEnviroment {
    public String host = "localhost";
    public Creature creature = null;
    private World world;
    private int[][] maze;
    private int sizeCell;
    private WS3DProxy proxy;

    public CreateEnviroment(int[][] maze) {
        this.maze = maze;
        proxy = new WS3DProxy();
        try {
            world = World.getInstance();
            world.reset();  
            world.setEnvironmentDimension(600, 600);            
            sizeCell = 600 / maze.length;            
            createObjectsMaze();
        } catch (CommandExecException e) {
            System.out.println("erro - i " + e.getMessage());
        }
    }

    private void createObjectsMaze() throws CommandExecException {
        for (int linha = 0; linha < maze.length; linha++) {
            for (int coluna = 0; coluna < maze[0].length; coluna++) {
                switch(maze[linha][coluna]){                    
                    case CreatorMaze.PAREDE : World.createBrick(1, linha * sizeCell, coluna * sizeCell, linha * sizeCell + sizeCell, coluna * sizeCell + sizeCell); break;
                    case CreatorMaze.ROBO : creature = proxy.createCreature(linha * sizeCell + (sizeCell/2), coluna * sizeCell + (sizeCell/2), 0); break;
                    case CreatorMaze.DESTINO : World.createFood(0, linha * sizeCell + (sizeCell/2), coluna * sizeCell + (sizeCell/2));break;
                }                
            }            
        }
        
            List<Thing> coisas =  World.getWorldEntities();
            for (Thing coisa : coisas) {
                if (coisa.getCategory() == Constants.categoryCREATURE){
                     creature = Creature.getInstance(CommandUtility.getCreatureState(coisa.getName()));
                     System.out.println("executei peguei a criatura vazia");
                }
            }
        
    }
    
    public ControladorRobo getControlador(){
        return new ControladorRobo(creature, maze, sizeCell);
    }
    
    public Creature getCreature(){
        return creature;
    }

}
