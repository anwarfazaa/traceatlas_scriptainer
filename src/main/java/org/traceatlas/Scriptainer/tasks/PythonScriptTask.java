package org.traceatlas.Scriptainer.tasks;

import java.io.StringWriter;

import org.python.util.PythonInterpreter;
import org.traceatlas.Scriptainer.network.PlatformRestClient;

public class  PythonScriptTask implements Runnable {
    private final String scriptPath;
    private final String scriptName;
    private final long scriptInitRunDelay;
    private final long scriptRunInterval;

    private final PlatformRestClient platformRestClient;


    // functionName name has to be passed from the configuration Yaml file
    public PythonScriptTask(String scriptPath, String scriptName, long scriptInitRunDelay, long scriptRunInterval, PlatformRestClient platformRestClient) {
        this.scriptPath = scriptPath;
        this.scriptName = scriptName;
        this.scriptRunInterval = scriptRunInterval;
        this.scriptInitRunDelay = scriptInitRunDelay;
        this.platformRestClient = platformRestClient;
    }


    // New update
    // switched to python
    @Override
    public void run() {
        PythonInterpreter pythonInterpreter = new PythonInterpreter();
        // redirecting output to variable
        StringWriter output = new StringWriter();
        pythonInterpreter.setOut(output);
        // Load the python program
        pythonInterpreter.execfile(this.scriptPath);
        // call the defined function
        // closing interpreter , resource consumptions need check;
        pythonInterpreter.close();
        // testing output
        // to be replaced with REST API communication
        System.out.println(output.toString());
        this.platformRestClient.postJson(output.toString());
    }

    public String getScriptName() {
        return this.scriptName;
    }

    public long getScriptInitRunDelay() {
        return this.scriptInitRunDelay;
    }

    public long getScriptRunInterval() {
        return this.scriptRunInterval;
    }


    // Originally call() function was intended to run external python processes
    // Many issues were faced including not being able to measure process lifecycle
    // This will still be commented for reference.
    // New logic is using jython until further investigation.
    /*
    @Override
    public String call() throws IOException, PythonExecutionException {
        String[] command = new String[args.length + 2];
        command[0] = pythonExecutable;
        command[1] = scriptPath;
        System.arraycopy(args, 0, command, 2, args.length);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String error = errorReader.readLine();
            if (error != null) {
                throw new PythonExecutionException("Error executing Python script: " + error);
            }
        }

        try (BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String result = outputReader.readLine();
            return result;
        }
    }
     */
}
