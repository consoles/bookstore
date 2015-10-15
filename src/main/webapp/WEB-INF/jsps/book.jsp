<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsps/common/queryCondition.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>图书详情</title>

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
