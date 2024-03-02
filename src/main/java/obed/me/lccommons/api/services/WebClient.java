package obed.me.lccommons.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class WebClient {
    private static volatile WebClient instance;
    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private static final String BASE_ENDPOINT = "http://localhost";

    private WebClient() {
        this.mapper = new ObjectMapper();
        this.httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    }

    public static WebClient getInstance(){
        if(instance == null){
            synchronized (WebClient.class){
                if(instance == null)
                    instance = new WebClient();
            }
        }
        return instance;
    }
    public <E, T> E create(String endpoint, T body, Class<E> responseType) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_ENDPOINT.concat(endpoint))).
                    header("Content-Type", "application/json").POST
                            (HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body))).build();
            return mapper.readValue(httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body(), responseType);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <E> E get(String endpoint, Class<E> responseType) {
        try {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_ENDPOINT.concat(endpoint))).
                    header("Content-Type", "application/json").GET().build();
            return mapper.readValue(httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body(), responseType);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(String endpoint) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_ENDPOINT.concat(endpoint))).
                header("Content-Type", "application/json").DELETE().build();
        try {
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    }
