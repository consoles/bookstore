package org.gpf.bookstore.domain;
/**
 * 
* @ClassName: ShoppingCartItem 
* @Description: 购物车中的购物项
* @author gaopengfei
* @date 2015-10-13 上午9:18:21 
*
 */
public class ShoppingCartItem {
	private Book book; // 书
	private int quantity; // 书的数量

	public ShoppingCartItem(Book book) {
		this.book = book;
		this.quantity = 1;
	}

	public Book getBook() {
		return book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * 返回该商品在购物车中的钱数
	 */
	public float getItemMoney() {
		return book.getPrice() * quantity;
	}

	/**
	 * 使商品数量 + 1
	 */
	public void increment() {
		quantity++;
	}
}
