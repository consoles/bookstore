<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/jsps/common/queryCondition.jsp"%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>图书详情</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>

<body>

	<form action="books.do?method=getBooks" method="post">
		价格：<input type="number" name="minPrice" /><input type="number"
			name="maxPrice" /> <input type="submit" />
	</form>
	<br />
	<br />
	<c:if test="${param.title != null }">
		您已经将${param.title }放入到购物车中！
	</c:if>
	<c:if test="${!empty sessionScope.ShoppingCart.books }">
		您的购物车中有${sessionScope.ShoppingCart.bookNumber }本书，<a href="books.do?method=toCartPage&pageNo=${bookpage.pageNo }">查看购物车</a>
	</c:if>
	<table>
		<c:forEach items="${bookpage.list }" var="book">
			<tr>
				<td><a
					href="books.do?method=getBook&pageNo=${bookpage.pageNo }&id=${book.id }">${book.title
						}</a><br> ${book.author }</td>
				<td>${book.price }</td>
				<td><a href="books.do?method=addToCart&pageNo=${bookpage.pageNo }&id=${book.id }&title=${book.title }">加入购物车</a></td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<br /> 共${bookpage.totalPageNumber }页 &nbsp; 当前第${bookpage.pageNo }页
	<br />

	<c:if test="${bookpage.hasPrev() }">
		<a href="books.do?method=getBooks&pageNo=1">首页</a>
    		&nbsp;
    		<a href="books.do?method=getBooks&pageNo=${bookpage.prevPage }">上一页</a>
	</c:if>
	<c:if test="${bookpage.hasNext() }">
		<a href="books.do?method=getBooks&pageNo=${bookpage.nextPage }">下一页</a>
    		&nbsp;
    		<a
			href="books.do?method=getBooks&pageNo=${bookpage.totalPageNumber }">尾页</a>
	</c:if>

	转到
	<input type="number" size="1" id="pageNo">页

</body>
</html>
