<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script src="static/js/jquery-2.1.1.js"></script>
<script>
	$(function() {
		// 保存查询条件
		$('a').each(function(){
			this.onclick = function(){
				var $serializeVal = $(':hidden').serialize();
				var href = this.href + '&' + $serializeVal;
				console.log(href);
				window.location.href = href;
				return false; // 阻止默认事件
			}
		});

		// 转到多少页
		$('#pageNo').blur(
				function() {
					var $val = $.trim($(this).val());

					// 错误页码提示
					if (!checkPageNo($val)) {
						alert('页码错误！');
						$(this).val('');
						return;
					}

					// 正确页码跳转
					window.location.href = 'books.do?method=getBooks&pageNo='
							+ $val + '&' + $(":hidden").serialize();

				});
	});

	// 验证页码是否合法
	var checkPageNo = function(page) {
		var reg = /^\d+$/g; // js中的正则表达式不是字符串！
		var flag = false;
		if (reg.test(page)) {
			var pageNo = parseInt(page);
			if (pageNo >= 1
					&& pageNo <= parseInt('${bookpage.totalPageNumber }')) {
				flag = true;
			}
		}
		return flag;
	}
</script>
<!-- 隐藏域，翻页时保存查询条件 -->
<input type="hidden" name="minPrice" value="${param.minPrice }" />
<input type="hidden" name="maxPrice" value="${param.maxPrice }" />