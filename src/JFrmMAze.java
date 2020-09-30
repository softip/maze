
import java.awt.Color;
import java.awt.Graphics;
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
public class JFrmMAze extends javax.swing.JFrame {

    //0 nao vizitado
    //1 parede
    //2 visitado
    //3 alvo
    private List<Integer> path = new ArrayList<>();
    
    int [][] maze = 
            { {1,1,1,1,1,1,1,1,1,1,1,1,1},
              {1,0,1,0,1,0,1,0,0,0,0,0,1},
              {1,0,1,0,0,0,1,0,1,1,1,0,1},
              {1,0,0,0,1,1,1,0,0,0,0,0,1},
              {1,0,1,0,0,0,0,0,1,1,1,0,1},
              {1,0,1,0,1,1,1,0,1,0,0,0,1},
              {1,0,1,0,1,0,0,0,1,1,1,0,1},
              {1,0,1,0,1,1,1,0,1,0,1,0,1},
              {1,0,0,0,0,0,0,0,0,0,1,9,1},
              {1,1,1,1,1,1,1,1,1,1,1,1,1}

            };
    /**
     * Creates new form JFrmMAze
     */
    public JFrmMAze() {
        initComponents();
        
      AlgoritmoBusca.searchPath(maze, 1, 1, path);
      
        System.out.println(path);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.translate(50, 50);
        for (int linha = 0; linha < maze.length; linha++) {
            for (int coluna = 0; coluna < maze[linha].length; coluna++) {
                Color color;
                switch(maze[linha][coluna]){
                    case 1 : color = Color.BLACK; break;
                    case 9 : color = Color.RED; break;
                    default:color = Color.WHITE; break;
                }
                g.setColor(color);
                g.fillRect(30*coluna, 30 * linha, 30, 30);
                g.setColor(Color.BLACK);
                g.drawRect(30*coluna, 30*linha, 30, 30);
            }
        }
        //desenha o caminho
        for (int i = 0; i < path.size(); i = i + 2) {
            int pathX = path.get(i);
            int pathY = path.get(i+1);
             g.setColor(Color.GREEN);
             g.fillRect(30*pathX, 30 * pathY, 30, 30);
            
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrmMAze.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrmMAze.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrmMAze.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrmMAze.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrmMAze().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
