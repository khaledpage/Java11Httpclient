package services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.Demo.httpClient.KommandoUI;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import modell.Model;

import java.util.List;

public class GetService {

	private HttpClient client;

	public GetService() {
		client = HttpClient.newHttpClient();
	}

	public List<Model> getAll(String uri) throws Exception {

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		return new ObjectMapper().readValue(response.body(), new TypeReference<List<Model>>() {
		});

	}

	public Model getByID(String uri) throws IOException, InterruptedException {

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		return new ObjectMapper().readValue(response.body(), Model.class);
	}

	List<Model> x;

	public void readAsynchronously(String uri, KommandoUI obj,Runnable lambda) {

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();

		client.sendAsync(request, BodyHandlers.ofString()).thenApply(response -> {
			System.out.println(response.statusCode());
			return response;
		}).thenApply(HttpResponse::body).thenAccept((r) -> {
			try {

				x = new ObjectMapper().readValue(r, new TypeReference<List<Model>>() {
				});
				// x.forEach(System.out::println);
				obj.Printer(x);
				lambda.run();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

}
