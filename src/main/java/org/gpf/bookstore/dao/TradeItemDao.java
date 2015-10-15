package org.gpf.bookstore.dao;

import java.util.Collection;
import java.util.Set;

import org.gpf.bookstore.domain.TradeItem;

public interface TradeItemDao {
	/**
	 * 向数据表中插入 Trade 对象
	 * @param trade
	 */
	public abstract void batchSave(Collection<TradeItem> items);

	/**
	 * 根据 userId 获取和其关联的 Trade 的集合
	 * @param userId
	 * @return
	 */
	public abstract Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId);
}
