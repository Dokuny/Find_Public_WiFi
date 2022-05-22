package com.dokuny.find_public_wifi.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

    public static Properties getProp(String property) {
        Properties prop = null;
        try {
            prop = new Properties();
            InputStream is = PropertyManager.class.getClassLoader().getResourceAsStream(property);
            prop.load(is);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
