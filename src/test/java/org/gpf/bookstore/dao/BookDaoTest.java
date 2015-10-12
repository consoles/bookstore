package org.gpf.bookstore.dao;

import static org.junit.Assert.*;

import org.gpf.bookstore.dao.impl.BookDaoImpl;
import org.gpf.bookstore.domain.Book;
import org.gpf.bookstore.web.CriteriaBook;
import org.gpf.bookstore.web.Page;
import org.junit.Before;
import org.junit.Test;

public class BookDaoTest {

	private BookDao bookDao;
	
	@Before
	public void setUp() throws Exception {
		bookDao = new BookDaoImpl();
	}

	@Test
	public void testGetBook() {
		Book book = bookDao.getBook(5);
		System.out.println(book);
	}

	@Test
	public void testGetPage() {
		CriteriaBook cb = new CriteriaBook(70, 80, 3);
		Page<Book> page = bookDao.getPage(cb);
		System.out.println("满足条件的书有：" + page.getTotalItemNumber() + "本");
		System.out.println("共：" + page.getTotalPageNumber() + "页");
		System.out.println("每页显示：" + page.getPageSize() + "本书");
		System.out.println("当前页：" + page.getPageNo());
		System.out.println("上一页：" + page.getPrevPage());
		System.out.println("下一页：" + page.getNextPage());
	}

	@Test
	public void testGetTotalBookNumber() {
		CriteriaBook cb = new CriteriaBook(70, 80, 0);
		System.out.println(bookDao.getTotalBookNumber(cb));
	}

	@Test
	public void testGetPageList() {
		CriteriaBook cb = new CriteriaBook(70, 80, 3);
		System.out.println(bookDao.getPageList(cb, 3));
	}

	@Test
	public void testGetStoreNumber() {
		int storeNumber = bookDao.getStoreNumber(5);
		System.out.println(storeNumber);
	}

	@Test
	public void testBatchUpdateStoreNumberAndSalesAmount() {
		fail("Not yet implemented");
	}

}
