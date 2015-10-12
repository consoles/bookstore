package org.gpf.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gpf.bookstore.domain.Book;
import org.gpf.bookstore.service.BookService;
import org.gpf.bookstore.web.CriteriaBook;
import org.gpf.bookstore.web.Page;

@WebServlet(asyncSupported=true,urlPatterns={"/books.do"})
public class BookServlet extends HttpServlet {

	private static final long serialVersionUID = -6832343822821250410L;

	protected void getBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		
		int pageNo = 1;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;
		
		try {
			pageNo = Integer.valueOf(pageNoStr);
		} catch (NumberFormatException e) {
			
		}
		try {
			minPrice = Integer.valueOf(minPriceStr);
		} catch (NumberFormatException e) {
			
		}
		try {
			maxPrice = Integer.valueOf(maxPriceStr);
		} catch (NumberFormatException e) {
			
		}
		
		CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
		BookService bookService = new BookService();
		Page<Book> page = bookService.getPage(criteriaBook);
		request.setAttribute("bookpage", page);
		request.getRequestDispatcher("/WEB-INF/jsps/books.jsp").forward(request, response);
	}

	protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idStr = request.getParameter("id");
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		Book book = null;
		// 性能优化
		if (id > 0) {
			BookService bookService = new BookService();
			book = bookService.getBook(id);
		}
		
		if (book == null) {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
			return;
		}
		request.setAttribute("book", book);
		request.getRequestDispatcher("/WEB-INF/jsps/book.jsp").forward(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String methodName = req.getParameter("method");
		Method method;
		try {
			method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, req,resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
