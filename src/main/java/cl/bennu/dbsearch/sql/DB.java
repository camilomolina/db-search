package cl.bennu.dbsearch.sql;

import cl.bennu.dbsearch.jdbc.JDBCUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class DB {


    public static List<String> getDependence(String sp) {
        List<String> spList = null;
        try {
            String sql = "    SELECT distinct so.name\n" +
                    "    FROM sysobjects so\n" +
                    "    INNER JOIN syscomments sc ON so.id = sc.id\n" +
                    "    WHERE sc.text LIKE '%" + sp + "%'\n";

            Connection connection = JDBCUtils.open();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            spList = new ArrayList<String>();
            while (resultSet.next()) {
                String dependence = resultSet.getString(1);

                if (!dependence.equalsIgnoreCase(sp)) {

                    spList.add(dependence);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return spList;
    }


    public static Map<String, ?> getDependences(String sp) {
        Map<String, Object> map = null;
        try {
            String sql = "    SELECT distinct so.name\n" +
                    "    FROM sysobjects so\n" +
                    "    INNER JOIN syscomments sc ON so.id = sc.id\n" +
                    "    WHERE sc.text LIKE '%" + sp + "%'\n";

            Connection connection = JDBCUtils.open();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            map = new HashMap<String, Object>();
            while (resultSet.next()) {
                String dependence = resultSet.getString(1);

                if (!dependence.equalsIgnoreCase(sp)) {
                    List l = getDependence(dependence);
                    if (l == null || l.size() == 0) {
                        map.put(dependence, dependence);
                    } else {
                        map.put(dependence, getDependences(dependence));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}
