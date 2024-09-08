package com.example.MikoMini;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

public class MainVerticle extends AbstractVerticle{
	
	@Override
	public void start() throws Exception{
		DeploymentOptions options= new DeploymentOptions();
		options.setInstances(1);
//		.setWorkerPoolSize(5)
		vertx.deployVerticle(DatabaseVerticle.class.getName(), options );
		
		
//		24*3600*1000 for per day
//		vertx.setPeriodic(5000, td->{
//			System.out.println("Inside periodic call!!");
//			try {
//				System.out.println("First Attempt!!!");
////				vertx.eventBus().send("database", "MAIN_WORK");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
	}
}
