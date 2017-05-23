package de.htwg.blackjack.view.http;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import de.htwg.blackjack.controller.IController;

public class AkkaHttp extends AllDirectives{
	IController controller;
	public static void main(String[] args) throws IOException {
		ActorSystem system = ActorSystem.create("routes");
		
		final Http http = Http.get(system);
		final ActorMaterializer materializer = ActorMaterializer.create(system);
		
		AkkaHttp app = new AkkaHttp();
		
		final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow(system, materializer);
	    final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
	        ConnectHttp.toHost("localhost", 8080), materializer);
		
		System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
	    System.in.read(); // let it run until user presses return

		binding
	      .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
	      .thenAccept(unbound -> system.terminate()); // and shutdown when done
		
	}
	
	private Route createRoute() {
	    return route(
	        path("hello", () ->
	            get(() ->
	                complete("<h1>hello</h1>"))),
	    
		    path("blackjack", () ->
	        	get(() ->
	        		complete("blackjack"))));
	  }	
}
