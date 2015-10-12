package org.gpf.bookstore.service;

import org.gpf.bookstore.dao.BookDao;
import org.gpf.bookstore.dao.impl.BookDaoImpl;
import org.gpf.bookstore.domain.Book;
import org.gpf.bookstore.web.CriteriaBook;
import org.gpf.bookstore.web.Page;

public class BookService {

	private BookDao bookDao = new BookDaoImpl();
	
	public Page<Book> getPage(CriteriaBook criteriaBook){
		return bookDao.getPage(criteriaBook);
	}
}
