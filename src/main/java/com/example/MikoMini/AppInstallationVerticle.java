package com.example.MikoMini;

import com.example.MikoMini.db.entity.AppInfo;
import com.example.MikoMini.db.entity.DbConfigs;

import io.vertx.core.AbstractVerticle;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Tuple;

public class AppInstallationVerticle extends AbstractVerticle{
	@Override
	public void start() throws Exception{
		AppInfo appInfo= new AppInfo();
		vertx.deployVerticle(MailReleaseVerticle.class.getName());
		// String app_name ="";
		vertx.eventBus().consumer("app.install", msg->{
			System.out.println("msg body received in AppInstallationVerticle consumer eventbus!!!"+msg.body().toString());
			// STRING variable -- app_name= msg.body().toString();
			appInfo.setApp_name(msg.body().toString());
			updatedState(appInfo.getApp_name());
			msg.reply("Installation to proceed!!");
			System.out.println("Inside AppInstallation handler!!");
		});
		
		// A method to implement which updates the current app_state to PICKED from SCEHDULED state...
		// UPDATE app_info set app_state= 'PICKED' where app
//		updatedState(appInfo.getApp_name());
		
		
		// NEED to add implementation for ERROR app_status....
//		Also keep track of numOfAttempts==3...
		//Still , error then 
		
		// Pass it mailVerticle using eventBus
		// WITH REASON... APP Analytics , root cause...
		String errorInfo="";
		vertx.eventBus().send("mail.message", errorInfo);
		
	}

	private void appInstallation(String app_name) {
		// TODO Auto-generated method stub
		
		// INSTALL app and update status into table using update query...
		// If successfully installed , then app_status COMPLETED...
		
		
		// IF Error.  Logger . INFO/DEBUG/ERROR added to show error related details...
		
	}

	private void updatedState(String app_name) {
		// TODO Auto-generated method stub
		Pool pool = DbConfigs.getConnectionPool(vertx);
		// Get a connection from the pool
		pool.getConnection().compose(conn -> {
		  System.out.println("Got a connection from the pool  -- APPNAME  "+app_name);

		  // All operations execute on the same connection
		  return conn
		    .preparedQuery("update app_info set app_state=? WHERE app_name=?")
		    .execute(Tuple.of("PICKEDUP",app_name))
//		    .compose(res -> conn
//		      .query("SELECT * FROM users WHERE id='emad'")
//		      .execute())
		    .onComplete(ar -> {
		      // Release the connection to the pool
		      conn.close();
		    });
		}).onComplete(ar -> {
		  if (ar.succeeded()) {
			  int rowsAffected= ar.result().rowCount();
			  System.out.println("Rows Affected !!"+rowsAffected);
		    System.out.println("Done");
		    appInstallation(app_name);
		  } else {
		    System.out.println("Something went wrong " + ar.cause().getMessage());
		  }
		  pool.close();
		});
	}
}
