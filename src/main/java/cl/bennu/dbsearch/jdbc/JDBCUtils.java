package cl.bennu.dbsearch.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {

    public static Connection open() throws Exception {
        Class.forName("net.sourceforge.jtds.jdbcx.JtdsDataSource");

        String url = "jdbc:jtds:sqlserver://172.28.6.163:1433;DatabaseName=BTCSHPCDB";
        String user = "user_hipotecario";
        String pass = "hipoteca2012";

        url = "jdbc:jtds:sqlserver://localhost:1433;DatabaseName=SMHE";
        user = "sa";
        pass = "11042.";

        return DriverManager.getConnection(url, user, pass);
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            // no cerro
        }
    }

}
