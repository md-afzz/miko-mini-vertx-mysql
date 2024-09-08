package com.example.MikoMini;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.mail.MailAttachment;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.ext.mail.MailMessage;
import io.vertx.ext.mail.StartTLSOptions;

public class MailReleaseVerticle extends AbstractVerticle{

	public MailClient getMailClient() {
		MailConfig config = new MailConfig();
		config.setHostname("mail.example.com");
		config.setPort(587);
		config.setStarttls(StartTLSOptions.REQUIRED);
		config.setUsername("user");
		config.setPassword("password");
		MailClient mailClient = MailClient.create(vertx, config);
		
		MailMessage message = new MailMessage();
		message.setFrom("user@example.com (Example User)");
		message.setTo("recipient@example.org");
		message.setCc("Another User <another@example.net>");
		message.setText("this is the plain message text");
		message.setHtml("this is html text <a href=\"http://vertx.io\">vertx.io</a>");
		
		return mailClient;
	}
	
	public MailMessage setMessages(String msg) {
		MailMessage message = new MailMessage();
		message.setFrom("user@example.com (Example User)");
		message.setTo("recipient@example.org");
		message.setCc("Another User <another@example.net>");
		message.setText("this is the plain message text");
		message.setHtml("this is html text <a href=\"http://vertx.io\">vertx.io</a>");
		
		
		/*
		 * MailAttachment attachment1 = MailAttachment.create();
		 * attachment1.setContentType("text/plain");
		 * attachment1.setData(Buffer.buffer("attachment file"));
		 * 
		 * message.setAttachment(attachment1);
		 * 
		 * MailAttachment attachment2 = MailAttachment.create();
		 * attachment2.setContentType("image/jpeg");
		 * attachment2.setData(Buffer.buffer("image data"));
		 * attachment2.setDisposition("inline");
		 * attachment2.setContentId("<image1@example.com>");
		 * 
		 * message.setInlineAttachment(attachment2);
		 */
		
		return message;
	}
	
	@Override
	public void start() throws Exception{
		
		MailClient mailClient= getMailClient();
		MailMessage mailMessage= setMessages("");
		
		vertx.eventBus().consumer("mail.message", msg->{
			String msgBody= msg.body().toString();
			mailMessage.setText(msgBody);
			msg.reply("Inside mail message consumer");
			
			
			mailClient.sendMail(mailMessage)
			  .onSuccess(System.out::println)
			  .onFailure(Throwable::printStackTrace);
		});
		
		/*
		 * mailClient.sendMail(mailMessage) .onSuccess(System.out::println)
		 * .onFailure(Throwable::printStackTrace);
		 */
	}
}
