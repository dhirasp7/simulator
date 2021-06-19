package com.asprsys.simulator.config.queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {

	  @Value("${destination.sensor.queue}")
	  private String destinationQueue;

	  private static final Logger LOGGER =
	      LoggerFactory.getLogger(Sender.class);

	  @Autowired
	  private JmsTemplate jmsTemplate;

	  public void send(String message) {
	    LOGGER.info("sending message='{}' to destination='{}'", message,
	        destinationQueue);
	    jmsTemplate.convertAndSend(destinationQueue, message);
	  }
	  
	  public void send(String destination, Object message) {
		  if(destination == null) destination = destinationQueue;
		    LOGGER.info("sending message='{}' to destination='{}'", message,
		    		destination);
		    jmsTemplate.convertAndSend(destination, message);
	  }
	  
	  public void send(Object message) {
		    LOGGER.info("sending message='{}' to destination='{}'", message,
		        destinationQueue);
		    jmsTemplate.convertAndSend(destinationQueue, message);
		  }	  
	}