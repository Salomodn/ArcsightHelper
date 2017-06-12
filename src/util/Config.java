/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author root
 */
public class Config {

    public static final String OS = System.getProperty("os.name").toLowerCase();
    public static String userHome = System.getProperty("user.home") + "\\".replace("\\", "/");
    public static String systemUser = System.getProperty("user.name");
    public static String APP_LOGO = "/images/logo.png";
    //db vars
    static public final String driver_MYSQL = "com.mysql.jdbc.Driver";
    static public final String driver_MSSQL = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static public final String driver_Oracle = "oracle.jdbc.driver.OracleDriver";
    static public  String host = "127.0.0.1";
    static public  String port_MySQL = "3306";
    static public  String port_MSSQL = "1433";
    static public  String port_Oracle = "1521";
    static public final String connection_MYSQL = "jdbc:mysql://"+host+":"+port_MySQL+"/";//"+port_MySQL+/"
    static public final String connection_MSSQL = "jdbc:sqlserver://"+host+":"+port_MSSQL+"/";//"+port_MSSQL+/"
    static public final String connection_Oracle = "jdbc:oracle:thin:@"+host+":"+port_MSSQL+"/";
    static public  String user = "";
    static public  String databaseName = "";
    static public  String password = "";

}
