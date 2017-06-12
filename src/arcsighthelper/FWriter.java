/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcsighthelper;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author root
 */
public class FWriter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Set the look and feel to users OS LaF.
        try {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                /* Create and display the form */
                java.awt.EventQueue.invokeLater(() -> {
                    new view.Builder().setVisible(true);
                });
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
        }
        

    }
    
}
