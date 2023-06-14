package org.traceatlas.Scriptainer.resources;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class ScriptReader {

    private final String scriptContent;

    public ScriptReader(String scriptLocation) {
        Path ScriptLocation = Paths.get(scriptLocation);
        try {
            List<String> lines = Files.readAllLines(ScriptLocation);
            scriptContent = String.join("\n", lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getScriptContent() {
        return scriptContent;
    }

}
