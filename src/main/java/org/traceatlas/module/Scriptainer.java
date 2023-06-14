package org.traceatlas.module;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.*;
//import okhttp3.*;
import okio.Path;
import org.python.core.Py;
import org.python.modules._threading._threading;
import org.traceatlas.module.configuration.AppConfiguration;
import org.traceatlas.module.configuration.ScriptConfiguration;
import org.traceatlas.module.resources.ScriptsDirectoryScanner;
import org.traceatlas.module.tasks.PythonScriptTask;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;


public class Scriptainer {
    private static final Logger logger = Logger.getLogger(Scriptainer.class.getName());
    private static AtomicBoolean running = new AtomicBoolean(true);

    private static int runInterval = 1;

    private static String mainScriptsFolder;

    public static void main(String[] args) throws InterruptedException {
        // Change these paths according to your application structure

        AppConfiguration configuration = new AppConfiguration();
        ScriptsDirectoryScanner scriptsDirectoryScanner = new ScriptsDirectoryScanner(configuration.getJarPath());
        String apiUrl = configuration.getPlatformURL();

        // corePoolSize will need to be picked up from Configuration
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
        List<Boolean> scriptIsRunning = new ArrayList<>();

        List<Integer> timeouts = new ArrayList<>(); // Individual timeouts for each script in seconds
        List<String> scriptNames = new ArrayList<>();

        // Load timeout values and script names from config.yml files and initialize scriptIsRunning
        for (String scriptFolder : scriptsDirectoryScanner.getScriptsFolders()) {
            System.out.println("Script found at : " + scriptFolder);
            String configPath = scriptFolder + "\\config.yml";
            ScriptConfiguration.fetch(configPath);
            timeouts.add(ScriptConfiguration.timeout);
            scriptNames.add(ScriptConfiguration.scriptName);
            scriptIsRunning.add(false);
        }

        List<PythonScriptTask> tasksQueue = new ArrayList<>();
        List<ScheduledFuture<?>> scheduledFutures = new ArrayList<>();

        // Execute Python scripts periodically
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
            for (int i = 0; i < scriptsDirectoryScanner.getScriptsFolders().size(); i++) {
                if (!scriptIsRunning.get(i)) {
                    String scriptFolder = scriptsDirectoryScanner.getScriptsFolders().get(i);
                    String scriptPath = scriptFolder + Path.DIRECTORY_SEPARATOR + "main.py";
                    System.out.println("Script Path: " + scriptPath);
                    // TO BE CREATED
                    // Load out individual yaml file for each script configuration
                    // and submit configuration to PythonScriptTask
                    // function name is hardcoded to call for pre alpha phase
                    PythonScriptTask task = new PythonScriptTask(scriptPath,"call");
                    tasksQueue.add(task);
                    System.out.println("Script " + scriptNames.get(i) + " Added to process queue");
                }
            }

            for (PythonScriptTask task : tasksQueue){
                scheduledFutures.add(executor.scheduleAtFixedRate(task, 0,5, TimeUnit.SECONDS));
            }




        // Periodically check memory usage
        // commented until later stage
        /*
        ScheduledExecutorService memoryCheckExecutor = Executors.newScheduledThreadPool(1);
        memoryCheckExecutor.scheduleAtFixedRate(() -> {
            long usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
            if (usedMemory > configuration.getMaxMemory()) {
                System.out.println("Error: Used memory exceeds the threshold (" + usedMemory + " MB)");
                running.set(false);
            }
        }, 0, 10, TimeUnit.SECONDS);
        */

        // Collect results with individual timeouts and send to REST API
        while (running.get()) {
            // Keep the process running

        }

        // Shutdown the executor services
        executor.shutdown();
        scheduler.shutdown();
        //memoryCheckExecutor.shutdown();
    }



    public static void sendToRestApi(String url, String scriptName, String data) {
        System.out.println(data);
        /*
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String jsonData = "{\"script_name\": \"" + scriptName + "\", \"data\": " + data + "}";
        RequestBody body = RequestBody.create(JSON, jsonData);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                logger.warning("Error: Failed to send data to the REST API");
            }
        } catch (IOException e) {
            logger.warning("Error: Failed to send data to the REST API");
        }

         */
    }


}

