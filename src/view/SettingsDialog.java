/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.CardLayout;
import java.awt.Component;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

/**
 *
 * @author root
 */
public class SettingsDialog extends javax.swing.JDialog {
    
    int counter = 0;
    int cardsLength = 0;
    List<String> componentsList = new ArrayList();
    static Connection con;
    static HashMap<String,String> hashMap;

    /**
     * Creates new form SettingsDialog
     */
    public SettingsDialog(java.awt.Frame parent, String modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        displayCardGUI();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPane = new javax.swing.JPanel();
        jButtonNext = new javax.swing.JButton();
        jButtonPrevious = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout contentPaneLayout = new javax.swing.GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 241, Short.MAX_VALUE)
        );

        jButtonNext.setText("Next");
        jButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextActionPerformed(evt);
            }
        });

        jButtonPrevious.setText("Previous");
        jButtonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviousActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 366, Short.MAX_VALUE)
                .addComponent(jButtonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(contentPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNext)
                    .addComponent(jButtonPrevious))
                .addContainerGap(86, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextActionPerformed
        if (counter < contentPane.getComponentCount() - 1) {
            counter++;
        }
        if (counter == contentPane.getComponentCount() - 1) {
            jButtonNext.setEnabled(false);
            jButtonPrevious.setEnabled(true);
        }
        navigate(counter);
// TODO add your handling code here:
    }//GEN-LAST:event_jButtonNextActionPerformed

    private void jButtonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviousActionPerformed
        if (counter > 0) {
            counter--;
            
        }
        if (counter == 1) {
            jButtonPrevious.setEnabled(false);
            jButtonNext.setEnabled(true);
        }
        
        navigate(counter);
    }//GEN-LAST:event_jButtonPreviousActionPerformed

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
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SettingsDialog dialog = new SettingsDialog(new javax.swing.JFrame(), "Settings");
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPane;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonPrevious;
    // End of variables declaration//GEN-END:variables
    ConnectionJPanel connectionJPanel = new ConnectionJPanel();

    private void displayCardGUI() {
        setIconImage(new ImageIcon(getClass().getResource(util.Config.APP_LOGO)).getImage());
        contentPane.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new CardLayout());
        PlainJPanel plainJPanel = new PlainJPanel();
        SettingsJPanel settingsJPanel = new SettingsJPanel();
        
        TestConnectionJPanel testConnectionJPanel = new TestConnectionJPanel();
        settingsJPanel.setName("Settings");
        connectionJPanel.setName("Connection");
        testConnectionJPanel.setName("TestConnection");
        contentPane.add(settingsJPanel, "Settings");
        contentPane.add(testConnectionJPanel, "TestConnection");
        contentPane.add(connectionJPanel, "Connection");

        
        cardsLength = contentPane.getComponents().length;
        if (counter == 0) {
            jButtonPrevious.setEnabled(false);
            jButtonNext.setEnabled(true);
        }

        // add components list
        for (int i = 0; i < contentPane.getComponents().length; i++) {
            Component c = contentPane.getComponents()[i];
            String accessibleName = c.getName();
            componentsList.add(accessibleName);
            
        }
    }
    
    public void nextPanel(String panel) {
        int total = contentPane.getComponents().length;
        if (1 == total) {
            contentPane.remove(0);
            displayCardGUI();
        }
        CardLayout layout = (CardLayout) contentPane.getLayout();
        layout.show(contentPane, panel);
    }
    
    private void navigate(int position) {
        int p = position;
        String panel = componentsList.get(p);
        nextPanel(panel);
        if (p == 2) {
            connectionJPanel.setConnection(con);
            String tn = connectionJPanel.getTableParam();
            connectionJPanel.setTableName(tn);
            connectionJPanel.populateTable();
        }
        System.out.println(p);
        
    }
    
    public static class ConnectionClass implements TestConnectionJPanel.ConnectionInterface {
        
        @Override
        public void setConnection(Connection con,HashMap<String,String> hashMap) {
            SettingsDialog.con = con;
            SettingsDialog.hashMap = hashMap;
        }

        @Override
        public void setConnectionError(String error) {

        }
    }
    
}
