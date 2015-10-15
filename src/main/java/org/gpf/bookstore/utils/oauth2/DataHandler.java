package org.gpf.bookstore.utils.oauth2;


import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
* @ClassName: DataHandler 
* @Description: 数据处理器，处理方法中完成对数据库结果对象的逻辑处理
* @author gaopengfei
* @date 2015-10-14 下午8:50:10 
*
 */
public interface DataHandler {

   /**
    *  处理结果集
    * @param rs
    * @return
    * @throws SQLException
    */
    public Object handle(ResultSet rs) throws SQLException;
}
