package com.example.MikoMini;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class RESTVerticle extends AbstractVerticle {

	
	// AbstractVerticle class implements Verticle
//	vertx and context variables....
	
	
	// vertx objects is inherited from AbstractVerticle
//	@Override 
	public void startOld(Promise<Void> startPromise) throws Exception {
		vertx.createHttpServer().requestHandler(req -> {
			req.response()
			.putHeader("content-type", "text/plain") .end("Hello from Vert.x!");
		}).listen(8888).onComplete(http -> {
			if (http.succeeded()) {
				startPromise.complete();
				System.out.println("HTTP server started on port 8888"); 
			} else {
				startPromise.fail(http.cause()); 
			} 
		}); 
	}
	 
	
	@Override
	public void start() throws Exception {
		// Create a Router
		Router router = Router.router(vertx);

		// Mount the handler for all incoming requests at every path and HTTP method
		router.route().handler(context -> {
//			System.out.println("Inside handler---!!!");
			// Get the address of the request
			String address = context.request().connection().remoteAddress().toString();
			// Get the query parameter "name"
			MultiMap queryParams = context.queryParams();
			String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
			// Write a json response
			context.json(
					new JsonObject()
					.put("name", name)
					.put("address", address)
					.put("message", "Hello " + name + " connected from " + address)
					);
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
	}
	
}
