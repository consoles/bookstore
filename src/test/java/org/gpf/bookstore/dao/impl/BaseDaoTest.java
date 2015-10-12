package org.gpf.bookstore.dao.impl;

import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.List;

import org.gpf.bookstore.domain.Book;
import org.junit.Before;
import org.junit.Test;

public class BaseDaoTest {

	private BaseDao baseDao;
	
	@Before
	public void setUp() throws Exception {
		baseDao = new BookDaoImpl();
	}

	@Test
	public void testInsert() {
		String sql = "INSERT INTO trade(userid,tradetime) VALUES(?,?)";
		long id = baseDao.insert(sql, 1,new Date(new java.util.Date().getTime()));
		System.out.println(id);
	}

	@Test
	public void testUpdate() {
		String sql = "UPDATE mybooks SET Salesamount = ? WHERE id = ?";
		baseDao.update(sql, 10,4);
	}

	@Test
	public void testQuery() {
		String sql = "SELECT id,author,title,price,publishingDate,salesAmount,storeNumber,remark FROM mybooks WHERE id = ?";
		Book book = (Book) baseDao.query(sql, 4);
		System.out.println(book);
	}

	@Test
	public void testQueryForList() {
		String sql = "SELECT id,author,title,price,publishingDate,salesAmount,storeNumber,remark FROM mybooks WHERE id < ?";
		List<Book>books = baseDao.queryForList(sql, 4);
		System.out.println(books);
	}

	@Test
	public void testGetSingleVal() {
		String sql = "SELECT count(id) FROM mybooks";
		long count = (long) baseDao.getSingleVal(sql);
		System.out.println(count);
	}

	@Test
	public void testBatch() {
		String sql = "UPDATE mybooks SET salesAmount = ?,storeNumber = ? WHERE id = ?";
		baseDao.batch(sql, new Object[]{1,1,1},new Object[]{2,2,2},new Object[]{3,3,3});
	}

}
