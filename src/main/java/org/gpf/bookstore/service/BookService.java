package org.gpf.bookstore.service;

import org.gpf.bookstore.dao.BookDao;
import org.gpf.bookstore.dao.impl.BookDaoImpl;
import org.gpf.bookstore.domain.Book;
import org.gpf.bookstore.domain.ShoppingCart;
import org.gpf.bookstore.web.CriteriaBook;
import org.gpf.bookstore.web.Page;

public class BookService {

	private BookDao bookDao = new BookDaoImpl();
	
	public Page<Book> getPage(CriteriaBook criteriaBook){
		return bookDao.getPage(criteriaBook);
	}

	public Book getBook(int id) {
		return bookDao.getBook(id);
	}

	public boolean addToCart(int id, ShoppingCart sc) {
		
		Book book = bookDao.getBook(id);
		if (book!=null) {
			sc.addBook(book);
			return true;
		}
		return false;
	}

	/**
	 * 从购物车中删除商品
	 * @param id 商品id
	 * @param sc 购物车
	 */
	public void removeItemFromShoppingCart(int id, ShoppingCart sc) {

		sc.removeItem(id);
	}

	/**
	 * 清空购物车
	 * @param sc
	 */
	public void clear(ShoppingCart sc) {
		sc.clear();
	}

	public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {

		sc.updateItemQuantity(id, quantity);
	}
}
