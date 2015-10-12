<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/common/queryCondition.jsp" %>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>图书详情页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>

<body>
	<input type="hidden" name="minPrice" value="${param.minPrice }" />
	<input type="hidden" name="maxPrice" value="${param.maxPrice }" />
	标题：${book.title }
	<br /> 作者：${book.author }
	<br /> 价格：${book.price }
	<br /> 出版时间：${book.publishingDate }
	<br /> 数量：${book.salesAmount }
	<br /> 库存：${book.storeNumber }
	<br /> 星级：${book.remark }
	<br />

	<a href="books.do?method=getBooks&pageNo=${param.pageNo }">继续购物</a>
</body>
</html>
