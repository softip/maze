


import pronto.*;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sound.midi.SysexMessage;
import ws3dproxy.CommandExecException;
import ws3dproxy.CommandUtility;
import ws3dproxy.WS3DProxy;
import ws3dproxy.model.Creature;
import ws3dproxy.model.Leaflet;
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
public class TesteLeaflets {
    public static Creature creature = null;
    private static World world;
    private static WS3DProxy proxy;

    public static void main(String[] args) {
       proxy = new WS3DProxy();
        try {
            world = World.getInstance();
            world.reset();  
            creature = proxy.createCreature(400, 300, 0);
            creature.updateState();
            List<String> colors = new ArrayList<>();
            List<Leaflet> leaflets = creature.getLeaflets();
            for (Leaflet leaflet : leaflets) {
                System.out.println(leaflet);
                for(Map.Entry<String, Integer[]> joias : leaflet.getItems().entrySet()){
                    if (joias.getValue()[0] > joias.getValue()[1]){
                       if (!colors.contains(joias.getKey())){
                           colors.add(joias.getKey());
                       }   
                    }                    
                }
                System.out.println(" ");
            }
            System.out.println("Falta Pegar as cores");
            for (String color : colors) {
                System.out.println(color);
            }
            
        } catch (CommandExecException e) {
            System.out.println("erro - i " + e.getMessage());
        } 
    
        
    }

    

}
