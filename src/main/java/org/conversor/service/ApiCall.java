package org.conversor.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.net.http.HttpResponse.*;
import java.util.concurrent.CompletableFuture;

public class ApiCall {
  private String json;


  protected String Fetch(String uri) {
    HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .timeout(Duration.ofSeconds(30))
            .GET()
            .build();

    CompletableFuture<HttpResponse<String>> resFuture = client.sendAsync(request, BodyHandlers.ofString());
    resFuture
            .thenApply(HttpResponse::body)
            .thenAccept(res -> {
              json = res;
            }).join();

    resFuture
            .thenApply(HttpResponse::statusCode)
            .thenAccept(code -> {
              if ((code == 200))
                System.out.println("Estado de la solicitud: OK");
              else {
                System.err.println(STR."Estado de la solicitud rechazada, code: \{code.toString()}");
                throw new RuntimeException();
              }
            });
    return json;
  }
}
