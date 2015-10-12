package org.gpf.bookstore.dao;

import java.util.List;

/**
 * 
* @ClassName: Dao 
* @Description: 定义DAO的基本操作，由BaseDao提供实现 
* @author gaopengfei
* @date 2015-10-12 下午3:49:06 
* @param <T>: DAO实际操作的泛型
 */
public interface Dao <T>{

	/**
	 * 执行插入操作，返回插入后的记录id
	 * @param sql：待执行的SQL语句
	 * @param args：填充占位符的可变参
	 * @return：插入新记录的id
	 */
	long insert(String sql,Object ... args);
	
	/**
	 * 执行更新操作（包括无返回值的insert、update、delete）
	 */
	void update(String sql,Object ... args);
	
	/**
	 * 单条记录的查询
	 * @return 返回与记录对应的一个对象
	 */
	T query(String sql,Object ... args);
	
	/**
	 * 多条记录的查询
	 * @return 与记录对应的对象的集合
	 */
	List<T> queryForList(String sql,Object ... args);
	
	/**
	 * 执行一个属性或者值的查询操作，例如查询某条记录的一个字段，或者查询某个统计信息
	 * @return 要查询的值
	 */
	<V> V getSingleVal(String sql,Object ... args);
	
	/**
	 * 批量更新
	 */
	void batch(String sql,Object[] ... args);
}
