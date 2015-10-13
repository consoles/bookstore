package org.gpf.bookstore.dao.impl;

import java.sql.Connection;

import org.gpf.bookstore.dao.UserDao;
import org.gpf.bookstore.db.JDBCUtils;
import org.gpf.bookstore.domain.User;
import org.gpf.bookstore.web.ConnectionContext;
import org.junit.Before;
import org.junit.Test;

public class UserDaoImplTest {

	UserDao userDao;
	
	@Before
	public void setUp() throws Exception {
		Connection connection = JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(connection);
		userDao = new UserDaoImpl();
	}

	@Test
	public void testGetUser() {
		User user = userDao.getUser("AAA");
		System.out.println(user);
	}

}
