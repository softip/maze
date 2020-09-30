package pronto;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Observable;
import ws3dproxy.CommandExecException;
import ws3dproxy.CommandUtility;
import ws3dproxy.model.Creature;
import ws3dproxy.model.Thing;
import ws3dproxy.model.WorldPoint;
import ws3dproxy.util.Constants;

/**
 *
 * @author ivan-
 */
public class ControladorRobo extends Observable{

    private Creature robo;
    private int[][] maze;
    private int sizeCell;
    private int midleSizeCell;
    private boolean doMoviment = false;

    public ControladorRobo(Creature robo, int[][] maze, int sizeCell) {
        this.robo = robo;
        this.maze = maze;
        this.sizeCell = sizeCell;
        this.midleSizeCell = sizeCell / 2;       
    
    }

    public void moveFront() {
        if (!doMoviment && !hasBrickAHead()){
            Point p = getCoordinateRobo();
            Point nextP = null;
            if (p != null) {
                double degree = Math.toDegrees(robo.getPitch());
                if (degree < 0){
                    degree = 360 + degree;
                }
                if (degree < 20 || degree > 340) {
                    nextP = new Point(p.x + 1, p.y);
                } else if (degree > 160 && degree < 200) {
                    nextP = new Point(p.x - 1, p.y);
                } else if (degree < 110 && degree > 70) {
                    nextP = new Point(p.x, p.y + 1);
                } else if (degree > 250 && degree < 290) {
                    nextP = new Point(p.x, p.y - 1);
                } else {
                    System.out.println("Deu ruim");
                }
            }
            Point next = new Point(nextP.x * sizeCell + midleSizeCell, nextP.y * sizeCell + midleSizeCell);
            doMoviment = true;
            DoMoviment move = new DoMoviment(this, robo, next);
            move.start();
        }
    }

    public void turnLeft() {
        try {
            CommandUtility.sendSetAngle(robo.getIndex(), 0, 0, Math.PI);
        } catch (CommandExecException ex) {
            Logger.getLogger(ControladorRobo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void turnRight() {
        try {
            CommandUtility.sendSetAngle(robo.getIndex(), 0, 0, 0);
        } catch (CommandExecException ex) {
            Logger.getLogger(ControladorRobo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void turnDown() {
        try {
            CommandUtility.sendSetAngle(robo.getIndex(), 0, 0, Math.PI / 2);
        } catch (CommandExecException ex) {
            Logger.getLogger(ControladorRobo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void turnUp() {
        try {
            CommandUtility.sendSetAngle(robo.getIndex(), 0, 0, Math.PI * 3 / 2);
        } catch (CommandExecException ex) {
            Logger.getLogger(ControladorRobo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean hasBrickAHead(){
        this.robo.updateState();
        List<Thing> coisas = this.robo.getThingsInVision();
        for (Thing coisa : coisas) {
            if (coisa.getCategory() == Constants.categoryBRICK){
                double distancia = this.robo.calculateDistanceTo(coisa); 
                System.out.println("Distancia do WS " + distancia);             
                if (distancia < 29){
                    return true;
                }
            }                       
        }
        return false;
    }
   

    public Point getCoordinateRobo() {
        this.robo.updateState();
        WorldPoint com = robo.getAttributes().getCOM();
        Rectangle minRectRobo = new Rectangle((int) com.getX() - 4, (int) com.getY() - 4, (int) com.getX() + 4, (int) com.getY() + 4);

        for (int linha = 0; linha < maze.length; linha++) {
            for (int coluna = 0; coluna < maze[0].length; coluna++) {
                Rectangle r = new Rectangle(linha * sizeCell + (sizeCell / 2) - 5, coluna * sizeCell + (sizeCell / 2) - 5, 10, 10);
                if (r.intersects(minRectRobo)) {
                    return new Point(linha, coluna);
                }
            }
        }
        return null;
    }

    public Creature getCreature() {
        return robo;
    }
    
    public void notificarMovimentoConcluido(){
        Point p = getCoordinateRobo();
        setChanged();
        notifyObservers(p);
        this.doMoviment = false;
    }   
}

class DoMoviment implements Runnable{

    private final Thread thread;
    private boolean movimentando = true;
    private final Creature creature;
    private final Point next;
    private final ControladorRobo controlador;

    public DoMoviment(ControladorRobo controlador, Creature creature, Point next) {
        this.controlador = controlador;
        this.creature = creature;
        this.next = next;
        thread = new Thread(this);
    }

    public void start() {
        thread.start();
    }

    @Override
    public void run() {
        try {
            creature.start();
            while (movimentando) {
                creature.updateState();
                WorldPoint p = creature.getAttributes().getCOM();
                Rectangle pointCreature = new Rectangle((int) p.getX() - 2, (int) p.getY() - 2, 4, 4);
                Ellipse2D centralCell = new Ellipse2D.Float(next.x - 2, next.y - 2, 4, 4);
                if (centralCell.intersects(pointCreature)) {
                    movimentando = false;
                    controlador.notificarMovimentoConcluido();
                    creature.stop();
                } else {
                    creature.moveto(1, next.x, next.y);                    
                }
                sleep();
            }
        } catch (CommandExecException ex) {
            Logger.getLogger(DoMoviment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(DoMoviment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
}
