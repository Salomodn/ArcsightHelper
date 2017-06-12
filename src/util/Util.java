/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Desktop;
import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author root
 */
public class Util {
    public static final String OS = System.getProperty("os.name").toLowerCase();
    public static String userHome = System.getProperty("user.home") + "\\".replace("\\", "/");
    public static String systemUser = System.getProperty("user.name");

    public Util() {
    }
    
    public static void exportToExel(String filePath,String title,Util.FileExporter.Listener listener,final boolean open){
        new Util.FileExporter(new File(filePath.replace("\\", "/")+"/"+title+""), listener);
    }
    
    public static void exportToExel(final JLabel component,String filePath,String title,final boolean open){
        new Util.FileExporter(component, new File(filePath.replace("\\", "/")+"/"+title+"_exported_data_"+System.currentTimeMillis()+".xls"), new Util.FileExporter.Listener() {

                @Override
                public void setMessage(String message) {
                     component.setText(message);
                     if(open && Desktop.isDesktopSupported()){
                    
                    try {
                        java.awt.Desktop.getDesktop().open(new File(message));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                }
            });
    }
    
    
    
    
    public static String getFilePath(){
        String path = new Util.FileExporter().getIconedOutputPath();
        return path;
    }
    

    public static class FileExporter {

        public FileExporter() {
        }
        
        

        public static void exportToExel(final JLabel component, final JTable jTable) {
            new Util.FileExporter(component, new File(".xls"), new Util.FileExporter.Listener() {

                @Override
                public void setMessage(String message) {
                    component.setText(message);
                }
            });
        }

        public FileExporter(JComponent component, File file, Listener listener) {
            try {
                createDirIfNotExists(getDirectoryPath(file.getCanonicalPath()));
                listener.setMessage("\n" + file.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        public FileExporter(File file, Listener listener) {
            try {
                FileExporter.listener = listener;
                createDirIfNotExists(getDirectoryPath(file.getCanonicalPath()));
                FileExporter.listener.setMessage(file.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void exportTable(JTable table, File file) {
            try {
                TableModel model = table.getModel();
                FileWriter out = new FileWriter(file);
                for (int i = 0; i < model.getColumnCount(); i++) {
                    out.write(model.getColumnName(i) + "\t");
                }
                out.write("\n");

                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        out.write(model.getValueAt(i, j).toString() + "\t");
                    }
                    out.write("\n");
                }

                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getIconedOutputPath() {
            String directory = "";
            JFileChooser chooser = new JFileChooser();
            Frame window = new Frame();
            window.setIconImage(new ImageIcon(getClass().getResource("/images/logo.png")).getImage());
            chooser.setCurrentDirectory(new File("*.*"));
            chooser.setDialogTitle("Browse Directory");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
                directory = chooser.getSelectedFile().toString();
                String file = chooser.getSelectedFile().getName().toString();
            }

            return directory;
        }

        public static String getDirectoryPath(String filePath) {
            String dirPath = null;
            int i = filePath.lastIndexOf("/");
            if (i > 0 && i < filePath.length() - 1) {
                dirPath = filePath.substring(+1);
            }
            dirPath = filePath.substring(0, i + (1));
            return dirPath;

        }

        public static String createDirIfNotExists(String directoryPath) {
            File dir = new File(directoryPath);
            if (dir.exists() == false) {
                dir.mkdirs();
            }
            return directoryPath;
        }

        public static String createFileIfNotExists(String directoryPath, String filename) {
            File dir = new File(directoryPath);
            if (dir.exists() == false) {
                dir.mkdirs();
                File file = new File(dir, filename);
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                File file = new File(dir, filename);
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            return directoryPath + "/" + filename;
        }

        public static interface Listener {

            public abstract void setMessage(String message);
        }
        public static Listener listener;
    }
}
