<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>交易详情</title>
</head>

<body>
	用户名：${user.username }
	<br />
	<table>
		<c:forEach items="${user.trades }" var="trade">
			<tr>
				<table border="1" cellpadding="10">
					<tr>
						<td colspan="3">交易时间：${trade.tradeTime }</td>
					</tr>
					<c:forEach items="${ trade.items}" var="item">
						<tr>
							<td>${item.book.title }</td>
							<td>${item.book.price }</td>
							<td>${item.quantity }</td>
						</tr>
					</c:forEach>
				</table>
			</tr>
			<tr>
				<td colspan="3"></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>