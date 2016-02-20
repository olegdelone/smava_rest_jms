package de.smava.bank.client.service;

import de.smava.bank.client.rest.impl.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by olegdelone on 24.01.2016.
 */
@Service
public class AccountService {
    @Autowired
    private JmsSender jmsSender;

    public void saveAccount(Account account) throws ServiceException {
        Marshaller jaxbMarshaller;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
            jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        } catch (JAXBException e) {
            throw new ServiceException("It must be smth wrong with jaxbMarshaller", e);
        }
        Writer writer = new StringWriter();
        try {
            jaxbMarshaller.marshal(account, writer);
        } catch (JAXBException e) {
            throw new ServiceException("jaxbMarshaller wasn't able to serialize given object", e);
        }
        jmsSender.send(writer.toString());
    }
}
