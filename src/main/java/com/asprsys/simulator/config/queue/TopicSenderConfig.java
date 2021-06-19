package com.asprsys.simulator.config.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class TopicSenderConfig {

	  @Value("${spring.activemq.broker-url}")
	  private String brokerUrl;
	  @Value("${spring.activemq.username}")
	  private String username;
	  @Value("${spring.activemq.password}")
	  private String password;

	  @Bean
	  public ActiveMQConnectionFactory senderActiveMQConnectionFactory() {
	    ActiveMQConnectionFactory activeMQConnectionFactory =
	        new ActiveMQConnectionFactory();
	    activeMQConnectionFactory.setBrokerURL(brokerUrl);
	    activeMQConnectionFactory.setUserName(username);
	    activeMQConnectionFactory.setPassword(password);
	    return activeMQConnectionFactory;
	  }

	  @Bean
	  public JmsTemplate jmsTemplate() {
	    JmsTemplate jmsTemplate =
	        new JmsTemplate(senderActiveMQConnectionFactory());
	   // jmsTemplate.setPubSubDomain(true);

	    return jmsTemplate;
	  }
	}
