<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsps/common/queryCondition.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>

</head>

<body>
	<div id="bookNumber">
		购物车中共:${sessionScope.ShoppingCart.bookNumber }本书
	</div>
	
	<table>
		<thead>
			<tr>
				<td>书名</td>
				<td>价格</td>
				<td>数量</td>
				<td>&nbsp;</td>
			</tr>
		</thead>
		<c:forEach items="${sessionScope.ShoppingCart.items }" var="item">
			<tr>
				<td>${item.book.title }</td>
				<td>${item.book.price }</td>
				<td><input type="text" size="1" value="${item.quantity }" name="${item.book.id }" class="${item.quantity }"/></td>
				<td><a class="delete" href="books.do?method=remove&pageNo=${bookpage.pageNo }&id=${item.book.id }">删除</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="4" id="totalMoney">总金额：${sessionScope.ShoppingCart.totalMoney }</td>
		</tr>
	</table>
	<a href="books.do?method=clear&pageNo=${bookpage.pageNo }&id=${item.book.id }">清空购物车</a>
	<a href="books.do?method=getBooks&pageNo=${param.pageNo }">继续购物</a>
	<a href="books.do?method=forwardPage&destPage=cash.jsp">结账</a>
	<br />
	<br />
<script src="static/js/jquery-2.1.1.js"></script>
<script>
	// 删除单个商品
	$('.delete').click(function(){
		
		var $tr = $(this).parent().parent();
		var title = $.trim($tr.find('td:first').text());
		var flag = confirm('确定要删除' + title + '么？');
		if(flag){
			return true;
		}
		return false;	// 取消默认行为
	});
	
	// ajax修改商品数量
	$(':text').change(function(){
		
		var idVal = $.trim(this.name);
		var quantityVal = $.trim(this.value);
		var quantity = -1;
		var reg = /^\d+$/g;
		var flag = false;
		if(reg.test(idVal)){
			quantity = parseInt(quantityVal);
			if(quantity >= 0){
				flag = true;
			}
		}
		
		if(!flag){
			$(this).val($(this).attr('class'));
			alert('输入的值不合法！');
			return;
		}
		
		if(quantity == 0){
			if(confirm('确定删除？')){
				var $tr = $(this).parent().parent();
				var $a = $tr.find('td:last').find('a');
				$a[0].onclick();
				return;
			}
		}
		
		var url = "books.do";
		var args = {
				"method":'updateItemQuantity',
				"id":idVal,
				"quantity":quantityVal,
				"time":new Date()
		};
		
		$.post(url,args,function(data){
		
			console.log(data);
			$('#totalMoney').text('总金额：￥' + data.totalMoney);
			$('#bookNumber').text('购物车中共:' + data.bookNumber + '本书');
		},'json');
	});
</script>
</body>
</html>
