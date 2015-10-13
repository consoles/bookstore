package org.gpf.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gpf.bookstore.domain.Book;
import org.gpf.bookstore.domain.ShoppingCart;
import org.gpf.bookstore.service.BookService;
import org.gpf.bookstore.web.BookStoreWebUtils;
import org.gpf.bookstore.web.CriteriaBook;
import org.gpf.bookstore.web.Page;

import com.alibaba.fastjson.JSONObject;

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
	
	protected void addToCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		// 1. 得到商品id
		String idStr = req.getParameter("id");
		int id = -1;
		boolean flag = false;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		if (id > 0) {
			// 2. 获取购物车对象
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(req);
			// 3. 调用BookService的addToCart方法将书加入购物车
			BookService bookService = new BookService();
			flag = bookService.addToCart(id,sc);
		}
		
		if (flag) {
			// 4. 直接调用getBooks方法
			getBooks(req, resp);
			return;
		}
		resp.sendRedirect(req.getContextPath() + "/error-1.jsp");
		
	}
	
	protected void toCartPage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/jsps/cart.jsp").forward(request, response);
	}
	
	protected void remove(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = false;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		BookService bookService = new BookService();
		bookService.removeItemFromShoppingCart(id,sc);
		if (sc.isEmpty()) {
			request.getRequestDispatcher("/WEB-INF/jsps/emptycart.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("/WEB-INF/jsps/cart.jsp").forward(request, response);
	}
	
	protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(req);
		BookService bookService = new BookService();
		bookService.clear(sc);
		req.getRequestDispatcher("/WEB-INF/jsps/emptycart.jsp").forward(req, resp);
	}
	
	protected void updateItemQuantity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		String idStr = req.getParameter("id");
		String quantityStr = req.getParameter("quantity");
		
		int id = -1,quantity = -1;
		boolean flag = false;
		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (id > 0 && quantity > 0) {
			BookService bookService = new BookService();
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(req);
			bookService.updateItemQuantity(sc,id,quantity);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("bookNumber", sc.getBookNumber());
			result.put("totalMoney", sc.getTotalMoney());
			String jsonStr = JSONObject.toJSONString(result);
			resp.setContentType("application/json;charset=utf-8");
			resp.getWriter().print(jsonStr);
		}
		
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
