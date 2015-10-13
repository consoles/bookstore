package org.gpf.bookstore.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 * 
* @ClassName: ShoppingCart 
* @Description: 购物车 
* @author gaopengfei
* @date 2015-10-13 上午9:19:52 
*
 */
public class ShoppingCart {

	/**
	 * 购物车采用hashMap，键是书的id，值是购物项（书名，价格）
	 */
	private Map<Integer, ShoppingCartItem> books = new HashMap<>();

	/**
	 * 修改指定购物项的数量
	 */
	public void updateItemQuantity(Integer id, int quantity) {
		ShoppingCartItem sci = books.get(id);
		if (sci != null) {
			sci.setQuantity(quantity);
		}
	}

	/**
	 * 移除指定的购物项
	 * 
	 * @param id
	 */
	public void removeItem(Integer id) {
		books.remove(id);
	}

	/**
	 * 清空购物车
	 */
	public void clear() {
		books.clear();
	}

	/**
	 * 返回购物车是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return books.isEmpty();
	}

	/**
	 * 获取购物车中所有的商品的总的钱数
	 * 
	 * @return
	 */
	public float getTotalMoney() {
		float total = 0;

		for (ShoppingCartItem sci : getItems()) {
			total += sci.getItemMoney();
		}

		return total;
	}

	/**
	 * 获取购物车中的所有的 ShoppingCartItem 组成的集合
	 * 
	 * @return
	 */
	public Collection<ShoppingCartItem> getItems() {
		return books.values();
	}

	/**
	 * 返回购物车中商品的总数量
	 * 
	 * @return
	 */
	public int getBookNumber() {
		int total = 0;

		for (ShoppingCartItem sci : books.values()) {
			total += sci.getQuantity();
		}

		return total;
	}

	public Map<Integer, ShoppingCartItem> getBooks() {
		return books;
	}

	/**
	 * 检验购物车中是否有 id 指定的商品
	 * 
	 * @param id
	 * @return
	 */
	public boolean hasBook(Integer id) {
		return books.containsKey(id);
	}

	/**
	 * 向购物车中添加一件商品
	 * 
	 * @param book
	 */
	public void addBook(Book book) {
		// 1. 检查购物车中有没有该商品, 若有, 则使其数量 +1, 若没有,
		// 新创建其对应的 ShoppingCartItem, 并把其加入到 books 中
		ShoppingCartItem sci = books.get(book.getId());

		if (sci == null) {
			sci = new ShoppingCartItem(book);
			books.put(book.getId(), sci);
		} else {
			sci.increment();
		}
	}
}
