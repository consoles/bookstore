package org.gpf.bookstore.dao.impl;

import java.sql.Connection;

import org.gpf.bookstore.dao.AccountDao;
import org.gpf.bookstore.db.JDBCUtils;
import org.gpf.bookstore.domain.Account;
import org.gpf.bookstore.web.ConnectionContext;
import org.junit.Before;
import org.junit.Test;

public class AccountDaoImplTest {

	AccountDao accountDao;
	
	@Before
	public void setUp() throws Exception {
		Connection connection = JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(connection);
		accountDao = new AccountDaoImpl();
	}

	@Test
	public void testGet() {
		Account account = accountDao.get(1);
		System.out.println(account);
	}

	@Test
	public void testUpdateBalance() {
		accountDao.updateBalance(1, 40);
	}

}
