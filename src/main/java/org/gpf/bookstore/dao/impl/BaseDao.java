package org.gpf.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.gpf.bookstore.dao.Dao;
import org.gpf.bookstore.db.JDBCUtils;
import org.gpf.bookstore.utils.ReflectionUtils;
import org.gpf.bookstore.web.ConnectionContext;

public class BaseDao<T> implements Dao<T> {

	private QueryRunner queryRunner = new QueryRunner();
	private Class<T> clazz;

	public BaseDao() {
		clazz = ReflectionUtils.getSuperGenericType(getClass());
	}

	@Override
	public long insert(String sql, Object... args) {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 获取插入的id
		long id = 0;
		try {
			connection = ConnectionContext.getInstance().get();
			pstmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					pstmt.setObject(i + 1, args[i]);
				}
			}
			pstmt.executeUpdate();

			// 获取生成的主键值
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(rs, pstmt);
		}

		return id;
	}

	@Override
	public void update(String sql, Object... args) {

		Connection connection = null;
		try {
			connection = ConnectionContext.getInstance().get();
			queryRunner.update(connection, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public T query(String sql, Object... args) {

		Connection connection = null;
		try {
			connection = ConnectionContext.getInstance().get();
			return queryRunner.query(connection, sql, new BeanHandler<>(clazz),
					args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> queryForList(String sql, Object... args) {
		Connection connection = null;
		try {
			connection = ConnectionContext.getInstance().get();
			return queryRunner.query(connection, sql, new BeanListHandler<>(
					clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getSingleVal(String sql, Object... args) {
		Connection connection = null;
		try {
			connection = ConnectionContext.getInstance().get();
			return (V) queryRunner.query(connection, sql, new ScalarHandler<Object>(), args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void batch(String sql, Object[]... args) {
		Connection connection = null;
		try {
			connection = ConnectionContext.getInstance().get();
			queryRunner.batch(connection, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
