package org.gpf.bookstore.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(asyncSupported=true,urlPatterns={"/*"},initParams={@WebInitParam(name="encoding",value="utf-8")})
public class EncodingFilter implements Filter{

	private FilterConfig filterConfig = null;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		String encoding = filterConfig.getInitParameter("encoding");
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		
	}

}
