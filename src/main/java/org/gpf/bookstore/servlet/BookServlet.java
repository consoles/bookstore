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

import org.gpf.bookstore.domain.Account;
import org.gpf.bookstore.domain.Book;
import org.gpf.bookstore.domain.ShoppingCart;
import org.gpf.bookstore.domain.ShoppingCartItem;
import org.gpf.bookstore.domain.User;
import org.gpf.bookstore.service.AccountService;
import org.gpf.bookstore.service.BookService;
import org.gpf.bookstore.service.UserService;
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
	
	/**
	 * 转发页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void forwardPage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		String destPage = request.getParameter("destPage");
		request.getRequestDispatcher("/WEB-INF/jsps/" + destPage).forward(request, response);
	}
	
	protected void remove(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		
		String idStr = request.getParameter("id");
		int id = -1;
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
	
	protected void cash(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 1. 简单验证：不需要经过数据库或者调用数据库的方法，例如是否是email，是否为int类型
		String username = req.getParameter("username");
		String accountId = req.getParameter("accountId");
		
		StringBuilder errors = validateFormField(username, accountId);
		
		// 表单验证通过
		if (errors.toString().equals("")) {
			errors = validateUser(username, accountId);
			// 用户合法性验证通过
			if (errors.toString().equals("")) {
				errors = validateBookStoreNumber(req);
				// 库存验证通过
				if (errors.toString().equals("")) {
					// 验证余额
					errors = validateBalance(accountId,req);
				}
			}
		}
		
		if (!errors.toString().equals("")) {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/jsps/cash.jsp").forward(req, resp);
			return;
		}
		
		// 验证通过执行结账操作
		BookService bookService = new BookService();
		bookService.cash(BookStoreWebUtils.getShoppingCart(req),username,accountId);
		req.getRequestDispatcher("/success.jsp").forward(req, resp);
	}

	/**
	 * 余额充足？
	 * @param accountId
	 * @return
	 */
	private StringBuilder validateBalance(String accountId,HttpServletRequest request) {
		
		StringBuilder errors = new StringBuilder();
		
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		Account account = new AccountService().getAccount(Integer.parseInt(accountId));
		if (sc.getTotalMoney() > account.getBalance()) {
			errors.append("余额不足");
		}
		
		return errors;
	}
	
	/**
	 * 验证库存是否充足
	 * @return
	 */
	private StringBuilder validateBookStoreNumber(HttpServletRequest request) {
		
		StringBuilder errors = new StringBuilder();
		
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		for(ShoppingCartItem sci : sc.getItems()){
			int quantity = sci.getQuantity();
			int storeNumber = new BookService().getBook(sci.getBook().getId()).getStoreNumber();
			if (quantity > storeNumber) {
				errors.append(sci.getBook().getTitle() + "库存不足");
			}
		}
		return errors;
	}
	
	/**
	 *  验证用户名和账号是否匹配
	 * @param username
	 * @param accountId
	 * @return
	 */
	private StringBuilder validateUser(String username, String accountId) {
		boolean flag = false;
		UserService userService = new UserService();
		User user = userService.getUserByUsername(username);
		if (user != null) {
			int accountId2 = user.getAccountId();
			if ((accountId2 + "").trim().equals(accountId)) {
				flag = true;
			}
		} 
		StringBuilder errors2 = new StringBuilder();
		if (!flag) {
			errors2.append("用户名和账号不匹配！");
		}
		return errors2;
	}
	
	/**
	 * 验证表单域是否非空
	 * @param username
	 * @param accountId
	 * @return
	 */
	private StringBuilder validateFormField(String username, String accountId) {
		
		StringBuilder errors = new StringBuilder();
		if (null == username || "".equals(username.trim())) {
			errors.append("用户名不能为空<br />");
		}
		if (null == accountId || "".equals(accountId.trim())) {
			errors.append("账号不能为空");
		}
		return errors;
	}
	
	protected void updateItemQuantity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		String idStr = req.getParameter("id");
		String quantityStr = req.getParameter("quantity");
		
		int id = -1,quantity = -1;
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
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
