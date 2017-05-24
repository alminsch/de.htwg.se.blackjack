package de.htwg.blackjack.view.http;

import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.regex.Pattern;

import com.google.inject.Inject;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import de.htwg.blackjack.Blackjack;
import de.htwg.blackjack.controller.IController;

public class AkkaHttp extends AllDirectives{
	private IController controller;
	private final CompletionStage<ServerBinding> binding;
	private ActorSystem system;
	
	@Inject
	public AkkaHttp(final IController controller) throws IOException {
		this.controller = controller;
		system = ActorSystem.create("routes");
		
		final Http http = Http.get(system);
		final ActorMaterializer materializer = ActorMaterializer.create(system);
		
		
		final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = this.createRoute().flow(system, materializer);
	    binding = http.bindAndHandle(routeFlow,
	        ConnectHttp.toHost("localhost", 8080), materializer);
		System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
	}
	
	public void unbinding() {
		binding
	      .thenCompose(ServerBinding::unbind) 
	      .thenAccept(unbound -> system.terminate());
	}
	
	private Route createRoute() {
	    return route(
	        path("blackjack", () ->
	            get(() ->
	                complete(controller.getJson()))),
		    path(PathMatchers.segment("blackjack").slash(PathMatchers.segment(Pattern.compile("\\D+"))), value ->
		    	get(() ->		    		
		    		complete(Blackjack.getInstance().getTUI().userinputselection(value) + controller.getJson()))));
	  }	
}
