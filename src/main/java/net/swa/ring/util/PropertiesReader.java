package net.swa.ring.util;

import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

public class PropertiesReader {
    private String fileName;

    public PropertiesReader(String fileName) {
        this.fileName = fileName;
    }

    public String readProperty(String name) {
        ClassPathResource res = new ClassPathResource(this.fileName);
        Properties p = new Properties();
        try {
            p.load(res.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p.getProperty(name);
    }
}
