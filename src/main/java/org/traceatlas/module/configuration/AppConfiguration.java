package org.traceatlas.module.configuration;

import org.traceatlas.module.Scriptainer;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Logger;

public class AppConfiguration {

    private static final Logger logger = Logger.getLogger(Scriptainer.class.getName());
    private String filePath;
    private int maxMemoryAllowed;

    private String jarPath;

    public AppConfiguration() {
        try {
            getAppConfigPath();
            Yaml yaml = new Yaml();
            FileInputStream fis = new FileInputStream(this.filePath);
            Map<Object, Object> config = yaml.load(fis);
            maxMemoryAllowed = (int) config.get("MaxMemory");

        } catch (Exception e) {
            logger.warning("Error: Failed to read the configuration file: " + e.getMessage());
        }
    }

    private void getAppConfigPath() throws URISyntaxException {

        String currentDir = new File(Scriptainer.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getAbsolutePath();
        this.jarPath = currentDir + File.separator + "scripts" + File.separator;
        String filePath = currentDir + File.separator + "config.yml";
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

    public String getJarPath() { return this.jarPath; }

}
