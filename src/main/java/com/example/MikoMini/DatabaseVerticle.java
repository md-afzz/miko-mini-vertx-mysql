package com.example.MikoMini;

import java.time.format.DateTimeFormatter;

import com.example.MikoMini.db.entity.AppInfo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.PropertyKind;
import io.vertx.mysqlclient.MySQLBuilder;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;

public class DatabaseVerticle extends AbstractVerticle{
	
	// Pool or SqlClient
	public SqlClient getClient() {
		MySQLConnectOptions connectOptions = new MySQLConnectOptions()
				.setPort(3306)
				.setHost("localhost")
				.setDatabase("mdafjaldb")
				.setUser("root")
				.setPassword("MDafjal22#")
				//			  .setPipeliningLimit(16)
				;


		// Pool options
		PoolOptions poolOptions = new PoolOptions()
				//					.setConnectionTimeout(2000)
				//					.setIdleTimeout(1000)
				.setMaxSize(5);

		// Create the client pool
		SqlClient client = MySQLBuilder
				.client()
				.with(poolOptions)
				.connectingTo(connectOptions)
				.build();

		return client;
	}

	public Pool getConnectionPool() {
		MySQLConnectOptions connectOptions = new MySQLConnectOptions()
				.setPort(3306)
				  .setHost("localhost")
				  .setDatabase("mdafjaldb")
				  .setUser("root")
				  .setPassword("MDafjal22#")
				  .setPipeliningLimit(16)
				  ;

		// Pool options
		PoolOptions poolOptions =  new PoolOptions().setConnectionTimeout(2000)
				.setIdleTimeout(1000)
		  .setMaxSize(5);
		// Create the pooled client
		Pool client = MySQLBuilder.pool()
				.with(poolOptions)
				.connectingTo(connectOptions)
				.using(vertx)
				.build();

		return client;
	}
	
	public void startCl() throws Exception{
		System.out.println("Inside database start!!!");
		SqlClient client =getClient();
		System.out.println("Client "+client.toString());
		client.query("SELECT * FROM app_info ")
	    .execute()
		  .onComplete(ar -> {
		    if (ar.succeeded()) {
		      RowSet<Row> result = ar.result();
		      System.out.println(result.columnsNames());
		      System.out.println("Got " + result.size() + " rows ");
		    } else {
		      System.out.println("Failure: " + ar.cause().getMessage());
		    }

		    // Now close the pool
		    client.close();
		  });
		System.out.println("Ending method !!! start() in database verticles!!");
	}

//	@Override
	public void start() throws Exception{
		System.out.println("Inside database start!!");
		
		
		vertx.deployVerticle(AppInstallationVerticle.class.getName(), new DeploymentOptions().setInstances(1));//.setWorkerPoolSize(5)
		
		// Implementation for fetching all rowsets from Database table APP
		Pool pool = getConnectionPool();
		System.out.println("Pool created with size "+pool.size());
		// Get a connection from the pool
		pool.getConnection().compose(conn -> {
		  System.out.println("Got a connection from the pool");

		  // All operations execute on the same connection
		  return conn
		    .query("select * from app_info where app_state= 'SCHEDULED'")
		    .execute()
//		    .compose(res -> conn
//		      .query("SELECT * FROM users WHERE id='emad'")
//		      .execute())
		    .onComplete(ar -> {
		      // Release the connection to the pool
		      conn.close();
		    });
		}).onComplete(ar -> {
		  if (ar.succeeded()) {
			 RowSet<Row> rows= ar.result();
			  System.out.println("Number of Rows "+rows.size()+"  "+rows.columnsNames());
			  for(Row r: rows) {
				  System.out.println("app_name "+r.getString(1));
				  //
//				  AppInfo entity= new AppInfo(r.getInteger(0), r.getString(1), r.getString(2), 
//						  r.getLocalDate("created_at"), r.getLocalDate("updated_at"),
//						  r.getString("update_available"), r.getInteger("numOfAttempts"));
				  vertx.eventBus().send("app.install", String.format("%s", r.getString(1)));
				  
//				  if(r.getString("app_state").toString().equals("SCHEDULED")) {
//					  System.out.println("----- CALL HERE for INSTALLATION");
//				  }else
//				  {
//					  System.out.println("NO NEED TO INSTALL"+r.getString("app_state").toString());
//				  }
			  }
			  // On completion , closing pool....
//			  pool.close();
		  } else {
		    System.out.println("Something went wrong " + ar.cause().getMessage());
		  }
		  pool.close();
		});
		
		// For Each , rows make a call to APP_INSTALLTION verticle and then based on responses , updates query run on corresponding
		
		// At last , closing , client....
//		pool.close();
	}
}
