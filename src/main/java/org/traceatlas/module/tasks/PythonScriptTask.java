package org.traceatlas.module.tasks;

import org.traceatlas.module.exceptions.PythonExecutionException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import org.python.util.PythonInterpreter;
import org.python.core.*;
import org.traceatlas.module.resources.ScriptReader;

public class  PythonScriptTask implements Callable<String> {
    private final String scriptPath;
    private final String functionName;

    //private final String scriptContent;


    // functionName name has to be passed from the configuration Yaml file
    public PythonScriptTask( String scriptPath, String functionName) {
        this.scriptPath = scriptPath;
        this.functionName = functionName;
        //this.scriptContent = String.valueOf(new ScriptReader(scriptPath));
    }


    // New update
    // switched to python
    @Override
    public String call() {
        try (PythonInterpreter pythonInterpreter = new PythonInterpreter()){
            // Load the python program
            pythonInterpreter.execfile(this.scriptPath);

            // call the defined function
            PyObject pyObject = pythonInterpreter.get(this.functionName);
            PyObject result = pyObject.__call__();
            return result.toString();
        } catch (Exception ex) {
            return ex.getMessage();
        }
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

