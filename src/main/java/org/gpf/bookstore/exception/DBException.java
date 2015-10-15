package org.gpf.bookstore.exception;
/**
 * 
* @ClassName: DBException 
* @Description: 自定义的数据库异常 
* @author gaopengfei
* @date 2015-10-14 下午2:46:37 
*
 */
public class DBException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DBException() {
		
	}
	
	public DBException(String msg) {
		super(msg);
	}
	
	public DBException(String msg, Exception e) {
		super(msg, e);
	}
}
