<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>图书详情</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<!-- 隐藏域，翻页时保存查询条件 -->
  	<input type="hidden" name="minPrice" value="${param.minPrice }" />
  	<input type="hidden" name="maxPrice" value="${param.maxPrice }" />
    <center>
    	<form action="books.do?method=getBooks" method="post">
    		价格：<input type="number" name="minPrice"/><input type="number" name="maxPrice"/>
    		<input type="submit"/>
    	</form>
    	<br /><br />
    	<table>
    		<c:forEach items="${bookpage.list }" var="book">
    			<tr>
    				<td>
    					<a href="#">${book.title }</a><br>
    					${book.author }
    				</td>
    				<td>${book.price }</td>
    				<td><a href="">加入购物车</a></td>
    			</tr>
    		</c:forEach>
    	</table>
    	<br><br>
    	共${bookpage.totalPageNumber }页
    	&nbsp;
    	当前第${bookpage.pageNo }页
    	<br />
    	<c:if test="${bookpage.hasPrev }">
    		<a href="books.do?method=getBooks&pageNo=1">首页</a>
    		&nbsp;
    		<a href="books.do?method=getBooks&pageNo=${bookpage.prevPage }">上一页</a>
    	</c:if>
    	<c:if test="${bookpage.hasNext }">
    		<a href="books.do?method=getBooks&pageNo=${bookpage.nextPage }">下一页</a>
    		&nbsp;
    		<a href="books.do?method=getBooks&pageNo=${bookpage.totalPageNumber }">尾页</a>
    	</c:if>
    	转到<input type="number" size="1" id="pageNo">页	
    </center>
    <script src="static/js/jquery-2.1.1.js"></script>
    <script>
    	// 保存查询条件
   		$('a').click(function(){
   			var $serializeVal = $(':hidden').serialize();
   			var href = this.href + '&' + $serializeVal;
   			console.log(href);
   			window.location.href = href;
   			return false; // 必加
   		});
    	
    	// 转到多少页
    	$('#pageNo').blur(function(){
    		var $val = $.trim($(this).val());
    		
    		// 错误页码提示
    		if(!checkPageNo($val)){
    			alert('页码错误！');
    			$(this).val('');
    			return;
    		}
    		
    		// 正确页码跳转
    		window.location.href = 'books.do?method=getBooks&pageNo=' + $val + '&' + $(":hidden").serialize();
    		
    	});
    	
    	// 验证页码是否合法
    	var checkPageNo = function(page){
    		var reg = /^\d+$/g; // js中的正则表达式不是字符串！
    		var flag = false;
    		if(reg.test(page)){
    			var pageNo = parseInt(page);
    			if(pageNo >= 1 && pageNo <= parseInt('${bookpage.totalPageNumber }')){
    				flag = true;
    			}
    		}
    		return flag;
    	}
    </script>
  </body>
</html>
