package org.gpf.bookstore.dao.impl;

import org.gpf.bookstore.dao.UserDao;
import org.gpf.bookstore.domain.User;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

	@Override
	public User getUser(String username) {
		
		String sql = "SELECT userId,username,accountId FROM userinfo WHERE username = ?";
		return query(sql, username);
	}

}
