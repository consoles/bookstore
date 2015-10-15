package org.gpf.bookstore.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gpf.bookstore.db.JDBCUtils;
import org.gpf.bookstore.web.ConnectionContext;
/**
 * 
* @ClassName: TransactionFilter 
* @Description: 事务过滤器（将数据库连接和当前线程绑定）
* @author gaopengfei
* @date 2015-10-14 下午2:48:02 
*
 */
@WebFilter(asyncSupported=true,urlPatterns={"/*"})
public class TransactionFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Connection connection = null;
		try {
			// 获取连接
			connection = JDBCUtils.getConnection();
			// 设置事务
			connection.setAutoCommit(false);
			// 将连接和当前线程绑定（利用ThreadLocal）
			ConnectionContext connectionContext = ConnectionContext.getInstance();
			connectionContext.bind(connection);
			// 将请求转交给目标Servlet(通过过滤器)
			chain.doFilter(request, response);
			// 提交事务
			connection.commit();
		} catch (Exception e) {
			// 回滚事务
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// 重定向到错误页
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
		} finally {
			// 关闭连接
			JDBCUtils.release(connection);
		}
	}

	@Override
	public void destroy() {
		
	}

}
