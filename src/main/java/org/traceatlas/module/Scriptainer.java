package org.traceatlas.module;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.*;
//import okhttp3.*;
import org.traceatlas.module.configuration.AppConfiguration;
import org.traceatlas.module.configuration.ScriptConfiguration;
import org.traceatlas.module.resources.ScriptsDirectoryScanner;
import org.traceatlas.module.tasks.PythonScriptTask;
import java.util.concurrent.atomic.AtomicBoolean;


public class Scriptainer {
    private static final Logger logger = Logger.getLogger(Scriptainer.class.getName());
    private static AtomicBoolean running = new AtomicBoolean(true);

    private static int runInterval = 1;

    private static String mainScriptsFolder;

    public static void main(String[] args) {
        // Change these paths according to your application structure
        String apiUrl = "http://your_rest_api_service.com/endpoint";
        AppConfiguration configuration = new AppConfiguration();
        ScriptsDirectoryScanner scriptsDirectoryScanner = new ScriptsDirectoryScanner(configuration.getJarPath());



        ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
        List<ScheduledFuture<String>> futures = new ArrayList<>();
        List<Boolean> scriptIsRunning = new ArrayList<>();

        List<Integer> timeouts = new ArrayList<>(); // Individual timeouts for each script in seconds
        List<String> scriptNames = new ArrayList<>();

        // Load timeout values and script names from config.yml files and initialize scriptIsRunning
        for (String scriptFolder : scriptsDirectoryScanner.getScriptsFolders()) {
            System.out.println("Script found at : " + scriptFolder);
            String configPath = scriptFolder + "/config.yml";
            ScriptConfiguration.fetch(configPath);
            timeouts.add(ScriptConfiguration.timeout);
            scriptNames.add(ScriptConfiguration.scriptName);
            scriptIsRunning.add(false);
        }

        // Execute Python scripts periodically
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            for (int i = 0; i < scriptsDirectoryScanner.getScriptsFolders().size(); i++) {
                if (!scriptIsRunning.get(i)) {
                    String scriptFolder = scriptsDirectoryScanner.getScriptsFolders().get(i);
                    String scriptPath = scriptFolder + "/main.py";
                    // TO BE CREATED
                    // Load out individual yaml file for each script configuration
                    // and submit configuration to PythonScriptTask
                    // function name is hardcoded to call for pre alpha phase
                    PythonScriptTask task = new PythonScriptTask(scriptPath,"call");
                    ScheduledFuture<String> future = (ScheduledFuture<String>) executor.submit(task);
                    futures.add(future);
                    scriptIsRunning.set(i, true);

                    logger.info("Script " + scriptNames.get(i) + " started running");
                }
            }
        }, 0, runInterval, TimeUnit.SECONDS);

        // Periodically check memory usage

        ScheduledExecutorService memoryCheckExecutor = Executors.newScheduledThreadPool(1);
        memoryCheckExecutor.scheduleAtFixedRate(() -> {
            long usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
            if (usedMemory > configuration.getMaxMemory()) {
                logger.warning("Error: Used memory exceeds the threshold (" + usedMemory + " MB)");
                running.set(false);
            }
        }, 0, 10, TimeUnit.SECONDS);

        // Collect results with individual timeouts and send to REST API
        while (running.get()) {
            for (int i = 0; i < futures.size(); i++) {
                ScheduledFuture<String> future = futures.get(i);
                int timeout = timeouts.get(i);
                try {
                    String result = future.get(timeout, TimeUnit.SECONDS);
                    sendToRestApi(apiUrl, scriptNames.get(i), result);
                    scriptIsRunning.set(i, false);

                    logger.info("Script " + scriptNames.get(i) + " stopped running");
                } catch (TimeoutException e) {
                    logger.warning("Error: Task " + (i + 1) + " timed out");
                    scriptIsRunning.set(i, false);
                } catch (InterruptedException | ExecutionException e) {
                    logger.warning("Error: " + e.getMessage());
                    scriptIsRunning.set(i, false);
                }
            }
            futures.clear();

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

