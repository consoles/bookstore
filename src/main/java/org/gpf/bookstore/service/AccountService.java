package org.gpf.bookstore.service;

import org.gpf.bookstore.dao.AccountDao;
import org.gpf.bookstore.dao.impl.AccountDaoImpl;
import org.gpf.bookstore.domain.Account;

public class AccountService {

	private AccountDao accountDao = new AccountDaoImpl();
	
	public Account getAccount(int accountId) {
		return accountDao.get(accountId);
	}
}
