package de.smava.bank.jms.controller;

import de.smava.bank.dao.AbsDao;
import de.smava.bank.jms.JmsService;
import de.smava.bank.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by olegdelone on 23.01.2016.
 */
@Controller
@RequestMapping("/queue")
public class JmsController {
    private transient Logger log = LoggerFactory.getLogger(JmsController.class);

    @Autowired
    AbsDao<Account, Long> accountDao;

    @Autowired
    private JmsService jmsService;

    @RequestMapping(method = RequestMethod.GET)
    String getAll(Model model) {
        log.debug("queue: getAll() called...");
        List<Account> accounts = jmsService.reviewQueue();
        model.addAttribute("accountsInQueue", accounts);
        return "queue";
    }

    @RequestMapping(value = "/receiveOne", method = RequestMethod.POST)
    String receiveOne(RedirectAttributes redirectAttrs) {
        log.debug("queue: receiveOne() called..." );
        Account account = jmsService.receiveOne();
        if(account != null){
            log.debug("persisting account: {}", account);
            accountDao.persist(account);
            log.debug("persisted account: {}", account);
            redirectAttrs.addFlashAttribute("recentlyAdded", account);
        }
        return "redirect:/queue";
    }


}
