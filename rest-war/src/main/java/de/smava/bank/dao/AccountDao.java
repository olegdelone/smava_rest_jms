package de.smava.bank.dao;

import de.smava.bank.model.Account;
import org.springframework.stereotype.Repository;

/**
 * Created by olegdelone on 23.01.2016.
 */
@Repository
public class AccountDao extends AbsDao<Account, Long> {

    protected AccountDao() {
        super(Account.class);
    }

}
