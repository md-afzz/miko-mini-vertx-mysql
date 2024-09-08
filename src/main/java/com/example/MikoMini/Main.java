package com.example.MikoMini;


import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class Main {
	public static void main(String[] args) {
		
		Vertx vertx= Vertx.vertx();
		vertx.deployVerticle(MainVerticle.class.getName(), new DeploymentOptions().setInstances(1).setWorkerPoolSize(5));
	}
	
	public static void mainOld(String[] args) throws Exception {
		System.out.println("Inside main method!!");
		Vertx vertx= Vertx.vertx();
		Router router = Router.router(vertx);

	    // Mount the handler for all incoming requests at every path and HTTP method
	    Route handler1 =router.route(HttpMethod.GET, "/hello").handler(context -> {
	      // Get the address of the request
//	      String address = context.request().connection().remoteAddress().toString();
//	      // Get the query parameter "name"
//	      MultiMap queryParams = context.queryParams();
//	      String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
	      // Write a json response
//	      context.json(
//	        new JsonObject()
//	          .put("name", name)
//	          .put("address", address)
//	          .put("message", "Hello " + name + " connected from " + address)
//	      );
	     HttpServerResponse response = context.response();
	     response.setChunked(true);
	     response.write("Chunked Response");
	     context.vertx().setTimer(1000, tid-> context.next());
	      
	    });
	    
	    Route handler2= router.route(HttpMethod.GET, "/hello").handler(context->{
	    	HttpServerResponse response = context.response();
	    	response.write("   Ending Messages ");
	    	System.out.println("At last handler");
	    	response.end();
	    });

	    // Create the HTTP server
	    vertx.createHttpServer()
	      // Handle every request using the router
	      .requestHandler(router)
	      // Start listening
	      .listen(8888)
	      // Print the port
	      .onSuccess(server ->
	        System.out.println(
	          "HTTP server started on port " + server.actualPort()
	        )
	      );
//		new MainVerticle().start();
	}
}
