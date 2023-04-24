package org.traceatlas.module.tasks;

import org.traceatlas.module.exceptions.PythonExecutionException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class  PythonScriptTask implements Callable<String> {
    private final String pythonExecutable;
    private final String scriptPath;
    private final String[] args;

    public PythonScriptTask(String pythonExecutable, String scriptPath, String... args) {
        this.pythonExecutable = pythonExecutable;
        this.scriptPath = scriptPath;
        this.args = args;
    }

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
}

