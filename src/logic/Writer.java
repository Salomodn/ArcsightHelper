/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import view.MainJPanel;

/**
 *
 * @author root
 */
public class Writer
        implements view.MainJPanel.Iface {

    static File file;
    static String rdbms;
    static String[] fields;
    static String connectorType;
    static String paths;
    static String event_time, event_id;

    public static final String OS = System.getProperty("os.name").toLowerCase();
    public static String userHome = System.getProperty("user.home") + "\\".replace("\\", "/");
    public static String systemUser = System.getProperty("user.name");
    private static String tableName;
    private static String vendor;
    private static String product;
    private static HashMap<String, String> hashMap = new HashMap<String, String>();
    //Include Data Writer class
    private static DataWriter dataWriter = new DataWriter();
    private static String optionalParam;
    private static String query;

    public static void writeToFile(String RDBMSType, List<Object>... args) throws IOException {
        try {
            List<Object> mappingList = args[0];
            List<Object> valuesList = args[1];
            if (mappingList.size() == valuesList.size()) {
                for (int i = 0; i < mappingList.size(); i++) {
                    Object get = mappingList.get(i);
                    if (String.valueOf(get).equalsIgnoreCase("uniqueid.fields")) {
                        event_id = String.valueOf(valuesList.get(i));
                    }

                    if (String.valueOf(get).equalsIgnoreCase("timestamp.field")) {
                        event_time = String.valueOf(valuesList.get(i));
                    }

                }
            }

            String delimeter_o = "";
            String delimeter_c = "";
            if (rdbms.equalsIgnoreCase("MySQL")) {
                delimeter_o = "`";
                delimeter_c = delimeter_o;
            } else if (rdbms.equalsIgnoreCase("MSSQL")) {
                delimeter_o = "[";
                delimeter_c = "]";
            } else if (rdbms.equalsIgnoreCase("Oracle")) {
                delimeter_o = "\"";
                delimeter_c = delimeter_o;
            }
            String paramClause = !optionalParam.equalsIgnoreCase("") ? " WHERE " + delimeter_o + "" + event_time + "" + "" + delimeter_c + "" + " LIKE '" + optionalParam + "%' " : "";
            String clause = (connectorType.contains("sdktbdatabase") ? paramClause : "");
            if ("MySQL".equalsIgnoreCase(RDBMSType)) {
                String version_query = capQuery(query) + " LIMIT 1";
                String lastdate_query = capQuery(query.replaceAll(".*(?i)\\s+from", "SELECT MIN(" + delimeter_o + "" + event_time + "" + "" + delimeter_c + ") FROM ")).replaceAll(".*(?i)\\s+from", "SELECT MIN(`eventtime`) FROM");
                String lclause = null;
                if (connectorType.contains("sdktbdatabase")) {
                    if (lastdate_query.contains(lastdate_query.replaceAll("(?i)where", "WHERE"))) {
                        lclause = (" AND " + delimeter_o + "" + event_time + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    } else {
                        lclause = (" WHERE " + delimeter_o + "" + event_time + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    }
                    lastdate_query = (lastdate_query + lclause);
                } else if (connectorType.contains("sdkibdatabase")) {
                    if (lastdate_query.contains(lastdate_query.replaceAll("(?i)where", "WHERE"))) {
                        lclause = (" AND " + delimeter_o + "" + event_id + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    } else {
                        lclause = (" WHERE " + delimeter_o + "" + event_id + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    }
                    lastdate_query = (lastdate_query + lclause);
                }
                
                String event_deviceVendor = vendor;
                String event_deviceProduct = product;
                mappingList.add(0, "version.query");
                mappingList.add(1, "lastdate.query");
                mappingList.add(2, "query");
                mappingList.add(3, "event.deviceVendor");
                mappingList.add(4, "event.deviceProduct");

                valuesList.add(0, version_query);
                valuesList.add(1, lastdate_query);
                query = capQuery(query);

                String qclause = null;
                if (connectorType.contains("sdktbdatabase")) {
                    if (query.contains(query.replaceAll("(?i)where", "WHERE"))) {
                        qclause = (" AND " + delimeter_o + "" + event_time + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    } else {
                        qclause = (" WHERE " + delimeter_o + "" + event_time + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    }
                } else if (connectorType.contains("sdkibdatabase")) {
                    if (query.contains(query.replaceAll("(?i)where", "WHERE"))) {
                        qclause = (" AND " + delimeter_o + "" + event_id + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    } else {
                        qclause = (" WHERE " + delimeter_o + "" + event_id + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    }

                }

                valuesList.add(2, query + qclause);
                valuesList.add(3, "__getVendor(" + event_deviceVendor + ")");
                valuesList.add(4, "__getVendor(" + event_deviceProduct + ")");
            } else if ("MSSQL".equalsIgnoreCase(RDBMSType)) {
                String version = null;
                String version_query = capQuery(query).replaceAll("(?i)select", "SELECT TOP");//"SELECT TOP 1* FROM  " + delimeter_o + tableName + delimeter_c + "";
                String lastdate_query = (connectorType.contains("sdktbdatabase") ? "SELECT MIN(" + delimeter_o + "" + event_time + "" + "" + delimeter_c + ") FROM " + tableName + "" + clause : "SELECT MAX(" + delimeter_o + "" + event_time + "" + "" + delimeter_c + ") FROM " + tableName + "");
                String event_deviceVendor = vendor;
                String event_deviceProduct = product;
                mappingList.add(0, "version.query");
                mappingList.add(1, "lastdate.query");//SELECT DISTINCT eventtime FROM events ORDER BY eventtime DESC LIMIT 1,1
                mappingList.add(2, "query");
                mappingList.add(3, "event.deviceVendor");
                mappingList.add(4, "event.deviceProduct");
                query = capQuery(query);

                String qclause = null;
                if (connectorType.contains("sdktbdatabase")) {
                    if (query.contains(query.replaceAll("(?i)where", "WHERE"))) {
                        qclause = (" AND " + delimeter_o + "" + event_time + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    } else {
                        qclause = (" WHERE " + delimeter_o + "" + event_time + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    }
                } else if (connectorType.contains("sdkibdatabase")) {
                    if (query.contains(query.replaceAll("(?i)where", "WHERE"))) {
                        qclause = (" AND " + delimeter_o + "" + event_id + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    } else {
                        qclause = (" WHERE " + delimeter_o + "" + event_id + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    }

                }
                valuesList.add(0, version_query);
                valuesList.add(1, lastdate_query);
                valuesList.add(2, query + qclause);
                valuesList.add(3, "__getVendor(" + event_deviceVendor + ")");
                valuesList.add(4, "__getVendor(" + event_deviceProduct + ")");
            } else if ("Oracle".equalsIgnoreCase(RDBMSType)) {
                String version = capQuery(query), oclause = null;
                if (version.contains(version.replaceAll("(?i)where", "WHERE"))) {
                    oclause = (" AND ROWNUM <= 1");
                } else {
                    oclause = (" WHERE ROWNUM <= 1");
                }
                String version_query = version + oclause;
                String lastdate_query = (connectorType.contains("sdktbdatabase") ? "SELECT MIN(" + delimeter_o + "" + event_time + "" + "" + delimeter_c + ") FROM " + tableName + "" + clause : "SELECT MAX(" + delimeter_o + "" + event_time + "" + "" + delimeter_c + ") FROM " + tableName + "");
                String event_deviceVendor = vendor;
                String event_deviceProduct = product;
                mappingList.add(0, "version.query");
                mappingList.add(1, "lastdate.query");
                mappingList.add(2, "query");
                mappingList.add(3, "event.deviceVendor");
                mappingList.add(4, "event.deviceProduct");
                query = capQuery(query);

                String qclause = null;
                if (connectorType.contains("sdktbdatabase")) {
                    if (query.contains(query.replaceAll("(?i)where", "WHERE"))) {
                        qclause = (" AND " + delimeter_o + "" + event_time + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    } else {
                        qclause = (" WHERE " + delimeter_o + "" + event_time + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    }
                } else if (connectorType.contains("sdkibdatabase")) {
                    if (query.contains(query.replaceAll("(?i)where", "WHERE"))) {
                        qclause = (" AND " + delimeter_o + "" + event_id + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    } else {
                        qclause = (" WHERE " + delimeter_o + "" + event_id + "" + "" + delimeter_c + "").concat(" > \u003d ?");
                    }

                }
                valuesList.add(0, version_query);
                valuesList.add(1, lastdate_query);
                valuesList.add(2, query + qclause);
                valuesList.add(3, "__getVendor(" + event_deviceVendor + ")");
                valuesList.add(4, "__getVendor(" + event_deviceProduct + ")");
            }

            if (mappingList.size() == valuesList.size()) {
                for (int i = 0; i < valuesList.size(); i++) {
                    hashMap.put(String.valueOf(mappingList.get(i)), String.valueOf(valuesList.get(i)));

                    if (paths == null) {
                        String fname = connectorType.contains("custom.sdktbdatabase") ? "custom.sdktbdatabase.properties" : "custom.sdkibdatabase.properties";
                        paths = util.Util.FileExporter.createDirIfNotExists(userHome.replace("\\", "/") + "Documents/FileWriter/") + fname;
                    }

                    dataWriter.saveData(paths, hashMap);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setFile(String paths) {
        Writer.paths = paths;
        file = new File(paths);
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
        String sql = "SELECT ", table = MainJPanel.getInstance().getTableName();
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
            String clause = (connectorType.contains("sdktbdatabase") ? " WHERE " + delimeter_o + "" + event_time + "" + "" + delimeter_c + "" : "  WHERE " + delimeter_o + "" + event_id + "" + "" + delimeter_c + "").concat(" > \u003d ?");
            sb.setLength(sb.length() - 1);
            sb.append(" FROM ");
            sb.append(delimeter_o);
            sb.append(tableName);
            sb.append(delimeter_c);

            sb.append(clause);
            sql += sb.toString();

        }
        return sql;
    }

    private static String capQuery(String query) {
        return query.replaceAll("(?i)select", "SELECT")
                .replaceAll("(?i)from", "FROM")
                .replaceAll("(?i)where", "WHERE")
                .replaceAll("(?i)or", "OR");
    }

    @Override
    public void setParams(String rdbms, String connectorType, String tableName, String query, String vendor, String product, String optionalParam, String[] fields) {
        Writer.rdbms = rdbms;
        Writer.connectorType = connectorType;
        Writer.tableName = tableName;
        Writer.query = query;
        Writer.vendor = vendor;
        Writer.product = product;
        Writer.optionalParam = optionalParam;
        Writer.fields = fields;
    }

}
