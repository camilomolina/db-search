package cl.bennu.dbsearch;

import cl.bennu.dbsearch.sql.DB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AppSearch {
    //static Gson GSON = new Gson();
    static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {

        //Dependencias sin recursividad
        List<String> list = DB.getDependence("ufn_get_ValorUF");
        for (String s : list) {
            System.out.println(s);
        }

        System.out.println(StringUtils.LF);

        //Dependencias con recursividad
        Map map = DB.getDependences("ufn_get_ValorUF");
        if (map != null) {
            StringBuffer stringBuffer = new StringBuffer();
            printMap(map, stringBuffer, 0);

            String json = GSON.toJson(map);
            System.out.println(json);

            try {
                FileUtils.writeStringToFile(new File("dependences.json"), json);
                FileUtils.writeStringToFile(new File("dependences.txt"), stringBuffer.toString());

                byte[] serialize = SerializationUtils.serialize((Serializable) map);
                FileUtils.writeByteArrayToFile(new File("dependences.ser"), serialize);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void printMap(Map map, StringBuffer stringBuffer, int level) {
        if (map == null) return;

        String levelSpace = StringUtils.repeat(StringUtils.SPACE + StringUtils.SPACE, level);

        for (Object o : map.keySet()) {
            System.out.println(levelSpace + o);

            stringBuffer.append(levelSpace);
            stringBuffer.append(o);
            stringBuffer.append(StringUtils.LF);

            Object o2 = map.get(o);
            if (o2 instanceof Map) {
                level++;
                printMap((Map) o2, stringBuffer, level);
                level--;
            }
        }
    }

}
