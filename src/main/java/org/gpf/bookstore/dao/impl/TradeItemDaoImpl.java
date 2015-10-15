package org.gpf.bookstore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gpf.bookstore.dao.TradeItemDao;
import org.gpf.bookstore.domain.TradeItem;

public class TradeItemDaoImpl extends BaseDao<TradeItem> implements
		TradeItemDao {

	@Override
	public Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId) {

		String sql = "SELECT itemid tradeItemId,bookId,quantity,tradeId FROM tradeitem WHERE tradeid = ?";
		return new HashSet<>(queryForList(sql, tradeId));
	}

	@Override
	public void batchSave(Collection<TradeItem> items) {

		String sql = "INSERT INTO tradeitem(bookid,quantity,tradeid) VALUES(?,?,?)";
		Object[][] parms = new Object[items.size()][3];
		List<TradeItem> tradeItems = new ArrayList<>(items);

		for (int i = 0; i < tradeItems.size(); i++) {
			parms[i][0] = tradeItems.get(i).getBookId();
			parms[i][1] = tradeItems.get(i).getQuantity();
			parms[i][2] = tradeItems.get(i).getTradeId();
		}
		batch(sql, parms);
	}

}
