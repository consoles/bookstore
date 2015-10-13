1. 通过表单的隐藏域保存查询条件（带条件的翻页）
2. jstl中的注释为<%--  --%>,不能使用普通的html注释
3. jstl中可以调用bean的属性和方法
4. Servlet3.0中Filter的配置initParams={@WebInitParam(name="charset",value="utf-8")}
5. 查看图书库存的时候应该调用BookService，然后Service再调用DAO得到库存，而不能直接从购物车中取得书得到图书的数量。
      因为是先得到购物车，可能因为多线程的问题修改了书的数量。即：
      
```java
int storeNumber = sci.getBook().getStoreNumber();                                   // 错误

int storeNumber = new BookService().getBook(sci.getBook().getId()).getStoreNumber();// 正确
```      

6. 结账的操作应该放到一个事务中。
7. 利用ThreadLocal和Filter让多个Dao使用同一个Connection完成事务的操作