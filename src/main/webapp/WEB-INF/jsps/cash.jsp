<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'cash.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
  <c:if test="${requestScope.errors != null }">
  	<font color="red">${requestScope.errors }</font>
  </c:if>
   <c:if test="${requestScope.errors2 != null }">
  	<font color="red">${requestScope.errors2 }</font>
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
