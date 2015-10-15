<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/common/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
  <meta charset="UTF-8">
    <title>结账页</title>

  </head>
  
  <body>
  <c:if test="${requestScope.errors != null }">
  	<font color="red">${requestScope.errors }</font>
  </c:if>
    您一共买了:${sessionScope.ShoppingCart.bookNumber }本书应该付${sessionScope.ShoppingCart.totalMoney }元<br />
    <form action="books.do?method=cash" method="post">
    	<table>
    		<tr>
    			<td>信用卡姓名：</td>
    			<td><input type="text" name="username" /></td>
    		</tr>
    		<tr>
    			<td>信用卡账号：</td>
    			<td><input type="text" name="accountId" /></td>
    		</tr>
    	</table>
    	<input type="submit">
    </form>
    
  </body>
</html>
