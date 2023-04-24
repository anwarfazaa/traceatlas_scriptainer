package org.traceatlas.module.configuration;
import java.io.File;
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
            Path path = Paths.get(configPath);
            String content = path.toString();
            Yaml yaml = new Yaml();
            Map<String, Object> configMap = yaml.load(content);
            timeout = (int) configMap.get("timeout");
            scriptName = (String) configMap.get("script_name");
        } catch (Exception e) {
            logger.warning("Error: Failed to read the configuration file from folder : " + configPath);
        }
    }
}




