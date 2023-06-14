package org.traceatlas.Scriptainer.tasks;
/*
    This is another possible and high performance scripting tool that can be added to Scriptainer
    Reasons:
        1- Syntax is easy.
        2- capable of using built in java libs
 */
public class GroovyScriptTask implements Runnable {
    private final String scriptPath;
    private final String scriptName;
    private final long scriptInitRunDelay;
    private final long scriptRunInterval;


    // functionName name has to be passed from the configuration Yaml file
    public GroovyScriptTask(String scriptPath, String scriptName, long scriptInitRunDelay, long scriptRunInterval) {
        this.scriptPath = scriptPath;
        this.scriptName = scriptName;
        this.scriptRunInterval = scriptRunInterval;
        this.scriptInitRunDelay = scriptInitRunDelay;
        //this.scriptContent = String.valueOf(new ScriptReader(scriptPath));
    }

    @Override
    public void run() {


    }
}
