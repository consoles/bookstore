package org.gpf.bookstore.web;

import java.sql.Connection;

public class ConnectionContext {

	private ThreadLocal<Connection> conncetionThreadLocal = new ThreadLocal<>();
	
	private ConnectionContext() {
		
	}
	
	private static ConnectionContext instance = new ConnectionContext();
	
	public static ConnectionContext getInstance() {
		return instance;
	}
	
	public void bind(Connection connection) {
		conncetionThreadLocal.set(connection);
	}
	
	public Connection get() {
		return conncetionThreadLocal.get();
	}
	
	public void remove() {
		conncetionThreadLocal.remove();
	}
}
