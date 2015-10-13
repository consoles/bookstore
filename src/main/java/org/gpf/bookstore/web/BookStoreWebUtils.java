package org.gpf.bookstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.gpf.bookstore.domain.ShoppingCart;

/**
 * 获取购物车对象
* @ClassName: BookStoreWebUtils 
* @Description: 从session中获取购物车对象，有？直接返回:创建购物车对象并放入session
* @author gaopengfei
* @date 2015-10-13 上午9:40:26 
*
 */
public class BookStoreWebUtils {

	public static ShoppingCart getShoppingCart(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		ShoppingCart sc = (ShoppingCart) session.getAttribute("ShoppingCart");
		if (sc == null) {
			sc = new ShoppingCart();
			session.setAttribute("ShoppingCart", sc);
		}
		return sc;
	}
}
