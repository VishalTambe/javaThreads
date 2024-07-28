package com.java.example.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HttpClientTest {

	public static void main(String[] args) throws URISyntaxException, InterruptedException, ExecutionException {

		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder().GET().uri(new URI("https://httpbin.org/delay/2")).build();

		CompletableFuture pipeline = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
				.whenComplete((r, throwable) -> {
					if (throwable == null) {
						if (r.statusCode() >= 400) {
							throw new RuntimeException("Http Request Repsponded with error");
						}
					}
				}).thenApply(r -> r.body()).thenAccept(System.out::println);
	//pipeline.complete(pipeline);
	pipeline.join();

	}

}
