package com.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class PropertiesLoader {
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    private static Logger log = LoggerFactory.getLogger(PropertiesLoader.class);
    
    public static Properties loadProperties(String... resourcesPaths) {
        Properties props = new Properties();
        for (String location : resourcesPaths) {
            InputStream is = null;
            try {
                Resource resource = resourceLoader.getResource(location);
                is = resource.getInputStream();
                props.load(new BufferedReader(new InputStreamReader(is, "UTF-8")));
            } catch (IOException ex) {
                log.info(
                        "Could not load properties from path:" + location + ", " + ex.getMessage());
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
        return props;
    }
}
