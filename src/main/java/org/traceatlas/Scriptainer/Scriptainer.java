package org.traceatlas.Scriptainer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.*;
//import okhttp3.*;
import okio.Path;
import org.traceatlas.Scriptainer.configuration.AppConfiguration;
import org.traceatlas.Scriptainer.configuration.ScriptConfiguration;
import org.traceatlas.Scriptainer.resources.ScriptsDirectoryScanner;
import org.traceatlas.Scriptainer.tasks.PythonScriptTask;
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

        List<PythonScriptTask> tasksQueue = new ArrayList<>();
        List<ScheduledFuture<?>> scheduledFutures = new ArrayList<>();

        // Execute Python scripts periodically
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
            for (String scriptFolder : scriptsDirectoryScanner.getScriptsFolders()) {
                    //String scriptFolder = scriptsDirectoryScanner.getScriptsFolders().get(i);
                    String scriptPath = scriptFolder + Path.DIRECTORY_SEPARATOR + "main.py";
                    System.out.println("Script Path: " + scriptPath);
                    // Latest update - 15-6-2023 12:37 AM
                    // Load out individual yaml file for each script configuration
                    // and submit configuration to PythonScriptTask
                    // function name is hardcoded to call for pre alpha phase

                    ScriptConfiguration.fetch(scriptFolder + Path.DIRECTORY_SEPARATOR + "config.yml");
                    PythonScriptTask task = new PythonScriptTask(scriptPath,ScriptConfiguration.scriptName, ScriptConfiguration.scriptRunInitDelay, ScriptConfiguration.scriptRunInterval);
                    tasksQueue.add(task);
                    System.out.println("Script " + ScriptConfiguration.scriptName + " Added to process queue");
            }

            for (PythonScriptTask task : tasksQueue){
                scheduledFutures.add(executor.scheduleAtFixedRate(task, task.getScriptInitRunDelay(),task.getScriptRunInterval(), TimeUnit.SECONDS));
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

