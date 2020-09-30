package pronto;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


import javax.swing.JPanel;

/**
 *
 * @author ivan-
 */
public class CreatorMaze extends JPanel implements MouseListener, Observer{
    
    public static final int VAZIO = 0;
    public static final int PAREDE = 1;    
    public static final int ROBO = 2;
    public static final int DESTINO = 3;
    
    private int w;
    private int h;
    private int size = 12;
    private int tamCell;
    private int actionSelected = PAREDE;
    private int[][] maze;
    private Image imgRobo;
    private Image imgLocalizacao;
    private final List<Point> pathPercorrido = new ArrayList<>();
    private Point lastPointRobo;
    
    public CreatorMaze(int w, int h, int size) {       
        this.w = w;
        this.h = h;  
        this.size = size;
        maze = new int[size][size];
        clearMaze();
        addMouseListener(this);        
    }       
    
    private void clearMaze(){
        for (int linha = 0; linha < size; linha++) {
            for (int coluna = 0; coluna < size; coluna++) {
                if (linha == 0 || coluna == 0 || linha == size-1 || coluna == size-1){                    
                    maze[linha][coluna] = PAREDE;
                }else{
                    maze[linha][coluna] = VAZIO;
                }                
            }
        }
        pathPercorrido.clear();
    }
        
    @Override
    public void paint(Graphics g){
        super.paint(g);  
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);
        g.setColor(Color.BLACK);
        tamCell = (w / size); 
        drawMaze(g);
        drawPath(g);        
        drawRoboEDestino(g);
        drawGrid(g);
    }            
    
    private void drawMaze(Graphics g){
        for (int linha = 0; linha < size; linha++) {            
            for (int coluna = 0; coluna < size; coluna++) {
                Color cor = Color.WHITE;
                switch(maze[linha][coluna]){
                    case PAREDE: cor = Color.BLACK; break;
                    case VAZIO: cor = Color.WHITE; break;
                }     
                g.setColor(cor);
                g.fillRect(linha * tamCell, coluna * tamCell, tamCell, tamCell);                                              
            }
        }
    }
    
    private void drawRoboEDestino(Graphics g){
        for (int linha = 0; linha < size; linha++) {            
            for (int coluna = 0; coluna < size; coluna++) {
                Image img  = null;
                Color cor = Color.WHITE;
                switch(maze[linha][coluna]){                    
                    case ROBO: img= imgRobo; break;
                    case DESTINO: img = imgLocalizacao; break;
                }     
                                              
                if (img != null){
                    g.setColor(cor);
                    g.fillRect(linha * tamCell, coluna * tamCell, tamCell, tamCell);
                    g.drawImage(img, linha * tamCell+4, coluna * tamCell+4, tamCell-4, tamCell-4, null);
                }
            }
        }
    }
    
    private void drawGrid(Graphics g){
        g.setColor(Color.BLACK);
        for (int linha = 0; linha < size; linha++) {            
            for (int coluna = 0; coluna < size; coluna++) {
                g.drawRect(linha * tamCell, coluna * tamCell, tamCell, tamCell);
            }
        }        
    }
    
    private void drawPath(Graphics g){
        g.setColor(Color.RED);
        int mc = tamCell / 2;
        Point last = null;
        Point atual = null;
        for (Point point : pathPercorrido) {
            atual = new Point(point.x * tamCell + mc, point.y * tamCell + mc);
            g.fillArc(atual.x - 5 , atual.y -5, 10, 10, 0, 360);
            if (last != null){
                g.drawLine(atual.x, atual.y, last.x, last.y);
            }
            last = atual;
        }        
    }
    
    public Point translateXYToLC(Point clickPoint){                
        for (int linha = 0; linha < size; linha++) {
            for (int coluna = 0; coluna < size; coluna++) {
                Rectangle rec = new Rectangle(linha * tamCell, coluna * tamCell, tamCell, tamCell); 
                if (rec.contains(clickPoint)){
                    return new Point(linha, coluna);
                }
            }
        }        
        return null;
    }    
    
    public void createMazeElement(Point lc){
        maze[lc.x][lc.y] = actionSelected;
        if (actionSelected == ROBO){
            lastPointRobo = lc;
        }
        repaint();
    }
    
    public void setParede(){
        this.actionSelected = PAREDE;
    }
    
    public void setRobo(){
        this.actionSelected = ROBO;
    }
    
    public void setDestino(){
        this.actionSelected = DESTINO;
    }
    
    public void setRemove(){
        this.actionSelected = VAZIO;
    }

    public void setImgRobo(String pathRobo) {
        this.imgRobo = new javax.swing.ImageIcon(getClass().getResource(pathRobo)).getImage();
    }

    public void setImgLocalizacao(String pathLocalizacao) {        
        this.imgLocalizacao = new javax.swing.ImageIcon(getClass().getResource(pathLocalizacao)).getImage();
    }
    
    public int[][] getMaze(){
        return maze;
    }    
        
    @Override
    public void mouseClicked(MouseEvent e) {     
        
    }
        
    @Override
    public void mousePressed(MouseEvent e) {
        Point p = translateXYToLC(e.getPoint()) ;
        if (p != null){
            createMazeElement(p);
        }
    }
    
    public void addPointCaminho(Point p){
        this.pathPercorrido.add(p);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ControladorRobo){            
            Point ponto = (Point) arg;            
            maze[lastPointRobo.x][lastPointRobo.y] = VAZIO;
            maze[ponto.x][ponto.y] = ROBO;                        
            this.pathPercorrido.add(lastPointRobo);
            lastPointRobo = ponto;
            repaint();
        }                    
    }   
}
