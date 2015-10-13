package org.gpf.bookstore.service;

import org.gpf.bookstore.dao.UserDao;
import org.gpf.bookstore.dao.impl.UserDaoImpl;
import org.gpf.bookstore.domain.User;

public class UserService {

	private UserDao userDao = new UserDaoImpl();
	
	public User getUserByUsername(String username){
		
		return userDao.getUser(username);
	}
}
