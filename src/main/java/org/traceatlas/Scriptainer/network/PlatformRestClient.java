package org.traceatlas.Scriptainer.network;


import com.google.gson.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class PlatformRestClient {

    // private static final HttpClient client = new HttpClient();
    private static final WebClient platformEndpoint = WebClient.create("http://localhost:8080/scriptainer_data");


    public void postJson(String jsonObject) {
        Mono<String> response = platformEndpoint.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonObject)
                .retrieve()
                .bodyToMono(String.class);

        String responseStr = response.block();
        System.out.println(responseStr);
    }


}
