package de.smava.bank.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Message;

/**
 * Created by olegdelone on 23.01.2016.
 */
@Service
public class JmsSender {
    private transient Logger log = LoggerFactory.getLogger(JmsSender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("jms.broker.destination")
    private String queueName;

    public void send(final String text) {
        this.jmsTemplate.send(session -> {
            log.debug("sending text into {}: {}", queueName, text);
            Message message = session.createTextMessage(text);
            return message;
        });
    }

}
