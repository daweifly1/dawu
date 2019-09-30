package top.chendawei.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigUtil {
    private static Map<String, String> map = new HashMap();

    static {
        Properties prop = new Properties();
        URL url = ConfigUtil.class.getClassLoader().getResource("app.properties");
        try {
            InputStream in = url.openStream();
            prop.load(in);
            for (Object key : prop.keySet()) {
                map.put(key.toString(), prop.getProperty(key.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return map.get(key);
    }
}
