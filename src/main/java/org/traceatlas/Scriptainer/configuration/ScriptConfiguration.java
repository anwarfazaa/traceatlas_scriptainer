package org.traceatlas.Scriptainer.configuration;
import java.io.FileInputStream;
import java.util.Map;
import java.util.logging.Logger;

import org.yaml.snakeyaml.Yaml;

// ...

public class ScriptConfiguration {
    private static final Logger logger = Logger.getLogger(ScriptConfiguration.class.getName());
    public static int timeout;
    public static String scriptName;
    public static long scriptRunInterval;
    public static long scriptRunInitDelay;
    /*public ScriptConfiguration(int timeout, String scriptName) {
        this.timeout = timeout;
        this.scriptName = scriptName;
    }*/

    public static void fetch(String configPath) {
        try {
            Yaml yaml = new Yaml();
            FileInputStream fis = new FileInputStream(configPath);
            Map<String, Object> configMap = yaml.load(fis);
            //timeout = (int) configMap.get("Timeout");
            scriptName = (String) configMap.get("ScriptName");
            scriptRunInterval = (int) configMap.get("ScriptRunInterval");
            scriptRunInitDelay = (int) configMap.get("ScriptRunInitDelay");
        } catch (Exception e) {
            logger.warning("Error: Failed to read the configuration file from folder : " + e.getMessage());
        }
    }
}




