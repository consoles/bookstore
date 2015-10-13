package org.gpf.bookstore.dao;

import org.gpf.bookstore.domain.Account;

public interface AccountDao {
	/**
	 * 根据accountId获得对应的Account对象
	 * @param accountId
	 * @return
	 */
	public abstract Account get(Integer accountId);
	
	/**
	 * 根据传入的 accountId, amount 更新指定账户的余额: 扣除 amount 指定的钱数
	 * @param accountId
	 * @param amount
	 * @throws Exception
	 */
	public abstract void updateBalance(Integer accountId,float amount);
}
