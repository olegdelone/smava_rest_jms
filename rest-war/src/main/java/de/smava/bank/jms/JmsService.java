package de.smava.bank.jms;

import de.smava.bank.model.Account;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by olegdelone on 24.01.2016.
 */
@Service
public class JmsService {
    private transient Logger log = LoggerFactory.getLogger(JmsService.class);
    private static final int MAX_LIMIT = 50;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    ActiveMQConnectionFactory activeMQConnectionFactory;

    @Autowired
    ActiveMQQueue activeMQQueue;

    public Account receiveOne() {
        Account account = null;
        Message message;
        int i = 0; // max iteration throu the queue
        while ((message = jmsTemplate.receive()) != null && ++i < MAX_LIMIT) {
            if (message instanceof TextMessage) {
                try {
                    account = unmarshall((TextMessage) message);
                    break;
                } catch (JAXBException e) {
                    log.warn("JAXBException ", e);
                } catch (JMSException e) {
                    log.warn("JMSException ", e);
                }
            }
        }
        return account;
    }

    //The current behavior of ActiveMQ is that a queue browser can only browse messages
// that are available when it is created and not when getEnumeration is called.
    @Deprecated
    public List<Account> reviewQueue() {
        List<Account> accounts = new ArrayList<>();
        try {
            ActiveMQConnection connection;
            connection = (ActiveMQConnection) activeMQConnectionFactory.createConnection();
            QueueSession queueSession = connection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
            Queue queue = activeMQQueue;
            QueueBrowser browser = queueSession.createBrowser(queue);
            Enumeration<?> messagesInQueue = browser.getEnumeration();

            int i = 0;
            while (messagesInQueue.hasMoreElements() && ++i < MAX_LIMIT) {
                Message message = (Message) messagesInQueue.nextElement();
                if (message instanceof TextMessage) {
                    try {
                        accounts.add(unmarshall((TextMessage) message));
                    } catch (JAXBException e) {
                        log.warn("JAXBException ", e);
                    } catch (JMSException e) {
                        log.warn("JMSException ", e);
                    }
                }
            }
        } catch (JMSException e) {
            log.warn("JMSException: ", e);
        }

        return accounts;
    }


    private static Account unmarshall(TextMessage textMessage) throws JAXBException, JMSException {
        Unmarshaller unmarshaller;
        JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
        unmarshaller = jaxbContext.createUnmarshaller();
        Reader reader = new StringReader(textMessage.getText());
        Account account = (Account) unmarshaller.unmarshal(reader);
        return account;
    }
}
