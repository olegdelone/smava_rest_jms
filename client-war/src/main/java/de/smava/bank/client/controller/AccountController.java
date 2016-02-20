package de.smava.bank.client.controller;

//import de.smava.bank.dao.AbsDao;
//import de.smava.bank.model.Account;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import de.smava.bank.client.rest.impl.Account;
import de.smava.bank.client.service.AccountService;
import de.smava.bank.client.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by olegdelone on 23.01.2016.
 */
@Controller
@RequestMapping("/accounts")
public class AccountController {
    private transient Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAll(Model model) {
        log.debug("client: getAll() called...");
        Client client = Client.create();

        WebResource webResource = client.resource("http://localhost:8080/rest/rest/account"); // todo

        ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
        log.debug("gotten response: {}", response);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        List<Account> accounts = response.getEntity(new GenericType<List<Account>>(){});
        log.debug("gotten accounts: {}", accounts);
        model.addAttribute("accounts", accounts);
        model.addAttribute("account", new Account());
        return "accounts";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("account") Account account) throws ServiceException {
        log.debug("client: add({}) called..." , account);
        accountService.saveAccount(account); // todo: move exception message on frontend maybe
        return "redirect:/accounts";
    }


}
