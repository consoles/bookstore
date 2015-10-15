package org.gpf.bookstore.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gpf.bookstore.domain.User;
import org.gpf.bookstore.service.UserService;

@WebServlet("/user.do")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取username
		String username = request.getParameter("username");
		User user = new UserService().getUserWithTrades(username);
		
		if (user == null) {
			response.sendRedirect("error.jsp");
			return;
		}
		request.setAttribute("user", user);
		request.getRequestDispatcher("/WEB-INF/jsps/trades.jsp").forward(request, response);
	}

}
