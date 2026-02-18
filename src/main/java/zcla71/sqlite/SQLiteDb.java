package zcla71.sqlite;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Ferramenta para abrir arquivos .SQLite3 online: https://sqliteviewer.app/
public class SQLiteDb {
    private String path;

    public SQLiteDb(String path) {
        this.path = path;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + this.path);
    }

    public Collection<String> getTableNames(Connection conn) throws SQLException {
        Collection<String> result = new ArrayList<>();
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet tables = metaData.getTables(null, null, null, null);
        while (tables.next()) {
            result.add(tables.getString("TABLE_NAME"));
        }
        return result.stream().filter(tn -> !tn.startsWith("sqlite_")).toList();
    }

    public <T> List<T> getData(Connection conn, String tableName, Class<T> classe) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
        List<T> result = new ArrayList<>();
        try (
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from " + tableName);
        ) {
            ResultSetMetaData metaData = rs.getMetaData();
            while(rs.next()) {
                T object = classe.getDeclaredConstructor().newInstance();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String colName = metaData.getColumnName(i);
                    Object value = rs.getObject(colName);
                    Field field = classe.getDeclaredField(colName);
                    field.setAccessible(true);
                    try {
                        field.set(object, value);
                    } catch (IllegalArgumentException e) {
                        boolean tratado = false;
                        // Boolean
                        if (field.getType().equals(Boolean.class)) {
                            // Converte de Integer
                            if (value instanceof Integer iValue) {
                                value = !iValue.equals(0);
                                field.set(object, value);
                                tratado = true;
                            }
                        }
                        // Integer
                        if (field.getType().equals(Integer.class)) {
                            // Converte de String
                            if (value instanceof String sValue) {
                                if (sValue.length() == 0) {
                                    value = null;
                                } else {
                                    value = Integer.parseInt(sValue);
                                }
                                field.set(object, value);
                                tratado = true;
                            }
                        }
                        // Se não conseguiu, relança o erro
                        if (!tratado) {
                            throw e;
                        }
                    }
                }
                result.add(object);
            }
        }
        return result;
    }
}
