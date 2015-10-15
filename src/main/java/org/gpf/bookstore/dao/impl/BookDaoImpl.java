package org.gpf.bookstore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.gpf.bookstore.dao.BookDao;
import org.gpf.bookstore.domain.Book;
import org.gpf.bookstore.domain.ShoppingCartItem;
import org.gpf.bookstore.web.CriteriaBook;
import org.gpf.bookstore.web.Page;

public class BookDaoImpl extends BaseDao<Book> implements BookDao{

	@Override
	public Book getBook(int id) {
		String sql = "SELECT id,author,title,price,publishingDate,salesAmount,storeNumber,remark FROM mybooks WHERE id = ?";
		return query(sql, id);
	}

	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		Page<Book> page = new Page<Book>(cb.getPageNo());
		page.setTotalItemNumber(getTotalBookNumber(cb));
		// 检验pageNo的合法性
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb, 3));
		return page;
	}

	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		String sql = "SELECT count(id) FROM mybooks WHERE price >= ? AND price <= ?";
		return getSingleVal(sql, cb.getMinPrice(),cb.getMaxPrice());
	}

	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		String sql = "SELECT id,author,title,price,publishingDate,salesAmount,storeNumber,remark FROM mybooks WHERE price >= ? AND price <= ? LIMIT ?,?";
		return queryForList(sql, cb.getMinPrice(),cb.getMaxPrice(),(cb.getPageNo() - 1) * pageSize,pageSize);
	}

	@Override
	public int getStoreNumber(Integer id) {
		String sql = "SELECT storeNumber FROM mybooks WHERE id = ?";
		return getSingleVal(sql, id);
	}

	@Override
	public void batchUpdateStoreNumberAndSalesAmount(
			Collection<ShoppingCartItem> items) {
		String sql = "UPDATE mybooks SET Salesamount = Salesamount + ?,Storenumber = Storenumber - ? WHERE id = ?";
		Object[][] params = null;
		List<ShoppingCartItem> scits = new ArrayList<>(items);
		params = new Object[items.size()][3];
		for (int i = 0; i < items.size(); i++) {
			params[i][0] = scits.get(i).getQuantity();
			params[i][1] = scits.get(i).getQuantity();
			params[i][2] = scits.get(i).getBook().getId();
		}
		batch(sql, params);
	}

}
