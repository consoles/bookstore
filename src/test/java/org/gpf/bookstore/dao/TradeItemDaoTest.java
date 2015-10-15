package org.gpf.bookstore.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.gpf.bookstore.dao.impl.TradeItemDaoImpl;
import org.gpf.bookstore.db.JDBCUtils;
import org.gpf.bookstore.domain.TradeItem;
import org.gpf.bookstore.web.ConnectionContext;
import org.junit.Before;
import org.junit.Test;

public class TradeItemDaoTest {

	private TradeItemDao tradeItemDao;
	@Before
	public void setUp() throws Exception {
		Connection connection = JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(connection);
		tradeItemDao = new TradeItemDaoImpl();
	}

	@Test
	public void testInsert() {
		Collection<TradeItem>items = new ArrayList<>();
		items.add(new TradeItem(null, 1, 10, 12));
		items.add(new TradeItem(null, 2, 20, 12));
		items.add(new TradeItem(null, 3, 30, 12));
		tradeItemDao.batchSave(items);
	}

	@Test
	public void testGetTradesWithUserId() {
		Set<TradeItem>items = tradeItemDao.getTradeItemsWithTradeId(12);
		System.out.println(items);
	}

}
