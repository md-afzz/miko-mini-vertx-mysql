package com.example.MikoMini.db.entity;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLBuilder;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;

public class DbConfigs {
	// Pool or SqlClient
		public static SqlClient getClient(Vertx vertx) {
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

		public static Pool getConnectionPool(Vertx vertx) {
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
		

}
