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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static util.Config.connection_MYSQL;
import static util.Config.databaseName;
import static util.Config.driver_MYSQL;
import static util.Config.password;
import static util.Config.user;

/**
 *
 * @author root
 */
public class MD3 {
   static java.util.HashMap<String, String> hashMap = new HashMap<>();
   public static java.sql.Connection getConnection() {
        java.sql.Connection con = null;
        try {
            Class.forName(driver_MYSQL);
            String url = connection_MYSQL + databaseName;
            System.err.println(url+"---"+user+"---"+password);
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return con;
    }

    public static java.util.HashMap<String, String>  getMeta(Connection con, String table,String rdbms) {
        
        ResultSet rs = null;

        try {
            DatabaseMetaData meta = con.getMetaData();

            // Get Tables 
            String TABLE_NAME = "TABLE_NAME";
            String TABLE_SCHEMA = "TABLE_SCHEM";
            String[] TABLE_TYPES = {"TABLE"};
            java.util.List<String> tablesList = new java.util.ArrayList();
            ResultSet tables = meta.getTables(null, null, null, TABLE_TYPES);
            while (tables.next()) {
                String tableName = tables.getString(TABLE_NAME);
                tablesList.add(tableName);
            }
            for (int i = 0; i < tablesList.size(); i++) {
                String get = tablesList.get(i);
                table = table.equalsIgnoreCase(get) ? get : "";

            }
            ResultSet rsPK = meta.getPrimaryKeys(null, null, table);

            ResultSet rsUnique = meta.getIndexInfo(null, null, table, true, true);
            rs = !rsUnique.wasNull() ? rsUnique : rsPK;

            java.util.List<String> uniquesList = new java.util.ArrayList();
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                uniquesList.add(columnName);
            }
            java.util.List<String> columnsList = new java.util.ArrayList();
            java.util.List<String> typeList = new java.util.ArrayList();
            ResultSet resultSet = meta.getColumns(null, null, table, null);
            while (resultSet.next()) {
                String name = resultSet.getString("COLUMN_NAME");
                String type = resultSet.getString("TYPE_NAME");
                int size = resultSet.getInt("COLUMN_SIZE");
                columnsList.add(name);
                typeList.add(type);
            }
            
            java.util.List<String> valuesList = new java.util.ArrayList();
            String tableName = table;
            String[] fields = columnsList.toArray(new String[columnsList.size()]);

            String query = getCustomQuery(rdbms, tableName, fields);
            ResultSet qResultSet = con.createStatement().executeQuery(query);
            
            
            int index = 0;

            while (qResultSet.next()) {
                for (int i = 0; i < columnsList.size(); i++) {
                    String get = columnsList.get(i);
                    String values = qResultSet.getString(get);
                    valuesList.add(values);
                }
            }

            String unique = uniquesList.size() == 1 ? uniquesList.get(0) : "";
            for (int i = 0; i < columnsList.size(); i++) {
                String values = valuesList.get(i);
                String columns = columnsList.get(i);
                String type = typeList.get(i);
                if (!isIP(values)) {
                    if (unique.equalsIgnoreCase(columns)) {
                        hashMap.put("uniqueid.fields", columns);
                    } else if (type.equalsIgnoreCase("datetime")) {
                        hashMap.put("timestamp.field", columns);
                        hashMap.put("event.deviceReceiptTime", columns);
                    } else if (!type.equalsIgnoreCase("datetime") && !unique.equalsIgnoreCase(columns) && !isIP(values) && !containsIgnoreCase(columns, "name")) {
                        
                        hashMap.put("event.deviceCustomString1", columns);
                    }else{
                        
                        hashMap.put("event.sourceUserName", columns);
                    }

                } else {
                    if (i % 2 == 1) {
                        hashMap.put("event.destinationAddress", columns);
                        
                    } else {
                        hashMap.put("event.sourceAddress", columns);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }
    
    public static java.util.HashMap<String, String>  getMetaFromSQL(Connection con, String sql,String rdbms) {
        String table = DbUtils.getTableName(sql);
        ResultSet rs = null;

        try {
            DatabaseMetaData meta = con.getMetaData();

            // Get Tables 
            String TABLE_NAME = "TABLE_NAME";
            String TABLE_SCHEMA = "TABLE_SCHEM";
            String[] TABLE_TYPES = {"TABLE"};
            java.util.List<String> tablesList = new java.util.ArrayList();
            ResultSet tables = meta.getTables(null, null, null, TABLE_TYPES);
            while (tables.next()) {
                String tableName = tables.getString(TABLE_NAME);
                tablesList.add(tableName);
            }
            for (int i = 0; i < tablesList.size(); i++) {
                String get = tablesList.get(i);
                table = table.equalsIgnoreCase(get) ? get : "";

            }
            ResultSet rsPK = meta.getPrimaryKeys(null, null, table);

            ResultSet rsUnique = meta.getIndexInfo(null, null, table, true, true);
            
            rs = !rsUnique.wasNull() ? rsUnique : rsPK;

            java.util.List<String> uniquesList = new java.util.ArrayList();
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                uniquesList.add(columnName);
            }
            ResultSet resultSet1 = con.createStatement().executeQuery(sql);
            java.util.List<String> columnsList = DbUtils.resultSetToList(resultSet1);
            java.util.List<String> typeList = new java.util.ArrayList();
            
            ResultSet resultSet = meta.getColumns(null, null, table, null);
            while (resultSet.next()) {
                String name = resultSet.getString("COLUMN_NAME");
                String type = resultSet.getString("TYPE_NAME");
                int size = resultSet.getInt("COLUMN_SIZE");
                typeList.add(type);
            }
            
            java.util.List<String> valuesList = new java.util.ArrayList();
            String tableName = table;
            String[] fields = columnsList.toArray(new String[columnsList.size()]);

            String query = getCustomQuery(rdbms, tableName, fields);
            ResultSet qResultSet = con.createStatement().executeQuery(query);
            
            int index = 0;

            while (qResultSet.next()) {
                for (int i = 0; i < columnsList.size(); i++) {
                    String get = columnsList.get(i);
                    String values = qResultSet.getString(get);
                    valuesList.add(values);
                }
            }

            String unique = uniquesList.size() == 1 ? uniquesList.get(0) : "";
            for (int i = 0; i < columnsList.size(); i++) {
                String values = valuesList.get(i);
                String columns = columnsList.get(i);
                String type = typeList.get(i);
                if (!isIP(values)) {
                    if (unique.equalsIgnoreCase(columns)) {
                        hashMap.put("uniqueid.fields", columns);
                    } else if (type.equalsIgnoreCase("datetime")) {
                        System.err.println("---"+i);
                        if(i>1){
                            hashMap.put("event.deviceCustomDate"+(columnsList.size()-i), columns);
                        }else{
                            hashMap.put("timestamp.field", columns);
                        }
                        
                        hashMap.put("event.deviceReceiptTime", columns);
                    } else if (!type.equalsIgnoreCase("datetime") && !unique.equalsIgnoreCase(columns) && !isIP(values) && !containsIgnoreCase(columns, "name")) {
                        hashMap.put("event.deviceCustomString"+(columnsList.size()-i), columns);
                    }else{
                        hashMap.put("event.sourceUserName", columns);
                    }

                } else {
                    if (i % 2 == 1) {
                        hashMap.put("event.destinationAddress", columns);
                        
                    } else {
                        hashMap.put("event.sourceAddress", columns);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    public static String getCustomQuery(String rdbms, String tableName, String[] fields) {
        String delimeter_o = "";
        String delimeter_c = "";
        if (rdbms.equalsIgnoreCase("MySQL")) {
            delimeter_o = "`";
            delimeter_c = delimeter_o;
        } else if (rdbms.equalsIgnoreCase("MSSQL")) {
            delimeter_o = "[";
            delimeter_c = "]";
        }
        String sql = "SELECT ", table;
        //Fields Variables
        if (fields.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(delimeter_o);
            sb.append(fields[0]);
            sb.append(delimeter_c);
            sb.append(",");
            for (int i = 1; i < fields.length; i++) {
                sb.append(delimeter_o);
                sb.append(fields[i]);
                sb.append(delimeter_c);
                sb.append(",");
            }
            sb.setLength(sb.length() - 1);
            sb.append(" FROM ");
            sb.append(delimeter_o);
            sb.append(tableName);
            sb.append(delimeter_c);
            sql += sb.toString();

        }
        return sql;
    }

    public static boolean isIP(String ipAddress) {
        String IPADDRESS_PATTERN
                = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(ipAddress);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean containsIgnoreCase(String haystack, String needle) {
        if (needle.equals("")) {
            return true;
        }
        if (haystack == null || needle == null || haystack.equals("")) {
            return false;
        }

        Pattern p = Pattern.compile(needle, Pattern.CASE_INSENSITIVE + Pattern.LITERAL);
        Matcher m = p.matcher(haystack);
        return m.find();
    }
    
    public static void clearHashMap(){
        hashMap.clear();
    }

}
