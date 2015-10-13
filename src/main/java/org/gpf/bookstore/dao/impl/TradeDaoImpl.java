package org.gpf.bookstore.dao.impl;

import java.util.HashSet;
import java.util.Set;

import org.gpf.bookstore.dao.TradeDao;
import org.gpf.bookstore.domain.Trade;

public class TradeDaoImpl  extends BaseDao<Trade> implements TradeDao {

	@Override
	public void insert(Trade trade) {
		String sql = "INSERT INTO trade(userid,tradetime) VALUES(?,?)";
		long tradeId = insert(sql, trade.getUserId(),trade.getTradeTime());
		trade.setTradeId((int) tradeId);
	}

	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
		String sql = "SELECT tradeid,userid,tradetime FROM trade WHERE userid = ?";
		return new HashSet<>(queryForList(sql, userId));
	}

}
