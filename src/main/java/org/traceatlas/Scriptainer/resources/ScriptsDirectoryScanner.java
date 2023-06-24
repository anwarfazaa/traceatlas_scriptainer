package org.traceatlas.Scriptainer.resources;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScriptsDirectoryScanner {

    private final List<String> scriptsFolders;

    public ScriptsDirectoryScanner(String rootFolder) {
        this.scriptsFolders = scanScriptFolders(rootFolder);
    }

    private List<String> scanScriptFolders(String rootFolder) {
        List<String> scriptFolders = new ArrayList<>();
        File root = new File(rootFolder);

        if (root.isDirectory()) {
            File[] subdirectories = root.listFiles(File::isDirectory);
            if (subdirectories != null) {
                for (File subDir : subdirectories) {
                    scriptFolders.add(subDir.getAbsolutePath());
                }
            }
        }
        return scriptFolders;
    }

    public List<String> getScriptsFolders() {
        return scriptsFolders;
    }
}
