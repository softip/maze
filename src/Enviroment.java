
import ws3dproxy.CommandExecException;
import ws3dproxy.WS3DProxy;
import ws3dproxy.model.Creature;
import ws3dproxy.model.World;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ivan-
 */
public class Enviroment {

    public String host = "localhost";
    public Creature creature = null;
    private World world;

    public Enviroment() {
        WS3DProxy proxy = new WS3DProxy();
        try {
            world = World.getInstance();
            world.reset();
//          World.createFood(0, 350, 75);
            creature = proxy.createCreature(400, 300, 0, 1);
            creature.start();
            //creature.move(3, 3, 3);
            createMaze();

        } catch (CommandExecException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void createMaze() throws CommandExecException{
        //World.createBrick(0, 0, 0, 50, 200);
        
        int x1 = 0;
        int y1 = 0;
        int x2 = 800;
        int y2 = 0;
        
        for (double i = 3.25; i < 16; i = i + 4.25) {            
            y1 = (int) (i * 38);            
            y2 = y1 + 38;
            World.createBrick(4, x1, y1, x2, y2);    
        }
        
        //World.createBrick(1, 50, 50, 300, 300);
        //World.createBrick(0, 0, 410, 200, 448);
        //World.createBrick(0, 0, 562, 200, 600);
    }
}
