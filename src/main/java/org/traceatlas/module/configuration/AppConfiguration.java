package org.traceatlas.module.configuration;

import org.traceatlas.module.Scriptainer;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Logger;

public class AppConfiguration {

    private static final Logger logger = Logger.getLogger(Scriptainer.class.getName());
    private String filePath;
    private long maxMemoryAllowed;

    public AppConfiguration() {
        try {
            getAppConfigPath();
            Path path = Paths.get(this.filePath);
            String content = path.toString();
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(content);
            maxMemoryAllowed = (long) config.get("max_memory");
        } catch (Exception e) {
            logger.warning("Error: Failed to read the configuration file");
        }
    }

    private void getAppConfigPath() {
        String currentDir = System.getProperty("user.dir");
        String filePath = currentDir + File.separator + "app-conf.yml";
        File configFile = new File(filePath);
        if (configFile.exists()) {
            this.filePath = filePath;
        } else {
            logger.warning("Error: app-conf.yml not found in the current directory");
        }
    }


    public long getMaxMemory() {
        return this.maxMemoryAllowed;
    }

}
