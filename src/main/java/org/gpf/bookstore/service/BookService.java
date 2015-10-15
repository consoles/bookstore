package org.gpf.bookstore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import org.gpf.bookstore.dao.AccountDao;
import org.gpf.bookstore.dao.BookDao;
import org.gpf.bookstore.dao.TradeDao;
import org.gpf.bookstore.dao.TradeItemDao;
import org.gpf.bookstore.dao.UserDao;
import org.gpf.bookstore.dao.impl.AccountDaoImpl;
import org.gpf.bookstore.dao.impl.BookDaoImpl;
import org.gpf.bookstore.dao.impl.TradeDaoImpl;
import org.gpf.bookstore.dao.impl.TradeItemDaoImpl;
import org.gpf.bookstore.dao.impl.UserDaoImpl;
import org.gpf.bookstore.domain.Book;
import org.gpf.bookstore.domain.ShoppingCart;
import org.gpf.bookstore.domain.ShoppingCartItem;
import org.gpf.bookstore.domain.Trade;
import org.gpf.bookstore.domain.TradeItem;
import org.gpf.bookstore.web.CriteriaBook;
import org.gpf.bookstore.web.Page;

public class BookService {

	private BookDao bookDao = new BookDaoImpl();
	private AccountDao accountDao = new AccountDaoImpl();
	private TradeDao tradeDao = new TradeDaoImpl();
	private UserDao userDao = new UserDaoImpl();
	private TradeItemDao tradeItemDao = new TradeItemDaoImpl();

	public Page<Book> getPage(CriteriaBook criteriaBook) {
		return bookDao.getPage(criteriaBook);
	}

	public Book getBook(int id) {
		return bookDao.getBook(id);
	}

	public boolean addToCart(int id, ShoppingCart sc) {

		Book book = bookDao.getBook(id);
		if (book != null) {
			sc.addBook(book);
			return true;
		}
		return false;
	}

	/**
	 * 从购物车中删除商品
	 * 
	 * @param id
	 *            商品id
	 * @param sc
	 *            购物车
	 */
	public void removeItemFromShoppingCart(int id, ShoppingCart sc) {

		sc.removeItem(id);
	}

	/**
	 * 清空购物车
	 * 
	 * @param sc
	 */
	public void clear(ShoppingCart sc) {
		sc.clear();
	}

	public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {

		sc.updateItemQuantity(id, quantity);
	}

	/**
	 * 该方法应该写到一个事务中
	 * @param shoppingCart
	 * @param username
	 * @param accountId
	 */
	public void cash(ShoppingCart shoppingCart, String username,
			String accountId) {
		// 1.更新mybooks表的库存和售出的数目
		bookDao.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());

		// 2.在account表中更新余额
		accountDao.updateBalance(Integer.parseInt(accountId),
				shoppingCart.getTotalMoney());

		// 3.向trade表中插入一条记录
		Trade trade = new Trade();
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(userDao.getUser(username).getUserId());
		tradeDao.insert(trade);

		// 假设在此处发生了异常，则事务应该回滚
//		int tmp = 10 / 0;
		
		// 4.向tradeitem表中插入n条记录
		Collection<TradeItem> items = new ArrayList<>();
		for (ShoppingCartItem sci : shoppingCart.getItems()) {
			TradeItem tradeItem = new TradeItem();

			tradeItem.setBookId(sci.getBook().getId());
			tradeItem.setQuantity(sci.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());

			items.add(tradeItem);
		}
		tradeItemDao.batchSave(items);

		// 5.清空购物车
		shoppingCart.clear();
	}
}
