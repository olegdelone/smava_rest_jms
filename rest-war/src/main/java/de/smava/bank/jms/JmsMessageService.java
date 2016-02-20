package de.smava.bank.jms;

import de.smava.bank.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringReader;

/**
 * deprecated since synchronous interaction was implemented
 */
@Service
@Deprecated
public class JmsMessageService implements MessageListener {
    private transient Logger log = LoggerFactory.getLogger(JmsMessageService.class);

    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            log.warn("caught wrong message: ", message);
            return;
        }
        TextMessage textMessage = (TextMessage) message;
        log.debug("JMS message received: {}", message);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Reader reader = new StringReader(textMessage.getText());
            Account account = (Account) unmarshaller.unmarshal(reader);
            account.setId(null);

        } catch (JAXBException|JMSException e) {
            // if we re-throw exception it may lead to looping of message receiving
            log.warn("Something wrong with message: {} ", message);
            log.warn("Trace: ", e);
        }
    }
}
