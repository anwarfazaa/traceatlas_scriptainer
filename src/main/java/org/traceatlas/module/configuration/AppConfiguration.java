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
    private String platformURL;
    private String jarPath;

    public AppConfiguration() {
        try {
            getAppConfigPath();
            Yaml yaml = new Yaml();
            FileInputStream fis = new FileInputStream(this.filePath);
            Map<Object, Object> config = yaml.load(fis);
            maxMemoryAllowed = (int) config.get("MaxMemory");
            platformURL = (String) config.get("PlatformURL");

        } catch (Exception e) {
            System.out.println("Error: Failed to read the configuration file: " + e.getMessage());
        }
    }

    private void getAppConfigPath() throws URISyntaxException {

        File currentDir = new File(Scriptainer.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        this.jarPath = currentDir.getParentFile().getPath() + File.separator + "scripts" + File.separator;
        String filePath = currentDir.getParentFile().getPath() + File.separator + "config.yml";
        File configFile = new File(filePath);
        if (configFile.exists()) {
            this.filePath = filePath;
        } else {
            System.out.println("Error: config.yml not found in the current directory " + filePath);
        }
    }


    public long getMaxMemory() {
        return this.maxMemoryAllowed;
    }

    public String getJarPath() { return this.jarPath; }

    public String getPlatformURL() { return this.platformURL; }

}
