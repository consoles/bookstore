<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/common/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>查看已购买的宝贝</title>


  </head>
  
  <body>
    <form action="user.do?method=getUser" method="post">
    	用户名:<input type="text" name="username" />
    	<input type="submit" />
    </form>
  </body>
</html>
