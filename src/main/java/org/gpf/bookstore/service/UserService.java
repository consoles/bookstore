package org.gpf.bookstore.service;

import java.util.Set;

import org.gpf.bookstore.dao.BookDao;
import org.gpf.bookstore.dao.TradeDao;
import org.gpf.bookstore.dao.TradeItemDao;
import org.gpf.bookstore.dao.UserDao;
import org.gpf.bookstore.dao.impl.BookDaoImpl;
import org.gpf.bookstore.dao.impl.TradeDaoImpl;
import org.gpf.bookstore.dao.impl.TradeItemDaoImpl;
import org.gpf.bookstore.dao.impl.UserDaoImpl;
import org.gpf.bookstore.domain.Trade;
import org.gpf.bookstore.domain.TradeItem;
import org.gpf.bookstore.domain.User;

public class UserService {

	private UserDao userDao = new UserDaoImpl();
	private TradeDao tradeDao = new TradeDaoImpl();
	private TradeItemDao tradeItemDao = new TradeItemDaoImpl();
	private BookDao bookDao = new BookDaoImpl();
	
	public User getUserByUsername(String username){
		
		return userDao.getUser(username);
	}
	
	public User getUserWithTrades(String username) {
		User user = userDao.getUser(username);
		if (user == null) {
			return null;
		}
		int userId = user.getUserId();
		Set<Trade> trades = tradeDao.getTradesWithUserId(userId);
		if (trades != null) {
			for (Trade trade : trades) {
				int tradeId = trade.getTradeId();
				Set<TradeItem> items = tradeItemDao.getTradeItemsWithTradeId(tradeId);
				if (items != null) {
					for (TradeItem item :items) {
						item.setBook(bookDao.getBook(item.getBookId()));
					}
				}
				trade.setItems(items);
			}
		}
		user.setTrades(trades);
		
		return user;
	}
	
}
