package services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

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
	   
	    HttpRequest request = HttpRequest.newBuilder()
	          .uri(URI.create(uri))
	          .build();

	    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());


		return new ObjectMapper().readValue(response.body(), new TypeReference<List<Model>>() {});
	    
	}
	
	
	public Model getByID(String uri) throws IOException, InterruptedException {
		
		   HttpRequest request = HttpRequest.newBuilder()
			          .uri(URI.create(uri))
			          .build();

		HttpResponse<String> res = client.send(request, BodyHandlers.ofString());
		
		return new ObjectMapper().readValue(res.body(), Model.class);
	}
	

}
