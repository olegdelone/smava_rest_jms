package de.smava.bank.resource;

import de.smava.bank.dao.AbsDao;
import de.smava.bank.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import java.util.List;

/**
 * Created by olegdelone on 23.01.2016.
 */

@Path("account")
@Component
public class AccountResource {
    private transient Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Autowired
    AbsDao<Account, Long> accountDao;

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void add(JAXBElement<Account> input) {
        log.debug("add({}) called", input);
        Account account = input.getValue();
        account.setId(null);
        accountDao.persist(account);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void update(JAXBElement<Account> input) {
        log.debug("update({}) called", input);
        accountDao.merge(input.getValue());
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") long id) {
        log.debug("remove({}) called", id);
        accountDao.remove(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{id}")
    public Account get(@PathParam("id") long id) {
        log.debug("get({}) called", id);
        return accountDao.find(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Account> getAll() {
        log.debug("getAll() called");
        return accountDao.list(0,50);
    }

}
