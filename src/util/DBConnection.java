/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

import view.TestConnectionJPanel;

/**
 *
 * @author root
 */
public class DBConnection {
    static DBConnectionInfo dBConnectionInfo;

    public DBConnection() {
    }

    public static java.sql.Connection getConnection(String url,String databaseName,String user,String password,String port,String driver) {
        dBConnectionInfo = new TestConnectionJPanel();
        if(util.Config.OS.equalsIgnoreCase("linux")){
            Config.port_MySQL = port;
        }else{
            Config.port_MSSQL = port;
        }
        java.sql.Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url+ databaseName, user, password);
        } catch (Exception e) {
            //e.printStackTrace();
            dBConnectionInfo.setConnectionError(e.getLocalizedMessage());
        }

        return con;
    }
    
    public static String[] getTables(Connection con) {
        java.util.List<String> tablesList = new java.util.ArrayList();
        try {
            DatabaseMetaData meta = con.getMetaData();

            // Get Tables 
            String TABLE_NAME = "TABLE_NAME";
            String TABLE_SCHEMA = "TABLE_SCHEM";
            String[] TABLE_TYPES = {"TABLE"};
            
            ResultSet tables = meta.getTables(null, null, null, TABLE_TYPES);
            while (tables.next()) {
                String tableName = tables.getString(TABLE_NAME);
                //System.out.println(tables.getString(TABLE_NAME));
                //System.out.println(tables.getString(TABLE_SCHEMA));
                tablesList.add(tableName);
            }
        } catch (Exception e) {
        }
        return tablesList.toArray(new String[tablesList.size()]);
    }
    
    public static interface DBConnectionInfo{
        void setConnectionError(String error);
    }
}
