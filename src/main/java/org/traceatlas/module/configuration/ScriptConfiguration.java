package org.traceatlas.module.configuration;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.traceatlas.module.Scriptainer;
import org.yaml.snakeyaml.Yaml;

// ...

public class ScriptConfiguration {
    private static final Logger logger = Logger.getLogger(ScriptConfiguration.class.getName());
    public static int timeout;
    public static String scriptName;
    public ScriptConfiguration(int timeout, String scriptName) {
        this.timeout = timeout;
        this.scriptName = scriptName;
    }

    public static void fetch(String configPath) {
        try {
            Yaml yaml = new Yaml();
            FileInputStream fis = new FileInputStream(configPath);
            Map<String, Object> configMap = yaml.load(fis);
            timeout = (int) configMap.get("Timeout");
            scriptName = (String) configMap.get("ScriptName");
        } catch (Exception e) {
            logger.warning("Error: Failed to read the configuration file from folder : " + e.getMessage());
        }
    }
}




