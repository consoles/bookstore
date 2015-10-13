package org.gpf.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.util.Set;

import org.gpf.bookstore.dao.TradeDao;
import org.gpf.bookstore.db.JDBCUtils;
import org.gpf.bookstore.domain.Trade;
import org.gpf.bookstore.web.ConnectionContext;
import org.junit.Before;
import org.junit.Test;

public class TradeDaoImplTest {

	private TradeDao tradeDao;
	
	@Before
	public void setUp() throws Exception {
		Connection connection = JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(connection);
		tradeDao = new TradeDaoImpl();
	}

	@Test
	public void testInsertTrade() {
		Trade trade = new Trade();
		trade.setUserId(1);
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		tradeDao.insert(trade);
	}

	@Test
	public void testGetTradesWithUserId() {
		 Set<Trade> trades = tradeDao.getTradesWithUserId(1);
		 System.out.println(trades);
	}

}
